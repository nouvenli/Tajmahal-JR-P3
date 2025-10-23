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
 * It communicates with the {@link RestaurantRepository} to fetch restaurant details and provides
 * utility methods related to the restaurant UI.
 * <p>
 * This ViewModel is integrated with Hilt for dependency injection.
 */
@HiltViewModel
public class DetailsViewModel extends ViewModel {

    // --- Repositories ---
    private final RestaurantRepository restaurantRepository;

    // --- LiveData  computed ---
    private final MediatorLiveData<ReviewStats> reviewStats = new MediatorLiveData<>();


    /**
     * Constructor that Hilt will use to create an instance of MainViewModel.
     * * @param restaurantRepository The repository which will provide restaurant data.
     */
    @Inject
    public DetailsViewModel(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;


    //  LiveData computed
        reviewStats.addSource(getReviews(), reviews -> {
            if (reviews == null || reviews.isEmpty()) {
                reviewStats.setValue(new ReviewStats(0f, 0, new int[5], new int[5]));
                return;
            }

            float sum = 0f;
            int[] distribution = new int[5];
            for (Review r : reviews) {
                int rate = r.getRate();
                if (rate >= 1 && rate <= 5) {
                    distribution[rate - 1]++;
                    sum += rate;
                }
            }

            int count = reviews.size();
            float average = sum / count;

            int[] percent = new int[5];
            for (int i = 0; i < 5; i++) {
                percent[i] = (int) ((distribution[i] * 100f) / count);
            }

            reviewStats.setValue(new ReviewStats(average, count, distribution, percent));
        });
    }

    // --- exposed LiveData ---

    /**
     * Fetches the details of the Taj Mahal restaurant.
     * @return LiveData object containing the details of the Taj Mahal restaurant.
     */
    public LiveData<Restaurant> getTajMahalRestaurant() {
        return restaurantRepository.getRestaurant();
    }

    public LiveData<List<Review>> getReviews() {
        return restaurantRepository.getReviews();
    }

    public LiveData<ReviewStats> getReviewStats() {
        return reviewStats;
    }

    /**
     * Retrieves the current day of the week in French.
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

