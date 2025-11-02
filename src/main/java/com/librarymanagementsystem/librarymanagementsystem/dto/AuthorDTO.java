package com.librarymanagementsystem.librarymanagementsystem.dto;

import java.time.LocalDate;

/**
 * Data Transfer Object for Author entities.
 * This class is used to transfer author data between the client and the server.
 */
public class AuthorDTO {

    /**
     * The unique identifier for the author.
     */
    private Long id;
    /**
     * The name of the author.
     */
    private String name;
    /**
     * The author's date of birth.
     */
    private LocalDate dateOfBirth;

    // Getters and setters

    /**
     * Gets the author's ID.
     * @return The author's ID.
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the author's ID.
     * @param id The author's ID.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the author's name.
     * @return The author's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the author's name.
     * @param name The author's name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the author's date of birth.
     * @return The author's date of birth.
     */
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    /**
     * Sets the author's date of birth.
     * @param dateOfBirth The author's date of birth.
     */
    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}
