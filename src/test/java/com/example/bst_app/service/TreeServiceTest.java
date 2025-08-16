package com.example.bst_app.service;

import com.example.bst_app.model.TreeRecord;
import com.example.bst_app.repo.TreeRecordRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TreeServiceTest {

    @Autowired
    private TreeService treeService;

    @Autowired
    private TreeRecordRepository repo;

    @Test
    void parseNumbers_validInput_parsesList() {
        List<Integer> nums = treeService.parseNumbers("10, 5, 15, 3, 7");
        assertEquals(List.of(10, 5, 15, 3, 7), nums);
    }

    @Test
    void parseNumbers_invalid_throws() {
        assertThrows(IllegalArgumentException.class, () -> treeService.parseNumbers("10, x, 3"));
    }

    @Test
    void buildAndPersistTree_savesRecord() {
        String json = treeService.buildAndPersistTree(List.of(10, 5, 15), false);
        assertNotNull(json);
        List<TreeRecord> all = repo.findAll();
        assertTrue(all.size() >= 1);
        TreeRecord last = all.get(all.size() - 1);
        assertEquals("10, 5, 15", last.getInputNumbers());
        assertFalse(last.isBalanced());
    }

    @Test
    void buildAndPersistTree_balanced_changesShape() {
        String jsonUnbalanced = treeService.buildAndPersistTree(List.of(10, 5, 15, 3, 7), false);
        String jsonBalanced = treeService.buildAndPersistTree(List.of(10, 5, 15, 3, 7), true);
        assertNotEquals(jsonUnbalanced, jsonBalanced, "Balanced tree JSON should differ from unbalanced.");
    }

    @Test
    void parseNumbers_variousDelimiters_parsesCorrectly() {
        List<Integer> nums = treeService.parseNumbers("10,5;15\n3 7");
        assertEquals(List.of(10, 5, 15, 3, 7), nums);
    }

    @Test
    void parseNumbers_singleNumber_parsesList() {
        List<Integer> nums = treeService.parseNumbers("42");
        assertEquals(List.of(42), nums);
    }

    @Test
    void parseNumbers_nullInput_throws() {
        assertThrows(IllegalArgumentException.class, () -> treeService.parseNumbers(null));
    }

    @Test
    void parseNumbers_whitespaceOnly_throws() {
        assertThrows(IllegalArgumentException.class, () -> treeService.parseNumbers("   "));
    }

    @Test
    void buildAndPersistTree_duplicateNumbers_handlesCorrectly() {
        String json = treeService.buildAndPersistTree(List.of(10, 5, 10, 5, 15), false);
        assertNotNull(json);
        assertTrue(json.contains("\"value\""));
    }

    @Test
    void buildAndPersistTree_singleNumber_createsValidTree() {
        String json = treeService.buildAndPersistTree(List.of(42), false);
        assertNotNull(json);
        assertTrue(json.contains("\"value\" : 42"));
        assertTrue(json.contains("\"left\" : null"));
        assertTrue(json.contains("\"right\" : null"));
    }
}
