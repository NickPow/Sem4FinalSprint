package com.example.bst_app.web;

import com.example.bst_app.BstAppApplication;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = BstAppApplication.class)
@AutoConfigureMockMvc
class TreeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void enterNumbersPage_loads() throws Exception {
        mockMvc.perform(get("/enter-numbers"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Binary Search Tree Builder")));
    }

    @Test
    void processNumbers_returnsJson() throws Exception {
        String body = mapper.writeValueAsString(Map.of(
                "numbers", "10, 5, 15",
                "balanced", false
        ));

        mockMvc.perform(post("/process-numbers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"value\"")));
    }

    @Test
    void previousTreesPage_loads() throws Exception {
        mockMvc.perform(get("/previous-trees"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Previous Trees")));
    }
}
