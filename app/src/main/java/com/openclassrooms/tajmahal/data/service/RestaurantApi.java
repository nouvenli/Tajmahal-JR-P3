package com.openclassrooms.tajmahal.data.service;

import androidx.lifecycle.LiveData;

import com.openclassrooms.tajmahal.domain.model.Restaurant;
import com.openclassrooms.tajmahal.domain.model.Review;

import java.util.List;

/**
 * Interface for fetching restaurant data.
 * <p>
 * Represents the API for accessing restaurant information. In a real-world application,
 * this interface might be implemented using a library like Retrofit, and would include annotations
 * specifying the HTTP methods (like GET, POST), the endpoint URL, and other API-specific details.
 * </p>
 *
 * For beginners: An interface in Java is a blueprint of a class or you can say it is a collection of
 * abstract methods and static constants. In an interface, each method is public and abstract but it does
 * not contain any constructor. An interface is not a class. Writing an interface is similar to writing a class,
 * but they are two different concepts. A class describes the attributes and behaviors of an object.
 * An interface contains behaviors that a class implements.
 *
 * <p>
 * Here, {@link RestaurantApi} provides a method to get details of a restaurant.
 * </p>
 *
 * @see Restaurant
 */
public interface RestaurantApi {

    /**
     * Retrieves the details of a restaurant.
     * <p>
     * This method will usually be connected to a network call or database query in its
     * implementing class, fetching the required restaurant information.
     * </p>
     *
     * @return The {@link Restaurant} object containing all the details of the restaurant.
     */
    Restaurant getRestaurant();

    /**
     * Retrieves all the reviews of the restaurant.
     * <p>
     * This method will usually be connected to a network call or database query in its
     * implementing class, fetching the list of the existing reviews.
     *
     */
    List<Review> getReviews();

    /**
     * this method fetching the new reviews
     *
      * @param review
     */
    void addReview(Review review);
}
