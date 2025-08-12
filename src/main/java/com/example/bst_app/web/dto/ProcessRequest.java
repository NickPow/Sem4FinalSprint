package com.example.bst_app.web.dto;

/**
 * Request DTO used by /process-numbers (AJAX JSON request)
 */
public class ProcessRequest {

    private String numbers;
    private boolean balanced;

    public ProcessRequest() {}

    public ProcessRequest(String numbers, boolean balanced) {
        this.numbers = numbers;
        this.balanced = balanced;
    }

    public String getNumbers() {
        return numbers;
    }

    public void setNumbers(String numbers) {
        this.numbers = numbers;
    }

    public boolean isBalanced() {
        return balanced;
    }

    public void setBalanced(boolean balanced) {
        this.balanced = balanced;
    }
}
