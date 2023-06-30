package com.kaelesty.movies;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHoled> {

    private List<Movie> movies = new ArrayList<>();
    private OnReachEndListener onReachEndListener;

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }

    public void setOnReachEndListener(OnReachEndListener onReachEndListener) {
        this.onReachEndListener = onReachEndListener;
    }

    @NonNull
    @Override
    public MovieViewHoled onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
        .inflate(
                R.layout.movie_item,
                parent,
                false
                );
        return new MovieViewHoled(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MovieViewHoled holder, int position) {
        Movie movie = movies.get(position);

        Glide.with(holder.itemView)
                .load(movie.getPoster().getUrl())
                .into(holder.imageViewPoster);

        double rating = movie.getRating().getRating();
        int backgroundId;
        holder.textViewRating.setText(String.format("%.1f", rating));
        if (rating >= 7) {
            backgroundId = R.drawable.circle_green;
        }
        else if (rating > 5) {
            backgroundId = R.drawable.circle_orange;
        } else {
            backgroundId = R.drawable.circle_red;
        }
        Drawable drawable = ContextCompat.getDrawable(holder.itemView.getContext(), backgroundId);
        holder.textViewRating.setBackground(drawable);
        if (position == movies.size() - 10) {
            onReachEndListener.onReachEnd();
        }
    }

    public interface OnReachEndListener {

        public void onReachEnd();
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public static class MovieViewHoled extends RecyclerView.ViewHolder {

        private final ImageView imageViewPoster;
        private final TextView textViewRating;


        public MovieViewHoled(@NonNull View itemView) {
            super(itemView);
            textViewRating = itemView.findViewById(R.id.textViewRating);
            imageViewPoster = itemView.findViewById(R.id.imageViewPoster);
        }
    }
}
