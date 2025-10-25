package com.openclassrooms.tajmahal.domain.model;

/**
 * Represents aggregated statistics calculated from customer reviews.
 * <p>
 * This business model encapsulates all computed statistics about reviews,
 * providing data ready to be displayed in the UI.
 * </p>
 */
public class ReviewStats {

    // --- Fields ---

    private final float averageRating;
    private final int reviewCount;
    private final int[] ratingDistribution;
    private final int[] percentDistribution;

    // --- Constructor ---

    /**
     * Constructs a new ReviewStats instance with calculated statistics.
     *
     * @param averageRating       the average rating across all reviews
     * @param reviewCount         the total number of reviews
     * @param ratingDistribution  array containing count of reviews for each rating (1-5 stars)
     * @param percentDistribution array containing percentage of reviews for each rating (1-5 stars)
     */
    public ReviewStats(float averageRating, int reviewCount,
                       int[] ratingDistribution, int[] percentDistribution) {
        this.averageRating = averageRating;
        this.reviewCount = reviewCount;
        this.ratingDistribution = ratingDistribution;
        this.percentDistribution = percentDistribution;
    }

    // --- Getters ---

    /**
     * Gets the average rating.
     *
     * @return the average rating value
     */
    public float getAverageRating() {
        return averageRating;
    }

    /**
     * Gets the total number of reviews.
     *
     * @return the review count
     */
    public int getReviewCount() {
        return reviewCount;
    }

    /**
     * Gets the distribution of ratings by count.
     * <p>
     * The array contains the number of reviews for each rating level (1-5 stars).
     * For example, index 0 represents 1-star reviews, index 4 represents 5-star reviews.
     * </p>
     *
     * @return array of review counts per rating level
     */
    public int[] getRatingDistribution() {
        return ratingDistribution;
    }

    /**
     * Gets the distribution of ratings by percentage.
     * <p>
     * The array contains the percentage of reviews for each rating level (1-5 stars).
     * For example, index 0 represents percentage of 1-star reviews.
     * </p>
     *
     * @return array of review percentages per rating level
     */
    public int[] getPercentDistribution() {
        return percentDistribution;
    }
}
