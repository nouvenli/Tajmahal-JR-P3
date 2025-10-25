package com.openclassrooms.tajmahal.ui.reviews;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.openclassrooms.tajmahal.data.repository.RestaurantRepository;
import com.openclassrooms.tajmahal.domain.model.Restaurant;
import com.openclassrooms.tajmahal.domain.model.Review;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class ReviewViewModel extends ViewModel {

    private final RestaurantRepository restaurantRepository;

    // --- LiveData for UI state management ---

    /**
     * LiveData for comment validation error.
     * Contains error message if invalid, null if valid.
     */
    private final MutableLiveData<String> commentError = new MutableLiveData<>();

    /**
     * LiveData for rating validation error.
     * Contains error message if invalid, null if valid.
     */
    private final MutableLiveData<String> ratingError = new MutableLiveData<>();

    /**
     * Single-use event to signal successful review addition to the View.
     * Used to trigger UI reset (form fields, show toast, etc.).
     */
    private final MutableLiveData<Boolean> reviewAddSuccessEvent = new MutableLiveData<>();

    // --- Constructor ---

    /**
     * Constructs a ReviewViewModel with the required repository.
     *
     * @param restaurantRepository the repository for accessing restaurant and review data
     */
    @Inject
    public ReviewViewModel(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    // --- Data access méthods ---

    /**
     * Retrieves the restaurant information.
     *
     * @return LiveData containing restaurant details
     */
    public LiveData<Restaurant> getRestaurant() {
        return restaurantRepository.getRestaurant();
    }

    /**
     * Retrieves the list of customer reviews.
     *
     * @return LiveData containing the list of reviews
     */
    public LiveData<List<Review>> getReviews() {
        return restaurantRepository.getReviews();
    }

    /**
     * Gets the comment validation error LiveData.
     *
     * @return LiveData containing error message or null if valid
     */
    public LiveData<String> getCommentError() {
        return commentError;
    }

    /**
     * Gets the rating validation error LiveData.
     *
     * @return LiveData containing error message or null if valid
     */
    public LiveData<String> getRatingError() {
        return ratingError;
    }

    /**
     * Gets the review addition success event LiveData.
     *
     * @return LiveData signaling successful review addition
     */
    public LiveData<Boolean> getReviewAddSuccessEvent() {
        return reviewAddSuccessEvent;
    }

    // --- User data methods ---

    /**
     * Gets the current user's name.
     * TODO: Replace with actual user repository when available.
     *
     * @return the current user's name
     */

    public String getCurrentUserName() {
        return "Manon Garcia";
    }

    /**
     * Gets the current user's profile picture URL.
     * TODO: Replace with actual user repository when available.
     *
     * @return the current user's profile picture URL
     */

    public String getCurrentUserPicture() {
        return "https://xsgames.co/randomusers/assets/avatars/female/20.jpg";
    }

    // --- Business logic & state management ---

    /**
     * Validates user input and adds the review if valid.
     * Updates error LiveData and success event accordingly.
     *
     * @param comment the review comment text
     * @param rating  the review rating (1-5)
     */

    public void processNewReview(String comment, int rating) {
        // Validate comment
        if (comment.isEmpty()) {
            commentError.setValue("Désolés, le commentaire ne peut pas être vide");
            ratingError.setValue(null); // Clear rating error
            return;
        }
        commentError.setValue(null); // Clear previous comment error

        // Validate rating
        if (rating == 0) {
            ratingError.setValue("Merci de donner une note");
            return;
        }
        ratingError.setValue(null); // Clear previous rating error

        // Review is valid: prepare user data
        String username = getCurrentUserName();
        String picture = getCurrentUserPicture();

        // Add review via repository
        addReview(username, picture, comment, rating);

        // Emit success event for the View to react
        reviewAddSuccessEvent.setValue(true);
    }
    /**
     * Adds a new review to the repository.
     *
     * @param username the reviewer's username
     * @param picture  the reviewer's profile picture URL
     * @param comment  the review comment
     * @param rate     the review rating
     */
    private void addReview(String username, String picture, String comment, int rate) {
        Review newReview = new Review(username, picture, comment, rate);
        restaurantRepository.addReview(newReview);
    }

    /**
     * Resets the success event to prevent success message on view recreation.
     * Used to avoid displaying success toast after screen rotation.
     */
    public void resetSuccessEvent() {
        reviewAddSuccessEvent.setValue(false);
    }
}

