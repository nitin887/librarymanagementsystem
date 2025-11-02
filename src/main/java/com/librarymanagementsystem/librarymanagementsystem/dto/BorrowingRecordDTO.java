package com.librarymanagementsystem.librarymanagementsystem.dto;

import java.time.LocalDate;

/**
 * Data Transfer Object for BorrowingRecord entities.
 * This class is used to transfer borrowing record data between the client and the server.
 */
public class BorrowingRecordDTO {

    /**
     * The unique identifier for the borrowing record.
     */
    private Long id;
    /**
     * The ID of the borrowed book.
     */
    private Long bookId;
    /**
     * The ID of the patron who borrowed the book.
     */
    private Long patronId;
    /**
     * The date the book was borrowed.
     */
    private LocalDate borrowDate;
    /**
     * The date the book is due to be returned.
     */
    private LocalDate dueDate;
    /**
     * The date the book was actually returned.
     */
    private LocalDate returnDate;

    // Getters and setters

    /**
     * Gets the borrowing record's ID.
     * @return The borrowing record's ID.
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the borrowing record's ID.
     * @param id The borrowing record's ID.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the ID of the borrowed book.
     * @return The book's ID.
     */
    public Long getBookId() {
        return bookId;
    }

    /**
     * Sets the ID of the borrowed book.
     * @param bookId The book's ID.
     */
    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    /**
     * Gets the ID of the patron.
     * @return The patron's ID.
     */
    public Long getPatronId() {
        return patronId;
    }

    /**
     * Sets the ID of the patron.
     * @param patronId The patron's ID.
     */
    public void setPatronId(Long patronId) {
        this.patronId = patronId;
    }

    /**
     * Gets the borrow date.
     * @return The borrow date.
     */
    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    /**
     * Sets the borrow date.
     * @param borrowDate The borrow date.
     */
    public void setBorrowDate(LocalDate borrowDate) {
        this.borrowDate = borrowDate;
    }

    /**
     * Gets the due date.
     * @return The due date.
     */
    public LocalDate getDueDate() {
        return dueDate;
    }

    /**
     * Sets the due date.
     * @param dueDate The due date.
     */
    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    /**
     * Gets the return date.
     * @return The return date.
     */
    public LocalDate getReturnDate() {
        return returnDate;
    }

    /**
     * Sets the return date.
     * @param returnDate The return date.
     */
    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }
}
