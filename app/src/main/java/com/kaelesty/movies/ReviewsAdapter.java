package com.kaelesty.movies;

import android.annotation.SuppressLint;
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

    private List<Review> reviews = new ArrayList<>();

    private OnReachEndListener onReachEndListener;

    public void setOnReachEndListener(OnReachEndListener onReachEndListener) {
        this.onReachEndListener = onReachEndListener;
    }

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
        int color = 0;
        try {
            if (Objects.equals(review.getType(), "Позитивный"))
                    color = android.R.color.holo_green_light;
            else if (Objects.equals(review.getType(), "Негативный")) {
                color = android.R.color.holo_red_light;
            }
            else {
                color = android.R.color.holo_orange_light;
            }
        }
        catch (NullPointerException e) {
            color = android.R.color.holo_orange_light;
        }

        holder.cardViewReview.setCardBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), color));

        if (position == reviews.size() - 1) {
            onReachEndListener.onReachEnd();
        }
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    public static class ReviewsViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewAuthor;
        private TextView textViewReview;
        private CardView cardViewReview;

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
