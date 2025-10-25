package com.openclassrooms.tajmahal.domain.model;

import java.util.Objects;


/**
 * Represents a user review.
 * This class encapsulates all the details of a review, including the username of the reviewer,
 * their profile picture, the comment they left, and the rating they gave.
 */
public class Review {

    //--- Fields ---

    private String username;
    private String picture;
    private String comment;
    private int rate;

    // --- Constructor ---

    /**
     * Constructs a new Review instance.
     *
     * @param username the name of the user leaving the review
     * @param picture  the profile picture URL or path of the user
     * @param comment  the feedback or comment from the user
     * @param rate     the rating given by the user
     */
    public Review(String username, String picture, String comment, int rate) {
        this.username = username;
        this.picture = picture;
        this.comment = comment;
        this.rate = rate;
    }

    //--- getters  ---

    /**
     * Gets the username of the reviewer.
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Gets the profile picture URL or path.
     *
     * @return the picture URL or path
     */
    public String getPicture() {
        return picture;
    }

    /**
     * Gets the comment or feedback.
     *
     * @return the comment text
     */
    public String getComment() {
        return comment;
    }

    /**
     * Gets the rating value.
     *
     * @return the rating (integer value)
     */
    public int getRate() {
        return rate;
    }

    // --- Setters ---

    /**
     * Sets the username of the reviewer.
     *
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Sets the profile picture URL or path.
     *
     * @param picture the picture URL or path to set
     */
    public void setPicture(String picture) {
        this.picture = picture;
    }

    /**
     * Sets the comment or feedback.
     *
     * @param comment the comment to set
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * Sets the rating value.
     *
     * @param rate the rating to set
     */
    public void setRate(int rate) {
        this.rate = rate;
    }

    // --- Object methods ---

    /**
     * Compares this review with another object for equality.
     * Two reviews are considered equal if all their fields are identical.
     *
     * @param o the object to be compared with
     * @return true if the objects are equal, false otherwise
     */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Review review = (Review) o;
        return rate == review.rate && Objects.equals(username, review.username) && Objects.equals(picture, review.picture) && Objects.equals(comment, review.comment);
    }

    /**
     * Generates a hash code for this review based on its fields.
     *
     * @return the generated hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(username, picture, comment, rate);
    }
}
