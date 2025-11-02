package com.librarymanagementsystem.librarymanagementsystem.service;

import com.librarymanagementsystem.librarymanagementsystem.dto.BookDTO;
import com.librarymanagementsystem.librarymanagementsystem.exception.ResourceNotFoundException;
import com.librarymanagementsystem.librarymanagementsystem.mapper.BookMapper;
import com.librarymanagementsystem.librarymanagementsystem.model.Book;
import com.librarymanagementsystem.librarymanagementsystem.repository.BookRepository;
import com.librarymanagementsystem.librarymanagementsystem.service.impl.BookServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookMapper bookMapper;

    @InjectMocks
    private BookServiceImpl bookService;

    private Book book;
    private BookDTO bookDTO;

    @BeforeEach
    void setUp() {
        book = new Book();
        book.setId(1L);
        book.setTitle("Test Book");
        book.setAuthor("Test Author");
        book.setPublishedDate(LocalDate.of(2022, 1, 1));
        book.setIsbn("1234567890");

        bookDTO = new BookDTO();
        bookDTO.setId(1L);
        bookDTO.setTitle("Test Book");
        bookDTO.setAuthor("Test Author");
        bookDTO.setPublishedDate(LocalDate.of(2022, 1, 1));
        bookDTO.setIsbn("1234567890");
    }

    @Test
    void getAllBooks() {
        when(bookRepository.findAll()).thenReturn(Arrays.asList(book));
        when(bookMapper.toBookDTO(any(Book.class))).thenReturn(bookDTO);

        List<BookDTO> result = bookService.getAllBooks();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getTitle()).isEqualTo("Test Book");
        verify(bookRepository, times(1)).findAll();
    }

    @Test
    void getBookById() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(bookMapper.toBookDTO(any(Book.class))).thenReturn(bookDTO);

        BookDTO result = bookService.getBookById(1L);

        assertThat(result.getTitle()).isEqualTo("Test Book");
        verify(bookRepository, times(1)).findById(1L);
    }

    @Test
    void getBookById_NotFound() {
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> bookService.getBookById(1L));
        verify(bookRepository, times(1)).findById(1L);
    }

    @Test
    void createBook() {
        when(bookMapper.toBook(any(BookDTO.class))).thenReturn(book);
        when(bookRepository.save(any(Book.class))).thenReturn(book);
        when(bookMapper.toBookDTO(any(Book.class))).thenReturn(bookDTO);

        BookDTO result = bookService.createBook(bookDTO);

        assertThat(result.getTitle()).isEqualTo("Test Book");
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    void updateBook() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(bookRepository.save(any(Book.class))).thenReturn(book);
        when(bookMapper.toBookDTO(any(Book.class))).thenReturn(bookDTO);

        BookDTO result = bookService.updateBook(1L, bookDTO);

        assertThat(result.getTitle()).isEqualTo("Test Book");
        verify(bookRepository, times(1)).findById(1L);
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    void updateBook_NotFound() {
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> bookService.updateBook(1L, bookDTO));
        verify(bookRepository, times(1)).findById(1L);
        verify(bookRepository, never()).save(any(Book.class));
    }

    @Test
    void deleteBook() {
        when(bookRepository.existsById(1L)).thenReturn(true);
        doNothing().when(bookRepository).deleteById(1L);

        bookService.deleteBook(1L);

        verify(bookRepository, times(1)).existsById(1L);
        verify(bookRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteBook_NotFound() {
        when(bookRepository.existsById(1L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> bookService.deleteBook(1L));
        verify(bookRepository, times(1)).existsById(1L);
        verify(bookRepository, never()).deleteById(anyLong());
    }

    @Test
    void searchBooksByTitle() {
        when(bookRepository.findByTitleContaining(anyString())).thenReturn(Arrays.asList(book));
        when(bookMapper.toBookDTO(any(Book.class))).thenReturn(bookDTO);

        List<BookDTO> result = bookService.searchBooksByTitle("Test");

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getTitle()).isEqualTo("Test Book");
        verify(bookRepository, times(1)).findByTitleContaining(anyString());
    }
}
