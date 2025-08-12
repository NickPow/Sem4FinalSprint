package com.example.bst_app.service;

import com.example.bst_app.bst.BinarySearchTree;
import com.example.bst_app.bst.TreeNode;
import com.example.bst_app.model.TreeRecord;
import com.example.bst_app.repo.TreeRecordRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * TreeService
 * - Parses user input into integers (validates)
 * - Builds BST by sequential insertion
 * - Optionally balances using inorder -> rebuild middle strategy
 * - Serializes to JSON string
 * - Persists input + JSON to DB
 */
@Service
public class TreeService {

    private final TreeRecordRepository treeRecordRepository;
    private final ObjectMapper objectMapper;

    public TreeService(TreeRecordRepository treeRecordRepository) {
        this.treeRecordRepository = treeRecordRepository;
        this.objectMapper = new ObjectMapper();
    }

    /** Public API: build tree from numbers, maybe balance, return JSON, and persist a record. */
    public String buildAndPersistTree(List<Integer> numbers, boolean balanced) {
        // 1) Build unbalanced BST by sequential insert
        BinarySearchTree bst = new BinarySearchTree();
        for (int n : numbers) {
            bst.insert(n);
        }

        // 2) Optional balance (Bonus): rebuild from inorder values
        if (balanced) {
            List<Integer> sorted = bst.inorder();
            TreeNode newRoot = buildBalanced(sorted, 0, sorted.size() - 1);
            bst.setRoot(newRoot);
        }

        // 3) Serialize to JSON
        Map<String, Object> jsonMap = bst.toJsonMap();
        String jsonString = toJson(jsonMap);

        // 4) Persist record with normalized input string
        String normalizedInput = numbers.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(", "));

        TreeRecord record = new TreeRecord(
                normalizedInput,
                jsonString,
                balanced,
                LocalDateTime.now()
        );
        treeRecordRepository.save(record);

        return jsonString;
    }

    /** Retrieve previous trees newest-first. */
    public List<TreeRecord> getPreviousTrees() {
        return treeRecordRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(TreeRecord::getCreatedAt).reversed())
                .toList();
    }

    /** Parse a comma/space/newline separated string into integers with validation. */
    public List<Integer> parseNumbers(String input) {
        if (input == null || input.trim().isEmpty()) {
            throw new IllegalArgumentException("Please enter at least one number.");
        }

        // Normalize delimiters: treat commas, spaces, newlines, semicolons as separators
        String normalized = input
                .replaceAll("[\\n\\r;]+", " ")
                .replaceAll("\\s+", " ")
                .trim()
                .replaceAll(",\\s*", " ");

        String[] parts = normalized.split("[ ,]+");

        List<Integer> numbers = new ArrayList<>();
        for (String p : parts) {
            try {
                numbers.add(Integer.parseInt(p));
            } catch (NumberFormatException ex) {
                throw new IllegalArgumentException("Invalid number: '" + p + "'");
            }
        }
        return numbers;
    }

    /** Build a height-balanced BST from a sorted list using the middle as root. */
    private TreeNode buildBalanced(List<Integer> sorted, int left, int right) {
        if (left > right) return null;
        int mid = (left + right) / 2;
        TreeNode node = new TreeNode(sorted.get(mid));
        node.left = buildBalanced(sorted, left, mid - 1);
        node.right = buildBalanced(sorted, mid + 1, right);
        return node;
    }

    private String toJson(Map<String, Object> map) {
        try {
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(map);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize tree to JSON", e);
        }
    }
}
