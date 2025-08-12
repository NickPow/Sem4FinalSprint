package com.example.bst_app.service;

import com.example.bst_app.model.TreeRecord;
import com.example.bst_app.repo.TreeRecordRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TreeServiceSmokeTest {

    @Autowired
    private TreeService treeService;

    @Autowired
    private TreeRecordRepository repo;

    @Test
    void buildsAndPersistsTree_now() {
        var nums = treeService.parseNumbers("10, 5, 15, 3, 7");
        String json = treeService.buildAndPersistTree(nums, false);

        assertNotNull(json);
        assertTrue(json.contains("\"value\""));

        List<TreeRecord> saved = repo.findAll();
        assertFalse(saved.isEmpty());
        assertEquals("10, 5, 15, 3, 7", saved.get(saved.size() - 1).getInputNumbers());
    }
}
