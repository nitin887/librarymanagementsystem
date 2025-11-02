package com.librarymanagementsystem.librarymanagementsystem.service;

import com.librarymanagementsystem.librarymanagementsystem.dto.PatronDTO;

import java.util.List;

public interface PatronService {

    List<PatronDTO> getAllPatrons();

    PatronDTO getPatronById(Long id);

    PatronDTO createPatron(PatronDTO patronDTO);

    PatronDTO updatePatron(Long id, PatronDTO patronDTO);

    void deletePatron(Long id);

    List<PatronDTO> searchPatronsByName(String name);
}
