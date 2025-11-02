package com.librarymanagementsystem.librarymanagementsystem.service.impl;

import com.librarymanagementsystem.librarymanagementsystem.dto.PatronDTO;
import com.librarymanagementsystem.librarymanagementsystem.exception.ResourceNotFoundException;
import com.librarymanagementsystem.librarymanagementsystem.mapper.PatronMapper;
import com.librarymanagementsystem.librarymanagementsystem.model.Patron;
import com.librarymanagementsystem.librarymanagementsystem.repository.PatronRepository;
import com.librarymanagementsystem.librarymanagementsystem.service.PatronService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PatronServiceImpl implements PatronService {

    private final PatronRepository patronRepository;
    private final PatronMapper patronMapper;

    public PatronServiceImpl(PatronRepository patronRepository, PatronMapper patronMapper) {
        this.patronRepository = patronRepository;
        this.patronMapper = patronMapper;
    }

    @Override
    public List<PatronDTO> getAllPatrons() {
        return patronRepository.findAll().stream()
                .map(patronMapper::toPatronDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PatronDTO getPatronById(Long id) {
        Patron patron = patronRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patron not found with id: " + id));
        return patronMapper.toPatronDTO(patron);
    }

    @Override
    public PatronDTO createPatron(PatronDTO patronDTO) {
        Patron patron = patronMapper.toPatron(patronDTO);
        return patronMapper.toPatronDTO(patronRepository.save(patron));
    }

    @Override
    public PatronDTO updatePatron(Long id, PatronDTO patronDTO) {
        Patron existingPatron = patronRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patron not found with id: " + id));

        existingPatron.setName(patronDTO.getName());
        existingPatron.setContactInfo(patronDTO.getContactInfo());

        Patron updatedPatron = patronRepository.save(existingPatron);
        return patronMapper.toPatronDTO(updatedPatron);
    }

    @Override
    public void deletePatron(Long id) {
        if (!patronRepository.existsById(id)) {
            throw new ResourceNotFoundException("Patron not found with id: " + id);
        }
        patronRepository.deleteById(id);
    }

    @Override
    public List<PatronDTO> searchPatronsByName(String name) {
        return patronRepository.findByNameContaining(name).stream()
                .map(patronMapper::toPatronDTO)
                .collect(Collectors.toList());
    }
}
