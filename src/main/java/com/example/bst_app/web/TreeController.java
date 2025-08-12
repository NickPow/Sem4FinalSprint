package com.example.bst_app.web;

import com.example.bst_app.model.TreeRecord;
import com.example.bst_app.service.TreeService;
import com.example.bst_app.web.dto.ProcessRequest;
import org.springframework.http.MediaType;
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
        var numbers = treeService.parseNumbers(request.getNumbers());
        return treeService.buildAndPersistTree(numbers, request.isBalanced());
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
}
