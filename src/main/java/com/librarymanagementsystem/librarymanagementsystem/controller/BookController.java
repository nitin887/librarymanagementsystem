package com.librarymanagementsystem.librarymanagementsystem.controller;

import com.librarymanagementsystem.librarymanagementsystem.dto.BookDTO;
import com.librarymanagementsystem.librarymanagementsystem.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing books in the library.
 * Provides endpoints for creating, retrieving, updating, and deleting books.
 */
@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    /**
     * Constructs a new BookController with the specified BookService.
     *
     * @param bookService The service for handling book-related operations.
     */
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    /**
     * Retrieves a list of all books in the library.
     *
     * @return A ResponseEntity containing a list of BookDTOs and an OK status.
     */
    @GetMapping
    @PreAuthorize("hasAnyRole('PATRON', 'LIBRARIAN', 'ADMIN','USER')")
    public ResponseEntity<List<BookDTO>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    /**
     * Retrieves a specific book by its ID.
     *
     * @param id The ID of the book to retrieve.
     * @return A ResponseEntity containing the BookDTO and an OK status.
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('PATRON', 'LIBRARIAN', 'ADMIN','USER')")
    public ResponseEntity<BookDTO> getBookById(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.getBookById(id));
    }

    /**
     * Searches for books by their title.
     *
     * @param title The title to search for.
     * @return A ResponseEntity containing a list of matching BookDTOs and an OK status.
     */
    @GetMapping("/search")
    @PreAuthorize("hasAnyRole('PATRON', 'LIBRARIAN', 'ADMIN','USER')")
    public ResponseEntity<List<BookDTO>> searchBooksByTitle(@RequestParam String title) {
        return ResponseEntity.ok(bookService.searchBooksByTitle(title));
    }

    /**
     * Creates a new book in the library.
     *
     * @param bookDTO The BookDTO containing the details of the book to create.
     * @return A ResponseEntity containing the created BookDTO and a CREATED status.
     */
    @PostMapping
    @PreAuthorize("hasAnyRole('LIBRARIAN', 'ADMIN')")
    public ResponseEntity<BookDTO> createBook(@RequestBody BookDTO bookDTO) {
        return new ResponseEntity<>(bookService.createBook(bookDTO), HttpStatus.CREATED);
    }

    /**
     * Updates an existing book's information.
     *
     * @param id      The ID of the book to update.
     * @param bookDTO The BookDTO containing the updated details.
     * @return A ResponseEntity containing the updated BookDTO and an OK status.
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('LIBRARIAN', 'ADMIN')")
    public ResponseEntity<BookDTO> updateBook(@PathVariable Long id, @RequestBody BookDTO bookDTO) {
        return ResponseEntity.ok(bookService.updateBook(id, bookDTO));
    }

    /**
     * Deletes a book from the library.
     *
     * @param id The ID of the book to delete.
     * @return A ResponseEntity with a NO_CONTENT status.
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('LIBRARIAN', 'ADMIN')")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
}
