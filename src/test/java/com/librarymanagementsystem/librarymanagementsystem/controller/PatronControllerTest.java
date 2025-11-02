package com.librarymanagementsystem.librarymanagementsystem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.librarymanagementsystem.librarymanagementsystem.dto.PatronDTO;
import com.librarymanagementsystem.librarymanagementsystem.service.PatronService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PatronController.class)
public class PatronControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PatronService patronService;

    @Autowired
    private ObjectMapper objectMapper;

    private PatronDTO patronDTO;

    @BeforeEach
    void setUp() {
        patronDTO = new PatronDTO();
        patronDTO.setId(1L);
        patronDTO.setName("Test Patron");
    }

    @Test
    @WithMockUser(roles = "USER")
    void getAllPatrons() throws Exception {
        when(patronService.getAllPatrons()).thenReturn(Arrays.asList(patronDTO));

        mockMvc.perform(get("/api/patrons"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Test Patron"));
    }

    @Test
    @WithMockUser(roles = "USER")
    void getPatronById() throws Exception {
        when(patronService.getPatronById(1L)).thenReturn(patronDTO);

        mockMvc.perform(get("/api/patrons/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Patron"));
    }

    @Test
    @WithMockUser(roles = "USER")
    void searchPatronsByName() throws Exception {
        when(patronService.searchPatronsByName("Test")).thenReturn(Arrays.asList(patronDTO));

        mockMvc.perform(get("/api/patrons/search").param("name", "Test"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Test Patron"));
    }

    @Test
    @WithMockUser(roles = "LIBRARIAN")
    void createPatron() throws Exception {
        when(patronService.createPatron(any(PatronDTO.class))).thenReturn(patronDTO);

        mockMvc.perform(post("/api/patrons").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patronDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Test Patron"));
    }

    @Test
    @WithMockUser(roles = "LIBRARIAN")
    void updatePatron() throws Exception {
        when(patronService.updatePatron(anyLong(), any(PatronDTO.class))).thenReturn(patronDTO);

        mockMvc.perform(put("/api/patrons/1").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patronDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Patron"));
    }

    @Test
    @WithMockUser(roles = "LIBRARIAN")
    void deletePatron() throws Exception {
        mockMvc.perform(delete("/api/patrons/1").with(csrf()))
                .andExpect(status().isNoContent());
    }
}
