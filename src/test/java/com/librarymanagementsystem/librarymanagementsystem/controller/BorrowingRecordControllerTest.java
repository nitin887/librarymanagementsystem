package com.librarymanagementsystem.librarymanagementsystem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.librarymanagementsystem.librarymanagementsystem.dto.BorrowingRecordDTO;
import com.librarymanagementsystem.librarymanagementsystem.service.BorrowingRecordService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BorrowingRecordController.class)
public class BorrowingRecordControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BorrowingRecordService borrowingRecordService;

    @Autowired
    private ObjectMapper objectMapper;

    private BorrowingRecordDTO borrowingRecordDTO;

    @BeforeEach
    void setUp() {
        borrowingRecordDTO = new BorrowingRecordDTO();
        borrowingRecordDTO.setId(1L);
        borrowingRecordDTO.setBookId(1L);
        borrowingRecordDTO.setPatronId(1L);
        borrowingRecordDTO.setBorrowDate(LocalDate.now());
    }

    @Test
    @WithMockUser(roles = "USER")
    void getAllBorrowingRecords() throws Exception {
        when(borrowingRecordService.getAllBorrowingRecords()).thenReturn(Arrays.asList(borrowingRecordDTO));

        mockMvc.perform(get("/api/borrowing-records"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L));
    }

    @Test
    @WithMockUser(roles = "LIBRARIAN")
    void borrowBook() throws Exception {
        when(borrowingRecordService.borrowBook(anyLong(), anyLong())).thenReturn(borrowingRecordDTO);

        mockMvc.perform(post("/api/borrowing-records/borrow").with(csrf())
                        .param("bookId", "1")
                        .param("patronId", "1"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    @WithMockUser(roles = "LIBRARIAN")
    void returnBook() throws Exception {
        when(borrowingRecordService.returnBook(anyLong())).thenReturn(borrowingRecordDTO);

        mockMvc.perform(put("/api/borrowing-records/1/return").with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }
}
