package com.kaelesty.movies;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ReviewsViewHolder> {

    public static final String TAG = "ReviewsAdapter";

    public static final String REVIEW_TYPE_POSITIVE = "Позитивный";
    public static final String REVIEW_TYPE_NEGATIVE = "Негативный";
    public static final String REVIEW_TYPE_NEUTRAL = "Нейтральный";

    private List<Review> reviews = new ArrayList<>();

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ReviewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.review_item,
                parent,
                false
        );
        return new ReviewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewsViewHolder holder, int position) {
        Review review = reviews.get(position);
        holder.textViewAuthor.setText(review.getAuthor());
        holder.textViewReview.setText(review.getReview());
        int color = android.R.color.holo_orange_light;;
        String type = review.getType();
        if (type != null) {
            switch (type) {
                case REVIEW_TYPE_POSITIVE:
                    color = android.R.color.holo_green_light;
                    break;
                case REVIEW_TYPE_NEGATIVE:
                    color = android.R.color.holo_red_light;
                    break;
            }
        }
        holder.cardViewReview.setCardBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), color));
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    public static class ReviewsViewHolder extends RecyclerView.ViewHolder {

        private final TextView textViewAuthor;
        private final TextView textViewReview;
        private final CardView cardViewReview;

        public ReviewsViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewAuthor = itemView.findViewById(R.id.textViewAuthor);
            textViewReview = itemView.findViewById(R.id.textViewReview);
            cardViewReview = itemView.findViewById(R.id.cardViewReview);
        }
    }

    public interface OnReachEndListener {
        public void onReachEnd();
    }
}
