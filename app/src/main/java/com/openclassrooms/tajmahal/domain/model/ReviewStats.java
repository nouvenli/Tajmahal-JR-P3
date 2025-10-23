package com.openclassrooms.tajmahal.domain.model;

/**
 * ReviewStats représente un objet métier regroupant
 * toutes les statistiques calculées sur les avis clients.
 * Elle sert à exposer des données prêtes à afficher à la vue.
 */
public class ReviewStats {

    private final float averageRating;
    private final int reviewCount;
    private final int[] ratingDistribution;
    private final int[] percentDistribution;

    public ReviewStats(float averageRating, int reviewCount,
                       int[] ratingDistribution, int[] percentDistribution) {
        this.averageRating = averageRating;
        this.reviewCount = reviewCount;
        this.ratingDistribution = ratingDistribution;
        this.percentDistribution = percentDistribution;
    }

    public float getAverageRating() {
        return averageRating;
    }

    public int getReviewCount() {
        return reviewCount;
    }

    public int[] getRatingDistribution() {
        return ratingDistribution;
    }

    public int[] getPercentDistribution() {
        return percentDistribution;
    }
}
