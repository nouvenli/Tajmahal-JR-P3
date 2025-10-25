package com.openclassrooms.tajmahal.domain.model;

import java.util.Date;
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

    //--- getters and setters for fields ---

    /**
     * Getters - Returns for fields : username, picture, comment, rate
     *
     * @return
     * a String representing the username
     * a String representing the picture's URL or path
     * a String containing the feedback or comment
     * an integer representing the rating value
     */

    public String getUsername() {
        return username;
    }
    public String getPicture() {
        return picture;
    }
    public String getComment() {
        return comment;
    }
    public int getRate() {
        return rate;
    }


    /**
     * Setters - Sets for fields : username, picture, comment, rate
     *
     * @param
     * username the name or updates the profile picture of the reviewer
     * picture the profile picture's URL or path to be set
     * comment the comment or feedback to be set
     * rate the rating value to be set
     */
    public void setUsername(String username) {
        this.username = username;
    }
    public void setPicture(String picture) {
        this.picture = picture;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
    public void setRate(int rate) {this.rate = rate; }



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
     * @return the generated hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(username, picture, comment, rate);
    }
}
