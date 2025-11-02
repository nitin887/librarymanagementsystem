package com.librarymanagementsystem.librarymanagementsystem.service;

import com.librarymanagementsystem.librarymanagementsystem.dto.PatronDTO;
import com.librarymanagementsystem.librarymanagementsystem.exception.ResourceNotFoundException;
import com.librarymanagementsystem.librarymanagementsystem.mapper.PatronMapper;
import com.librarymanagementsystem.librarymanagementsystem.model.Patron;
import com.librarymanagementsystem.librarymanagementsystem.repository.PatronRepository;
import com.librarymanagementsystem.librarymanagementsystem.service.impl.PatronServiceImpl;
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
public class PatronServiceTest {

    @Mock
    private PatronRepository patronRepository;

    @Mock
    private PatronMapper patronMapper;

    @InjectMocks
    private PatronServiceImpl patronService;

    private Patron patron;
    private PatronDTO patronDTO;

    @BeforeEach
    void setUp() {
        patron = new Patron();
        patron.setId(1L);
        patron.setName("Test Patron");

        patronDTO = new PatronDTO();
        patronDTO.setId(1L);
        patronDTO.setName("Test Patron");
    }

    @Test
    void getAllPatrons() {
        when(patronRepository.findAll()).thenReturn(Arrays.asList(patron));
        when(patronMapper.toPatronDTO(any(Patron.class))).thenReturn(patronDTO);

        List<PatronDTO> result = patronService.getAllPatrons();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo("Test Patron");
        verify(patronRepository, times(1)).findAll();
    }

    @Test
    void getPatronById() {
        when(patronRepository.findById(1L)).thenReturn(Optional.of(patron));
        when(patronMapper.toPatronDTO(any(Patron.class))).thenReturn(patronDTO);

        PatronDTO result = patronService.getPatronById(1L);

        assertThat(result.getName()).isEqualTo("Test Patron");
        verify(patronRepository, times(1)).findById(1L);
    }

    @Test
    void getPatronById_NotFound() {
        when(patronRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> patronService.getPatronById(1L));
        verify(patronRepository, times(1)).findById(1L);
    }

    @Test
    void createPatron() {
        when(patronMapper.toPatron(any(PatronDTO.class))).thenReturn(patron);
        when(patronRepository.save(any(Patron.class))).thenReturn(patron);
        when(patronMapper.toPatronDTO(any(Patron.class))).thenReturn(patronDTO);

        PatronDTO result = patronService.createPatron(patronDTO);

        assertThat(result.getName()).isEqualTo("Test Patron");
        verify(patronRepository, times(1)).save(any(Patron.class));
    }

    @Test
    void updatePatron() {
        when(patronRepository.findById(1L)).thenReturn(Optional.of(patron));
        when(patronRepository.save(any(Patron.class))).thenReturn(patron);
        when(patronMapper.toPatronDTO(any(Patron.class))).thenReturn(patronDTO);

        PatronDTO result = patronService.updatePatron(1L, patronDTO);

        assertThat(result.getName()).isEqualTo("Test Patron");
        verify(patronRepository, times(1)).findById(1L);
        verify(patronRepository, times(1)).save(any(Patron.class));
    }

    @Test
    void updatePatron_NotFound() {
        when(patronRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> patronService.updatePatron(1L, patronDTO));
        verify(patronRepository, times(1)).findById(1L);
        verify(patronRepository, never()).save(any(Patron.class));
    }

    @Test
    void deletePatron() {
        when(patronRepository.existsById(1L)).thenReturn(true);
        doNothing().when(patronRepository).deleteById(1L);

        patronService.deletePatron(1L);

        verify(patronRepository, times(1)).existsById(1L);
        verify(patronRepository, times(1)).deleteById(1L);
    }

    @Test
    void deletePatron_NotFound() {
        when(patronRepository.existsById(1L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> patronService.deletePatron(1L));
        verify(patronRepository, times(1)).existsById(1L);
        verify(patronRepository, never()).deleteById(anyLong());
    }

    @Test
    void searchPatronsByName() {
        when(patronRepository.findByNameContaining(anyString())).thenReturn(Arrays.asList(patron));
        when(patronMapper.toPatronDTO(any(Patron.class))).thenReturn(patronDTO);

        List<PatronDTO> result = patronService.searchPatronsByName("Test");

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo("Test Patron");
        verify(patronRepository, times(1)).findByNameContaining(anyString());
    }
}
