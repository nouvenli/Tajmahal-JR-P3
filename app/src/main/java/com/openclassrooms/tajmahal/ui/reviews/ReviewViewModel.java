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

    // --- LiveData ---

    // recupère info restaurant
    public LiveData<Restaurant> getRestaurant() { return restaurantRepository.getRestaurant();}

    // Pour le commentaire : null si valide, message d'erreur si invalide
    private final MutableLiveData<String> commentError = new MutableLiveData<>();

    public LiveData<String> getCommentError() {
        return commentError;
    }

    // Pour la note : null si valide, message d'erreur si invalide
    private final MutableLiveData<String> ratingError = new MutableLiveData<>();

    public LiveData<String> getRatingError() {
        return ratingError;
    }

    // Événement à usage unique pour signaler le succès à la View (pour réinitialiser le formulaire)
    private final MutableLiveData<Boolean> reviewAddSuccessEvent = new MutableLiveData<>();

    public LiveData<Boolean> getReviewAddSuccessEvent() {
        return reviewAddSuccessEvent;
    }


    // --- Constructor & Data access ---

    @Inject
    public ReviewViewModel(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }
    /**
     * Retrieves customer review details
     * @return LiveData object containing review details.
     */
    public LiveData<List<Review>> getReviews() {
        return restaurantRepository.getReviews();
    }

    // --- Business Logic & State Management ---

    /**
     * Valide les entrées utilisateur et ajoute l'avis si entrées valides.
     * Met à jour les LiveData d'erreur et de succès.
     */
    public void processNewReview(String comment, int rating) {
        // 1. Validation du Commentaire
        if (comment.isEmpty()) {
            commentError.setValue("Désolés, le commentaire ne peut pas être vide");
            ratingError.setValue(null); // Clear rating error
            return;
        }
        commentError.setValue(null); // Clear previous comment error

        // 2. Validation de la Note
        if (rating == 0) {
            ratingError.setValue("Merci de donner une note");
            return;
        }
        ratingError.setValue(null); // Clear previous rating error

        // 3. L'avis est valide: préparation des données utilisateur (à remplacer par un Repository)
        String username = getCurrentUserName();
        String picture = getCurrentUserPicture();;

        // 4. Ajout de l'avis via le Repository
        addReview(username, picture, comment, rating);

        // 5. Émission de l'événement de succès (pour que la View réagisse)
        reviewAddSuccessEvent.setValue(true);
    }

    /**
     * Adds a new review to the list with using the repository
     */
    private void addReview(String username, String picture, String comment, int rate) {
        Review newReview = new Review(username, picture, comment, rate);
        restaurantRepository.addReview(newReview);
    }

    /**
     * nom et image utilisateur courant
     */
    public String getCurrentUserName() {
        return "Manon Garcia";
    }

    public String getCurrentUserPicture() {
        return "https://xsgames.co/randomusers/assets/avatars/female/20.jpg";
    }

    /**
     * reset de l'événement de succès pour éviter un message de succès en cas de retour à la vue
     */
    public void resetSuccessEvent() {
        reviewAddSuccessEvent.setValue(false);
    }
}

