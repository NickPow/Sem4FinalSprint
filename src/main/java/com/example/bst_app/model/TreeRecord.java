package com.example.bst_app.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * TreeRecord stores the original input string and the resulting tree JSON.
 */
@Entity
@Table(name = "tree_records")
public class TreeRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 2000)
    private String inputNumbers;    // Numbers as entered or normalized string

    @Lob
    @Column(nullable = false)
    private String treeJson;        // JSON representation of the BST

    @Column(nullable = false)
    private boolean balanced;       // Whether balancing was requested

    @Column(nullable = false)
    private LocalDateTime createdAt;

    public TreeRecord() {
    }

    public TreeRecord(String inputNumbers, String treeJson, boolean balanced, LocalDateTime createdAt) {
        this.inputNumbers = inputNumbers;
        this.treeJson = treeJson;
        this.balanced = balanced;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public String getInputNumbers() {
        return inputNumbers;
    }

    public void setInputNumbers(String inputNumbers) {
        this.inputNumbers = inputNumbers;
    }

    public String getTreeJson() {
        return treeJson;
    }

    public void setTreeJson(String treeJson) {
        this.treeJson = treeJson;
    }

    public boolean isBalanced() {
        return balanced;
    }

    public void setBalanced(boolean balanced) {
        this.balanced = balanced;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
