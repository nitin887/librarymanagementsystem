package com.librarymanagementsystem.librarymanagementsystem.controller;

import com.librarymanagementsystem.librarymanagementsystem.dto.BorrowingRecordDTO;
import com.librarymanagementsystem.librarymanagementsystem.service.BorrowingRecordService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing borrowing records in the library.
 * Provides endpoints for borrowing and returning books, as well as retrieving borrowing records.
 */
@RestController
@RequestMapping("/api/borrowing-records")
public class BorrowingRecordController {

    private final BorrowingRecordService borrowingRecordService;

    /**
     * Constructs a new BorrowingRecordController with the specified BorrowingRecordService.
     *
     * @param borrowingRecordService The service for handling borrowing record-related operations.
     */
    public BorrowingRecordController(BorrowingRecordService borrowingRecordService) {
        this.borrowingRecordService = borrowingRecordService;
    }

    /**
     * Retrieves a list of all borrowing records.
     *
     * @return A ResponseEntity containing a list of BorrowingRecordDTOs and an OK status.
     */
    @GetMapping
    @PreAuthorize("hasAnyRole('PATRON', 'LIBRARIAN', 'ADMIN')")
    public ResponseEntity<List<BorrowingRecordDTO>> getAllBorrowingRecords() {
        return ResponseEntity.ok(borrowingRecordService.getAllBorrowingRecords());
    }

    /**
     * Retrieves a specific borrowing record by its ID.
     *
     * @param id The ID of the borrowing record to retrieve.
     * @return A ResponseEntity containing the BorrowingRecordDTO and an OK status.
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('PATRON', 'LIBRARIAN', 'ADMIN')")
    public ResponseEntity<BorrowingRecordDTO> getBorrowingRecordById(@PathVariable Long id) {
        return ResponseEntity.ok(borrowingRecordService.getBorrowingRecordById(id));
    }

    /**
     * Borrows a book for a patron.
     *
     * @param bookId   The ID of the book to borrow.
     * @param patronId The ID of the patron borrowing the book.
     * @return A ResponseEntity containing the created BorrowingRecordDTO and a CREATED status.
     */
    @PostMapping("/borrow")
    @PreAuthorize("hasAnyRole('LIBRARIAN', 'ADMIN')")
    public ResponseEntity<BorrowingRecordDTO> borrowBook(
            @RequestParam Long bookId,
            @RequestParam Long patronId) {
        return new ResponseEntity<>(borrowingRecordService.borrowBook(bookId, patronId), HttpStatus.CREATED);
    }

    /**
     * Returns a borrowed book.
     *
     * @param id The ID of the borrowing record.
     * @return A ResponseEntity containing the updated BorrowingRecordDTO and an OK status.
     */
    @PutMapping("/{id}/return")
    @PreAuthorize("hasAnyRole('LIBRARIAN', 'ADMIN')")
    public ResponseEntity<BorrowingRecordDTO> returnBook(@PathVariable Long id) {
        return ResponseEntity.ok(borrowingRecordService.returnBook(id));
    }

    /**
     * Retrieves all borrowing records for a specific book.
     *
     * @param bookId The ID of the book.
     * @return A ResponseEntity containing a list of BorrowingRecordDTOs and an OK status.
     */
    @GetMapping("/book/{bookId}")
    @PreAuthorize("hasAnyRole('PATRON', 'LIBRARIAN', 'ADMIN')")
    public ResponseEntity<List<BorrowingRecordDTO>> getBorrowingRecordsByBook(@PathVariable Long bookId) {
        return ResponseEntity.ok(borrowingRecordService.getBorrowingRecordsByBook(bookId));
    }

    /**
     * Retrieves all borrowing records for a specific patron.
     *
     * @param patronId The ID of the patron.
     * @return A ResponseEntity containing a list of BorrowingRecordDTOs and an OK status.
     */
    @GetMapping("/patron/{patronId}")
    @PreAuthorize("hasAnyRole('PATRON', 'LIBRARIAN', 'ADMIN')")
    public ResponseEntity<List<BorrowingRecordDTO>> getBorrowingRecordsByPatron(@PathVariable Long patronId) {
        return ResponseEntity.ok(borrowingRecordService.getBorrowingRecordsByPatron(patronId));
    }

    /**
     * Retrieves a list of all overdue books.
     *
     * @return A ResponseEntity containing a list of overdue BorrowingRecordDTOs and an OK status.
     */
    @GetMapping("/overdue")
    @PreAuthorize("hasAnyRole('LIBRARIAN', 'ADMIN')")
    public ResponseEntity<List<BorrowingRecordDTO>> getOverdueBooks() {
        return ResponseEntity.ok(borrowingRecordService.getOverdueBooks());
    }
}
