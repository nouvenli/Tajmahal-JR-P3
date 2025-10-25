package com.openclassrooms.tajmahal.ui.reviews;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.openclassrooms.tajmahal.adapter.ReviewAdapter;
import com.openclassrooms.tajmahal.databinding.FragmentReviewBinding;

import java.util.ArrayList;

import dagger.hilt.android.AndroidEntryPoint;


/**
 * Fragment that displays a list of reviews for a restaurant and allows users to add new reviews.
 * uses ReviewViewModel to manage data and ReviewAdapter to display the list of reviews.
 */

@AndroidEntryPoint
public class ReviewFragment extends Fragment {

    private FragmentReviewBinding binding;
    private ReviewViewModel reviewViewModel;
    private ReviewAdapter adapter;

// ---lifecycle methods ---

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentReviewBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupViewModel();
        setupRecyclerView();
        setupUI();
        setupRestaurantInfo();
        observeViewModelData();
        setupAddReviewButton();
        setupBackButton();

    }

    // ---setup methods ---

    /**
     * Initializes the ViewModel.
     */
    private void setupViewModel() {
        reviewViewModel = new ViewModelProvider(this).get(ReviewViewModel.class);
    }

    /**
     * Sets up the RecyclerView with its adapter.
     * Configures vertical layout for displaying the review list.
     */
    private void setupRecyclerView() {
        adapter = new ReviewAdapter();
        binding.rvReviews.setAdapter(adapter);
        binding.rvReviews.setLayoutManager(new LinearLayoutManager(requireContext()));

    }

    /**
     * Configures the status bar appearance.
     * Sets white background with dark icons for better visibility.
     */
    private void setupUI() {
        Window window = requireActivity().getWindow();

        // Reset status bar to normal state
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);

        // White background for status bar
        window.setStatusBarColor(Color.WHITE);

        // Dark icons on status bar (API 23+)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }

    /**
     * Sets up the current user's information display.
     * Loads username and profile picture.
     */
    private void setupRestaurantInfo() {
        binding.tvUserName.setText(reviewViewModel.getCurrentUserName());
        Glide.with(requireContext())
                .load(reviewViewModel.getCurrentUserPicture())
                .circleCrop()
                .into(binding.ivAvatarUser);
    }

    // --- ViewModel Observation  ---

    /**
     * Observes ViewModel LiveData and updates UI accordingly.
     * Handles restaurant info, review list, validation errors, and success events.
     */
    private void observeViewModelData() {
        // Observe restaurant information
        reviewViewModel.getRestaurant().observe(getViewLifecycleOwner(), restaurant -> {
            binding.tvRestaurantName.setText(restaurant.getName());
        });

        // Observe review list updates
        reviewViewModel.getReviews().observe(getViewLifecycleOwner(), reviews -> {
            adapter.submitList(new ArrayList<>(reviews), () -> {
                binding.rvReviews.smoothScrollToPosition(0);
            });
        });

        // Observe comment validation errors
        reviewViewModel.getCommentError().observe(getViewLifecycleOwner(), error -> {
            binding.etUserComment.setError(error);
        });

        // Observe rating validation errors
        reviewViewModel.getRatingError().observe(getViewLifecycleOwner(), error -> {
            // Utilise un Toast pour une erreur de rating
            if (error != null) {
                Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show();
            }
        });

        // Observe review addition success event
        reviewViewModel.getReviewAddSuccessEvent().observe(getViewLifecycleOwner(), isSuccess -> {
            if (isSuccess) {
                // Réinitialisation de l'UI si l'ajout a réussi - C'EST UNE MANIPULATION D'UI
                binding.etUserComment.setText("");
                binding.rbRatingBarUser.setRating(0);
                Toast.makeText(requireContext(), "Avis ajouté avec succès", Toast.LENGTH_SHORT).show();

                // Reset success event to prevent re-triggering on rotation
                reviewViewModel.resetSuccessEvent();
            }
        });
    }

    // --- user interaction ---

    /**
     * Sets up the review submission button.
     * Extracts user input and triggers review validation and addition.
     */
    private void setupAddReviewButton() {
        binding.btValidation.setOnClickListener(v -> {
            // Get user input
            String comment = binding.etUserComment.getText().toString().trim();
            float rating = binding.rbRatingBarUser.getRating();

            // Process review through ViewModel
            reviewViewModel.processNewReview(comment, (int) rating);
        });
    }

    /**
     * Sets up the back button to return to the previous screen.
     */
    private void setupBackButton() {
        binding.ivGoBack.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().popBackStack();
        });
    }


    // --- Factory method ---

    /**
     * Creates a new instance of ReviewFragment.
     *
     * @return a new ReviewFragment instance
     */
    public static ReviewFragment newInstance() {
        return new ReviewFragment();
    }
}