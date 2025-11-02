package com.librarymanagementsystem.librarymanagementsystem.controller;

import com.librarymanagementsystem.librarymanagementsystem.dto.AuthorDTO;
import com.librarymanagementsystem.librarymanagementsystem.service.AuthorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing authors in the library.
 * Provides endpoints for creating, retrieving, updating, and deleting authors.
 */
@RestController
@RequestMapping("/api/authors")
public class AuthorController {

    private final AuthorService authorService;

    /**
     * Constructs a new AuthorController with the specified AuthorService.
     *
     * @param authorService The service for handling author-related operations.
     */
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    /**
     * Retrieves a list of all authors.
     *
     * @return A ResponseEntity containing a list of AuthorDTOs and an OK status.
     */
    @GetMapping
    @PreAuthorize("hasAnyRole('PATRON', 'LIBRARIAN', 'ADMIN')")
    public ResponseEntity<List<AuthorDTO>> getAllAuthors() {
        return ResponseEntity.ok(authorService.getAllAuthors());
    }

    /**
     * Retrieves a specific author by their ID.
     *
     * @param id The ID of the author to retrieve.
     * @return A ResponseEntity containing the AuthorDTO and an OK status.
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('PATRON', 'LIBRARIAN', 'ADMIN')")
    public ResponseEntity<AuthorDTO> getAuthorById(@PathVariable Long id) {
        return ResponseEntity.ok(authorService.getAuthorById(id));
    }

    /**
     * Searches for authors by their name.
     *
     * @param name The name to search for.
     * @return A ResponseEntity containing a list of matching AuthorDTOs and an OK status.
     */
    @GetMapping("/search")
    @PreAuthorize("hasAnyRole('PATRON', 'LIBRARIAN', 'ADMIN')")
    public ResponseEntity<List<AuthorDTO>> searchAuthorsByName(@RequestParam String name) {
        return ResponseEntity.ok(authorService.searchAuthorsByName(name));
    }

    /**
     * Creates a new author.
     *
     * @param authorDTO The AuthorDTO containing the details of the author to create.
     * @return A ResponseEntity containing the created AuthorDTO and a CREATED status.
     */
    @PostMapping
    @PreAuthorize("hasAnyRole('LIBRARIAN', 'ADMIN')")
    public ResponseEntity<AuthorDTO> createAuthor(@RequestBody AuthorDTO authorDTO) {
        return new ResponseEntity<>(authorService.createAuthor(authorDTO), HttpStatus.CREATED);
    }

    /**
     * Updates an existing author's information.
     *
     * @param id        The ID of the author to update.
     * @param authorDTO The AuthorDTO containing the updated details.
     * @return A ResponseEntity containing the updated AuthorDTO and an OK status.
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('LIBRARIAN', 'ADMIN')")
    public ResponseEntity<AuthorDTO> updateAuthor(@PathVariable Long id, @RequestBody AuthorDTO authorDTO) {
        return ResponseEntity.ok(authorService.updateAuthor(id, authorDTO));
    }

    /**
     * Deletes an author.
     *
     * @param id The ID of the author to delete.
     * @return A ResponseEntity with a NO_CONTENT status.
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('LIBRARIAN', 'ADMIN')")
    public ResponseEntity<Void> deleteAuthor(@PathVariable Long id) {
        authorService.deleteAuthor(id);
        return ResponseEntity.noContent().build();
    }
}
