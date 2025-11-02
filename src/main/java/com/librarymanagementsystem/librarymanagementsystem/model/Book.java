package com.librarymanagementsystem.librarymanagementsystem.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDate;

/**
 * Represents a book in the library.
 * This is a JPA entity class that is mapped to the "book" table in the database.
 */
@Entity
public class Book {

    /**
     * The unique identifier for the book.
     * This is the primary key and is auto-generated.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The title of the book.
     */
    private String title;
    /**
     * The author of the book.
     */
    private String author;
    /**
     * The International Standard Book Number (ISBN) of the book.
     */
    private String isbn;
    /**
     * The date the book was published.
     */
    private LocalDate publishedDate;

    // Getters and setters

    /**
     * Gets the book's ID.
     * @return The book's ID.
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the book's ID.
     * @param id The book's ID.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the book's title.
     * @return The book's title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the book's title.
     * @param title The book's title.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the book's author.
     * @return The book's author.
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Sets the book's author.
     * @param author The book's author.
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * Gets the book's ISBN.
     * @return The book's ISBN.
     */
    public String getIsbn() {
        return isbn;
    }

    /**
     * Sets the book's ISBN.
     * @param isbn The book's ISBN.
     */
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    /**
     * Gets the book's published date.
     * @return The book's published date.
     */
    public LocalDate getPublishedDate() {
        return publishedDate;
    }

    /**
     * Sets the book's published date.
     * @param publishedDate The book's published date.
     */
    public void setPublishedDate(LocalDate publishedDate) {
        this.publishedDate = publishedDate;
    }
}
