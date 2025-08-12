package com.example.bst_app.bst;

/**
 * Basic BST node that stores an integer value
 * and references to left/right children.
 */
public class TreeNode {

    public int value;
    public TreeNode left;
    public TreeNode right;

    public TreeNode(int value) {
        this.value = value;
    }
}
