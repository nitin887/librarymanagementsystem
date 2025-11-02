package com.librarymanagementsystem.librarymanagementsystem.service;

import com.librarymanagementsystem.librarymanagementsystem.dto.AuthorDTO;
import com.librarymanagementsystem.librarymanagementsystem.exception.ResourceNotFoundException;
import com.librarymanagementsystem.librarymanagementsystem.mapper.AuthorMapper;
import com.librarymanagementsystem.librarymanagementsystem.model.Author;
import com.librarymanagementsystem.librarymanagementsystem.repository.AuthorRepository;
import com.librarymanagementsystem.librarymanagementsystem.service.impl.AuthorServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthorServiceTest {

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private AuthorMapper authorMapper;

    @InjectMocks
    private AuthorServiceImpl authorService;

    private Author author;
    private AuthorDTO authorDTO;

    @BeforeEach
    void setUp() {
        author = new Author();
        author.setId(1L);
        author.setName("Test Author");

        authorDTO = new AuthorDTO();
        authorDTO.setId(1L);
        authorDTO.setName("Test Author");
    }

    @Test
    void getAllAuthors() {
        when(authorRepository.findAll()).thenReturn(Arrays.asList(author));
        when(authorMapper.toAuthorDTO(any(Author.class))).thenReturn(authorDTO);

        List<AuthorDTO> result = authorService.getAllAuthors();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo("Test Author");
        verify(authorRepository, times(1)).findAll();
    }

    @Test
    void getAuthorById() {
        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));
        when(authorMapper.toAuthorDTO(any(Author.class))).thenReturn(authorDTO);

        AuthorDTO result = authorService.getAuthorById(1L);

        assertThat(result.getName()).isEqualTo("Test Author");
        verify(authorRepository, times(1)).findById(1L);
    }

    @Test
    void getAuthorById_NotFound() {
        when(authorRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> authorService.getAuthorById(1L));
        verify(authorRepository, times(1)).findById(1L);
    }

    @Test
    void createAuthor() {
        when(authorMapper.toAuthor(any(AuthorDTO.class))).thenReturn(author);
        when(authorRepository.save(any(Author.class))).thenReturn(author);
        when(authorMapper.toAuthorDTO(any(Author.class))).thenReturn(authorDTO);

        AuthorDTO result = authorService.createAuthor(authorDTO);

        assertThat(result.getName()).isEqualTo("Test Author");
        verify(authorRepository, times(1)).save(any(Author.class));
    }

    @Test
    void updateAuthor() {
        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));
        when(authorRepository.save(any(Author.class))).thenReturn(author);
        when(authorMapper.toAuthorDTO(any(Author.class))).thenReturn(authorDTO);

        AuthorDTO result = authorService.updateAuthor(1L, authorDTO);

        assertThat(result.getName()).isEqualTo("Test Author");
        verify(authorRepository, times(1)).findById(1L);
        verify(authorRepository, times(1)).save(any(Author.class));
    }

    @Test
    void updateAuthor_NotFound() {
        when(authorRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> authorService.updateAuthor(1L, authorDTO));
        verify(authorRepository, times(1)).findById(1L);
        verify(authorRepository, never()).save(any(Author.class));
    }

    @Test
    void deleteAuthor() {
        when(authorRepository.existsById(1L)).thenReturn(true);
        doNothing().when(authorRepository).deleteById(1L);

        authorService.deleteAuthor(1L);

        verify(authorRepository, times(1)).existsById(1L);
        verify(authorRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteAuthor_NotFound() {
        when(authorRepository.existsById(1L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> authorService.deleteAuthor(1L));
        verify(authorRepository, times(1)).existsById(1L);
        verify(authorRepository, never()).deleteById(anyLong());
    }

    @Test
    void searchAuthorsByName() {
        when(authorRepository.findByNameContaining(anyString())).thenReturn(Arrays.asList(author));
        when(authorMapper.toAuthorDTO(any(Author.class))).thenReturn(authorDTO);

        List<AuthorDTO> result = authorService.searchAuthorsByName("Test");

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo("Test Author");
        verify(authorRepository, times(1)).findByNameContaining(anyString());
    }
}
