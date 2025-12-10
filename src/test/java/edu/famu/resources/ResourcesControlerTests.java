package edu.famu.resources;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ResourcesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void listResources_ReturnsAtLeastSeededSize() throws Exception {
        mockMvc.perform(get("/api/resources"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(5))));
    }

    @Test
    void getResourceById_ReturnsCorrectResource() throws Exception {
        mockMvc.perform(get("/api/resources/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Math Tutoring Center"));
    }
}
