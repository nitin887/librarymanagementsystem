package com.librarymanagementsystem.librarymanagementsystem.service.impl;

import com.librarymanagementsystem.librarymanagementsystem.dto.AuthorDTO;
import com.librarymanagementsystem.librarymanagementsystem.exception.ResourceNotFoundException;
import com.librarymanagementsystem.librarymanagementsystem.mapper.AuthorMapper;
import com.librarymanagementsystem.librarymanagementsystem.model.Author;
import com.librarymanagementsystem.librarymanagementsystem.repository.AuthorRepository;
import com.librarymanagementsystem.librarymanagementsystem.service.AuthorService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    public AuthorServiceImpl(AuthorRepository authorRepository, AuthorMapper authorMapper) {
        this.authorRepository = authorRepository;
        this.authorMapper = authorMapper;
    }

    @Override
    public List<AuthorDTO> getAllAuthors() {
        return authorRepository.findAll().stream()
                .map(authorMapper::toAuthorDTO)
                .collect(Collectors.toList());
    }

    @Override
    public AuthorDTO getAuthorById(Long id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Author not found with id: " + id));
        return authorMapper.toAuthorDTO(author);
    }

    @Override
    public AuthorDTO createAuthor(AuthorDTO authorDTO) {
        Author author = authorMapper.toAuthor(authorDTO);
        return authorMapper.toAuthorDTO(authorRepository.save(author));
    }

    @Override
    public AuthorDTO updateAuthor(Long id, AuthorDTO authorDTO) {
        Author existingAuthor = authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Author not found with id: " + id));

        existingAuthor.setName(authorDTO.getName());
        existingAuthor.setDateOfBirth(authorDTO.getDateOfBirth());

        Author updatedAuthor = authorRepository.save(existingAuthor);
        return authorMapper.toAuthorDTO(updatedAuthor);
    }

    @Override
    public void deleteAuthor(Long id) {
        if (!authorRepository.existsById(id)) {
            throw new ResourceNotFoundException("Author not found with id: " + id);
        }
        authorRepository.deleteById(id);
    }

    @Override
    public List<AuthorDTO> searchAuthorsByName(String name) {
        return authorRepository.findByNameContaining(name).stream()
                .map(authorMapper::toAuthorDTO)
                .collect(Collectors.toList());
    }
}
