package com.librarymanagementsystem.librarymanagementsystem.controller;

import com.librarymanagementsystem.librarymanagementsystem.dto.PatronDTO;
import com.librarymanagementsystem.librarymanagementsystem.service.PatronService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing patrons in the library.
 * Provides endpoints for creating, retrieving, updating, and deleting patrons.
 */
@RestController
@RequestMapping("/api/patrons")
public class PatronController {

    private final PatronService patronService;

    /**
     * Constructs a new PatronController with the specified PatronService.
     *
     * @param patronService The service for handling patron-related operations.
     */
    public PatronController(PatronService patronService) {
        this.patronService = patronService;
    }

    /**
     * Retrieves a list of all patrons.
     *
     * @return A ResponseEntity containing a list of PatronDTOs and an OK status.
     */
    @GetMapping
    @PreAuthorize("hasAnyRole('PATRON', 'LIBRARIAN', 'ADMIN')")
    public ResponseEntity<List<PatronDTO>> getAllPatrons() {
        return ResponseEntity.ok(patronService.getAllPatrons());
    }

    /**
     * Retrieves a specific patron by their ID.
     *
     * @param id The ID of the patron to retrieve.
     * @return A ResponseEntity containing the PatronDTO and an OK status.
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('PATRON', 'LIBRARIAN', 'ADMIN')")
    public ResponseEntity<PatronDTO> getPatronById(@PathVariable Long id) {
        return ResponseEntity.ok(patronService.getPatronById(id));
    }

    /**
     * Searches for patrons by their name.
     *
     * @param name The name to search for.
     * @return A ResponseEntity containing a list of matching PatronDTOs and an OK status.
     */
    @GetMapping("/search")
    @PreAuthorize("hasAnyRole('PATRON', 'LIBRARIAN', 'ADMIN')")
    public ResponseEntity<List<PatronDTO>> searchPatronsByName(@RequestParam String name) {
        return ResponseEntity.ok(patronService.searchPatronsByName(name));
    }

    /**
     * Creates a new patron.
     *
     * @param patronDTO The PatronDTO containing the details of the patron to create.
     * @return A ResponseEntity containing the created PatronDTO and a CREATED status.
     */
    @PostMapping
    @PreAuthorize("hasAnyRole('LIBRARIAN', 'ADMIN')")
    public ResponseEntity<PatronDTO> createPatron(@RequestBody PatronDTO patronDTO) {
        return new ResponseEntity<>(patronService.createPatron(patronDTO), HttpStatus.CREATED);
    }

    /**
     * Updates an existing patron's information.
     *
     * @param id        The ID of the patron to update.
     * @param patronDTO The PatronDTO containing the updated details.
     * @return A ResponseEntity containing the updated PatronDTO and an OK status.
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('LIBRARIAN', 'ADMIN')")
    public ResponseEntity<PatronDTO> updatePatron(@PathVariable Long id, @RequestBody PatronDTO patronDTO) {
        return ResponseEntity.ok(patronService.updatePatron(id, patronDTO));
    }

    /**
     * Deletes a patron.
     *
     * @param id The ID of the patron to delete.
     * @return A ResponseEntity with a NO_CONTENT status.
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('LIBRARIAN', 'ADMIN')")
    public ResponseEntity<Void> deletePatron(@PathVariable Long id) {
        patronService.deletePatron(id);
        return ResponseEntity.noContent().build();
    }
}
