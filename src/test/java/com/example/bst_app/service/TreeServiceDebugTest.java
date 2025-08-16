package com.example.bst_app.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class TreeServiceDebugTest {

    @Autowired
    private TreeService treeService;

    @Test
    void debugSingleNumberJson() {
        String json = treeService.buildAndPersistTree(List.of(42), false);
        System.out.println("Actual JSON:");
        System.out.println(json);
        System.out.println("Contains '\"value\": 42': " + json.contains("\"value\": 42"));
        System.out.println("Contains '\"value\" : 42': " + json.contains("\"value\" : 42"));
        System.out.println("Contains 'value': " + json.contains("value"));
        System.out.println("Contains '42': " + json.contains("42"));
    }
}
