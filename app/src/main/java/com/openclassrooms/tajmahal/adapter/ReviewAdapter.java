package com.openclassrooms.tajmahal.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.openclassrooms.tajmahal.R;
import com.openclassrooms.tajmahal.domain.model.Review;

/**
 * Adapter class for RecyclerView that manage list of reviews
 * Bind data of reviews using ViewHolders
 */
public class ReviewAdapter extends ListAdapter<Review, ReviewAdapter.ViewHolder> {

    // --- Constructor ---

    /**
     * Constructs a new ReviewAdapter.
     * Uses ItemCallback for efficient list comparison.
     */
    public ReviewAdapter() {
        super(new ItemCallback());
    }

    // --- RecyclerView.Adapter methods ---

    /**
     * Creates a new ViewHolder for a review item.
     *
     * @param parent   the parent ViewGroup
     * @param viewType the view type (unused here)
     * @return a new ViewHolder instance
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_review, parent, false);
        return new ViewHolder(itemView);
    }

    /**
     * Binds review data to the ViewHolder at the specified position.
     *
     * @param holder   the ViewHolder to bind data to
     * @param position the position in the list
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Appelle holder.bind() avec l'élément à la position donnée
        holder.bind(getItem(position));
    }

    // --- ViewHolder ---

    /**
     * ViewHolder class that holds references to the views for each review item.
     */

    static class ViewHolder extends RecyclerView.ViewHolder {
        // Déclare les variables pour chaque élément du layout
        private final TextView tvReviewerName;
        private final TextView tvReviewerComment;
        private final RatingBar rbReviewRating;
        private final ImageView ivReviewerAvatar;

        /**
         * Constructs a ViewHolder and initializes view references.
         *
         * @param itemView the item view
         */
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvReviewerName = itemView.findViewById(R.id.tvReviewerName);
            tvReviewerComment = itemView.findViewById(R.id.tvReviewerComment);
            rbReviewRating = itemView.findViewById(R.id.rbReviewRating);
            ivReviewerAvatar = itemView.findViewById(R.id.ivReviewerAvatar);
        }

        /**
         * Binds review data to the views.
         *
         * @param review the review to display
         */
        public void bind(Review review) {
            // Remplit les TextView, ImageView, RatingBar avec les données de review
            tvReviewerName.setText(review.getUsername());
            tvReviewerComment.setText(review.getComment());
            rbReviewRating.setRating(review.getRate());
            Glide.with(itemView.getContext())
                    .load(review.getPicture())
                    .circleCrop()
                    .into(ivReviewerAvatar);
        }
    }

    // --- DiffUtil callback ---

    /**
     * Callback for calculating the difference between two lists of reviews.
     * Used by ListAdapter for efficient list updates.
     */
    private static class ItemCallback extends DiffUtil.ItemCallback<Review> {

        /**
         * Checks if two review items represent the same object.
         * Compares by reference (memory address).
         *
         * @param oldItem the old review
         * @param newItem the new review
         * @return true if they are the same object
         */
        @Override
        public boolean areItemsTheSame(@NonNull Review oldItem, @NonNull Review newItem) {
            return oldItem == newItem;
        }

        /**
         * Checks if two review items have the same content.
         * Compares all fields of the review objects.
         *
         * @param oldItem the old review
         * @param newItem the new review
         * @return true if they have the same content
         */
        @Override
        public boolean areContentsTheSame(@NonNull Review oldItem, @NonNull Review newItem) {
            return oldItem.equals(newItem);
        }

    }
}