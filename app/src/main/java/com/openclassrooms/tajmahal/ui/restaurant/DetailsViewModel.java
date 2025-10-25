package com.openclassrooms.tajmahal.ui.restaurant;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.openclassrooms.tajmahal.R;
import com.openclassrooms.tajmahal.data.repository.RestaurantRepository;
import com.openclassrooms.tajmahal.domain.model.Restaurant;
import com.openclassrooms.tajmahal.domain.model.Review;

import com.openclassrooms.tajmahal.domain.model.ReviewStats;

import javax.inject.Inject;

import java.util.Calendar;
import java.util.List;

import dagger.hilt.android.lifecycle.HiltViewModel;

/**
 * MainViewModel is responsible for preparing and managing the data for the {@link DetailsFragment}.
 * It communicates with the {@link RestaurantRepository} to fetch restaurant details and
 * calculate aggregated statistics about reviews.
 * <p>
 * This ViewModel is integrated with Hilt for dependency injection.
 */
@HiltViewModel
public class DetailsViewModel extends ViewModel {

    private final RestaurantRepository restaurantRepository;

    // --- Computed LiveData ---

    /**
     * MediatorLiveData that automatically calculates review statistics
     * whenever the review list changes.
     */
    private final MediatorLiveData<ReviewStats> reviewStats = new MediatorLiveData<>();

    // --- Constructor ---

    /**
     * Constructs a DetailsViewModel with the required repository.
     * Initializes the computed LiveData for review statistics.
     *
     * @param restaurantRepository the repository for accessing restaurant and review data
     */
    @Inject
    public DetailsViewModel(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;


        // Setup computed LiveData for review statistics
        reviewStats.addSource(getReviews(), reviews -> {
            // Handle empty or null reviews
            if (reviews == null || reviews.isEmpty()) {
                reviewStats.setValue(new ReviewStats(0f, 0, new int[5], new int[5]));
                return;
            }

            // Calculate rating distribution
            float sum = 0f;
            int[] distribution = new int[5];
            for (Review r : reviews) {
                int rate = r.getRate();
                if (rate >= 1 && rate <= 5) {
                    distribution[rate - 1]++;
                    sum += rate;
                }
            }

            // Calculate average rating
            int count = reviews.size();
            float average = sum / count;

            // Calculate percentage distribution
            int[] percent = new int[5];
            for (int i = 0; i < 5; i++) {
                percent[i] = (int) ((distribution[i] * 100f) / count);
            }
            // Update LiveData with calculated statistics
            reviewStats.setValue(new ReviewStats(average, count, distribution, percent));
        });
    }

    // --- Data access methods ---

    /**
     * Fetches the details of the Taj Mahal restaurant.
     *
     * @return LiveData object containing the details of the Taj Mahal restaurant.
     */
    public LiveData<Restaurant> getTajMahalRestaurant() {
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
     * Retrieves the calculated review statistics.
     * <p>
     * This LiveData is automatically updated whenever the review list changes.
     * It provides aggregated data including average rating, review count,
     * and rating distribution.
     * </p>
     *
     * @return LiveData containing the computed review statistics
     */
    public LiveData<ReviewStats> getReviewStats() {
        return reviewStats;
    }

    // --- Utility methods ---

    /**
     * Retrieves the current day of the week in French.
     *
     * @return A string representing the current day of the week in French.
     */
    public String getCurrentDay(Context context) {
        Calendar calendar = Calendar.getInstance();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        String dayString;

        switch (dayOfWeek) {
            case Calendar.MONDAY:
                dayString = context.getString(R.string.monday);
                break;
            case Calendar.TUESDAY:
                dayString = context.getString(R.string.tuesday);
                break;
            case Calendar.WEDNESDAY:
                dayString = context.getString(R.string.wednesday);
                break;
            case Calendar.THURSDAY:
                dayString = context.getString(R.string.thursday);
                break;
            case Calendar.FRIDAY:
                dayString = context.getString(R.string.friday);
                break;
            case Calendar.SATURDAY:
                dayString = context.getString(R.string.saturday);
                break;
            case Calendar.SUNDAY:
                dayString = context.getString(R.string.sunday);
                break;
            default:
                dayString = "";
        }
        return dayString;
    }

}

