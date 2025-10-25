package com.openclassrooms.tajmahal;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.openclassrooms.tajmahal.data.repository.RestaurantRepository;
import com.openclassrooms.tajmahal.domain.model.Review;
import com.openclassrooms.tajmahal.ui.reviews.ReviewViewModel;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.never;
import static org.junit.Assert.assertTrue;

/**
 * Unit tests for ReviewViewModel.
 * <p>
 * Tests only the ViewModel logic, not the Repository.
 * Uses a mock RestaurantRepository to isolate the ViewModel behavior.
 * </p>
 */
public class ReviewViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Mock
    private RestaurantRepository mockRepository;

    private ReviewViewModel viewModel;

    /**
     * Sets up the test environment before each test.
     * Initializes mocks and creates a new ViewModel instance.
     */
    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
        viewModel = new ReviewViewModel(mockRepository);
    }

    /**
     * Test 1: Verifies that a valid review is added to the repository.
     * <p>
     * Note: The Fragment handles cases with empty comments or zero ratings,
     * so this test focuses on valid data.
     * </p>
     */
    @Test
    public void processNewReview_withValidData_shouldCallRepository() {
        // Arrange
        String comment = "Great restaurant!";
        int rating = 4;

        // Act
        viewModel.processNewReview(comment, rating);

        // Assert
        verify(mockRepository).addReview(any(Review.class));
    }

    /**
     * Test 2: Verifies that the repository is called with correct review data.
     * Checks that username, picture, comment, and rating are properly set.
     */
    @Test
    public void processNewReview_shouldCallRepositoryWithCorrectData() {
        // Arrange
        String comment = "Très bon service";
        int rating = 4;

        // Act
        viewModel.processNewReview(comment, rating);

        // Assert
        verify(mockRepository).addReview(argThat(review ->
                review.getUsername().equals("Manon Garcia") &&
                        review.getPicture().equals("https://xsgames.co/randomusers/assets/avatars/female/20.jpg") &&
                        review.getComment().equals(comment) &&
                        review.getRate() == rating
        ));
    }

    /**
     * Test 3: Verifies that reviews are correctly retrieved from the repository.
     * Checks that:
     * - The result is not null
     * - The correct number of reviews is returned
     * - Reviews are in the correct order
     */
    @Test
    public void getReviews_ReturnsReviews() {
        // Arrange - Setup mock data
        List<Review> expectedReviews = Arrays.asList(
                new Review("John Doe", "https://example.com/image.jpg", "Great restaurant!", 4),
                new Review("Jane Smith", "https://example.com/image2.jpg", "Excellent service!", 5));
        MutableLiveData<List<Review>> liveData = new MutableLiveData<>(expectedReviews);
        when(mockRepository.getReviews()).thenReturn(liveData);

        // Act - Call the method to test
        LiveData<List<Review>> result = viewModel.getReviews();

        // Assert - Verify the result
        assertNotNull(result);
        assertEquals(2, result.getValue().size());
        assertEquals("John Doe", result.getValue().get(0).getUsername());

    }

    /**
     * Test 4: Verifies that an error is set when the comment is empty.
     * Ensures that the repository is not called with invalid data.
     */
    @Test
    public void processNewReview_withEmptyComment_shouldSetCommentError() {
        // Arrange
        String comment = "";
        int rating = 5;

        // Act
        viewModel.processNewReview(comment, rating);

        // Assert
        assertNotNull(viewModel.getCommentError().getValue());
        assertEquals("Désolés, le commentaire ne peut pas être vide", viewModel.getCommentError().getValue());
        verify(mockRepository, never()).addReview(any(Review.class));
    }

    /**
     * Test 5: Verifies that an error is set when the rating is zero.
     * Ensures that the repository is not called with invalid data.
     */
    @Test
    public void processNewReview_withZeroRating_shouldSetRatingError() {
        // Arrange
        String comment = "Great restaurant!";
        int rating = 0;

        // Act
        viewModel.processNewReview(comment, rating);

        // Assert
        assertNotNull(viewModel.getRatingError().getValue());
        assertEquals("Merci de donner une note", viewModel.getRatingError().getValue());
        verify(mockRepository, never()).addReview(any(Review.class));
    }

    /**
     * Test 6: Verifies that a valid review triggers both repository call and success event.
     * Ensures that the success event is emitted after successfully adding a review.
     */
    @Test
    public void processNewReview_withValidData_shouldAddReviewAndEmitSuccess() {
        // Arrange
        String comment = "Excellent restaurant!";
        int rating = 5;

        // Act
        viewModel.processNewReview(comment, rating);

        // Assert
        verify(mockRepository).addReview(any(Review.class));
        assertTrue(viewModel.getReviewAddSuccessEvent().getValue());
    }

    /**
     * Test 7: Verifies that the success event can be reset.
     */
    @Test
    public void resetSuccessEvent_shouldSetSuccessEventToFalse() {
        // Arrange
        viewModel.processNewReview("Great!", 5); // Triggers success
        assertTrue(viewModel.getReviewAddSuccessEvent().getValue()); // Vérifie que c'est bien true

        // Act
        viewModel.resetSuccessEvent();

        // Assert
        assertFalse(viewModel.getReviewAddSuccessEvent().getValue()); // Vérifie que c'est bien false
    }
}
