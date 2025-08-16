package com.example.bst_app.web;

import com.example.bst_app.model.TreeRecord;
import com.example.bst_app.service.TreeService;
import com.example.bst_app.web.dto.ProcessRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for handling user input, processing numbers into BSTs, and viewing history
 */
@Controller
public class TreeController {

    private final TreeService treeService;

    public TreeController(TreeService treeService) {
        this.treeService = treeService;
    }

    /** Show the input page */
    @GetMapping("/enter-numbers")
    public String enterNumbersPage() {
        return "enter-numbers";
    }

    /** Process numbers into a BST, return JSON */
    @PostMapping(value = "/process-numbers", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String processNumbers(@RequestBody ProcessRequest request) {
        try {
            var numbers = treeService.parseNumbers(request.getNumbers());
            return treeService.buildAndPersistTree(numbers, request.isBalanced());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Input validation failed: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Failed to process tree: " + e.getMessage());
        }
    }

    /** Show previously saved trees */
    @GetMapping("/previous-trees")
    public String previousTrees(Model model) {
        List<TreeRecord> records = treeService.getPreviousTrees();
        model.addAttribute("records", records);
        return "previous-trees";
    }

    /** Redirect root to /enter-numbers */
    @GetMapping("/")
    public String rootRedirect() {
        return "redirect:/enter-numbers";
    }

    /** Handle validation errors */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleValidationError(IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    /** Handle general processing errors */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleProcessingError(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
}
