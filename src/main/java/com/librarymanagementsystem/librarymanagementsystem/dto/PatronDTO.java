package com.librarymanagementsystem.librarymanagementsystem.dto;

/**
 * Data Transfer Object for Patron entities.
 * This class is used to transfer patron data between the client and the server.
 */
public class PatronDTO {

    /**
     * The unique identifier for the patron.
     */
    private Long id;
    /**
     * The name of the patron.
     */
    private String name;
    /**
     * The patron's contact information.
     */
    private String contactInfo;

    // Getters and setters

    /**
     * Gets the patron's ID.
     * @return The patron's ID.
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the patron's ID.
     * @param id The patron's ID.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the patron's name.
     * @return The patron's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the patron's name.
     * @param name The patron's name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the patron's contact information.
     * @return The patron's contact information.
     */
    public String getContactInfo() {
        return contactInfo;
    }

    /**
     * Sets the patron's contact information.
     * @param contactInfo The patron's contact information.
     */
    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }
}
