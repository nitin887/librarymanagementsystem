package com.librarymanagementsystem.librarymanagementsystem.service;

import com.librarymanagementsystem.librarymanagementsystem.dto.BorrowingRecordDTO;
import com.librarymanagementsystem.librarymanagementsystem.exception.ResourceNotFoundException;
import com.librarymanagementsystem.librarymanagementsystem.mapper.BorrowingRecordMapper;
import com.librarymanagementsystem.librarymanagementsystem.model.Book;
import com.librarymanagementsystem.librarymanagementsystem.model.BorrowingRecord;
import com.librarymanagementsystem.librarymanagementsystem.model.Patron;
import com.librarymanagementsystem.librarymanagementsystem.repository.BookRepository;
import com.librarymanagementsystem.librarymanagementsystem.repository.BorrowingRecordRepository;
import com.librarymanagementsystem.librarymanagementsystem.repository.PatronRepository;
import com.librarymanagementsystem.librarymanagementsystem.service.impl.BorrowingRecordServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BorrowingRecordServiceTest {

    @Mock
    private BorrowingRecordRepository borrowingRecordRepository;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private PatronRepository patronRepository;

    @Mock
    private BorrowingRecordMapper borrowingRecordMapper;

    @InjectMocks
    private BorrowingRecordServiceImpl borrowingRecordService;

    private Book book;
    private Patron patron;
    private BorrowingRecord borrowingRecord;
    private BorrowingRecordDTO borrowingRecordDTO;

    @BeforeEach
    void setUp() {
        book = new Book();
        book.setId(1L);
        book.setTitle("Test Book");

        patron = new Patron();
        patron.setId(1L);
        patron.setName("Test Patron");

        borrowingRecord = new BorrowingRecord();
        borrowingRecord.setId(1L);
        borrowingRecord.setBook(book);
        borrowingRecord.setPatron(patron);
        borrowingRecord.setBorrowDate(LocalDate.now());

        borrowingRecordDTO = new BorrowingRecordDTO();
        borrowingRecordDTO.setId(1L);
        borrowingRecordDTO.setBookId(1L);
        borrowingRecordDTO.setPatronId(1L);
        borrowingRecordDTO.setBorrowDate(LocalDate.now());
    }

    @Test
    void borrowBook() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(patronRepository.findById(1L)).thenReturn(Optional.of(patron));
        when(borrowingRecordRepository.save(any(BorrowingRecord.class))).thenReturn(borrowingRecord);
        when(borrowingRecordMapper.toBorrowingRecordDTO(any(BorrowingRecord.class))).thenReturn(borrowingRecordDTO);

        BorrowingRecordDTO result = borrowingRecordService.borrowBook(1L, 1L);

        assertThat(result.getBookId()).isEqualTo(1L);
        assertThat(result.getPatronId()).isEqualTo(1L);
        verify(borrowingRecordRepository, times(1)).save(any(BorrowingRecord.class));
    }

    @Test
    void returnBook() {
        when(borrowingRecordRepository.findById(1L)).thenReturn(Optional.of(borrowingRecord));
        when(borrowingRecordRepository.save(any(BorrowingRecord.class))).thenReturn(borrowingRecord);
        when(borrowingRecordMapper.toBorrowingRecordDTO(any(BorrowingRecord.class))).thenReturn(borrowingRecordDTO);

        borrowingRecordService.returnBook(1L);

        ArgumentCaptor<BorrowingRecord> borrowingRecordCaptor = ArgumentCaptor.forClass(BorrowingRecord.class);
        verify(borrowingRecordRepository).save(borrowingRecordCaptor.capture());

        BorrowingRecord savedRecord = borrowingRecordCaptor.getValue();
        assertThat(savedRecord.getReturnDate()).isNotNull();
    }

    @Test
    void getOverdueBooks() {
        when(borrowingRecordRepository.findByDueDateBeforeAndReturnDateIsNull(any(LocalDate.class))).thenReturn(Arrays.asList(borrowingRecord));
        when(borrowingRecordMapper.toBorrowingRecordDTO(any(BorrowingRecord.class))).thenReturn(borrowingRecordDTO);

        List<BorrowingRecordDTO> result = borrowingRecordService.getOverdueBooks();

        assertThat(result).hasSize(1);
        verify(borrowingRecordRepository, times(1)).findByDueDateBeforeAndReturnDateIsNull(any(LocalDate.class));
    }
}
