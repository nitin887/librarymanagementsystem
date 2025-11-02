package com.librarymanagementsystem.librarymanagementsystem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.librarymanagementsystem.librarymanagementsystem.dto.AuthorDTO;
import com.librarymanagementsystem.librarymanagementsystem.service.AuthorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
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

@WebMvcTest(AuthorController.class)
@AutoConfigureMockMvc(addFilters = false)
public class AuthorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthorService authorService;

    @Autowired
    private ObjectMapper objectMapper;

    private AuthorDTO authorDTO;

    @BeforeEach
    void setUp() {
        authorDTO = new AuthorDTO();
        authorDTO.setId(1L);
        authorDTO.setName("Test Author");
    }

    @Test
    @WithMockUser(roles = "USER")
    void getAllAuthors() throws Exception {
        when(authorService.getAllAuthors()).thenReturn(Arrays.asList(authorDTO));

        mockMvc.perform(get("/api/authors"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Test Author"));
    }

    @Test
    @WithMockUser(roles = "USER")
    void getAuthorById() throws Exception {
        when(authorService.getAuthorById(1L)).thenReturn(authorDTO);

        mockMvc.perform(get("/api/authors/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Author"));
    }

    @Test
    @WithMockUser(roles = "USER")
    void searchAuthorsByName() throws Exception {
        when(authorService.searchAuthorsByName("Test")).thenReturn(Arrays.asList(authorDTO));

        mockMvc.perform(get("/api/authors/search").param("name", "Test"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Test Author"));
    }

    @Test
    @WithMockUser(roles = "LIBRARIAN")
    void createAuthor() throws Exception {
        when(authorService.createAuthor(any(AuthorDTO.class))).thenReturn(authorDTO);

        mockMvc.perform(post("/api/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authorDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Test Author"));
    }

    @Test
    @WithMockUser(roles = "LIBRARIAN")
    void updateAuthor() throws Exception {
        when(authorService.updateAuthor(anyLong(), any(AuthorDTO.class))).thenReturn(authorDTO);

        mockMvc.perform(put("/api/authors/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authorDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Author"));
    }

    @Test
    @WithMockUser(roles = "LIBRARIAN")
    void deleteAuthor() throws Exception {
        mockMvc.perform(delete("/api/authors/1"))
                .andExpect(status().isNoContent());
    }
}
