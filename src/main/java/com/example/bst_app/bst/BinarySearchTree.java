package com.example.bst_app.bst;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Simple Binary Search Tree with:
 * - Sequential insert
 * - Inorder traversal (for balance step)
 * - Export to a JSON-friendly Map structure
 */
public class BinarySearchTree {

    private TreeNode root;

    public void insert(int value) {
        root = insertRecursive(root, value);
    }

    private TreeNode insertRecursive(TreeNode node, int value) {
        if (node == null) {
            return new TreeNode(value);
        }

        if (value < node.value) {
            node.left = insertRecursive(node.left, value);
        } else {
            node.right = insertRecursive(node.right, value);
        }

        return node;
    }

    public TreeNode getRoot() {
        return root;
    }

    public void setRoot(TreeNode root) {
        this.root = root;
    }

    /** Inorder traversal yields a sorted list of values. */
    public List<Integer> inorder() {
        List<Integer> out = new ArrayList<>();
        inorderDfs(root, out);
        return out;
    }

    private void inorderDfs(TreeNode node, List<Integer> out) {
        if (node == null) return;
        inorderDfs(node.left, out);
        out.add(node.value);
        inorderDfs(node.right, out);
    }

    /**
     * Convert the tree to a JSON-friendly nested Map structure:
     * {
     *   "value": 10,
     *   "left":  { ... } or null,
     *   "right": { ... } or null
     * }
     */
    public Map<String, Object> toJsonMap() {
        return nodeToMap(root);
    }

    private Map<String, Object> nodeToMap(TreeNode node) {
        if (node == null) return null;

        Map<String, Object> map = new LinkedHashMap<>();
        map.put("value", node.value);
        map.put("left", nodeToMap(node.left));
        map.put("right", nodeToMap(node.right));
        return map;
    }
}
