package com.kaelesty.movies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class MovieDetailsActivity extends AppCompatActivity {

    public static final String MOVIE_EXTRA_KEY = "movie";

    private MovieDetailsViewModel viewModel;

    private TrailersAdapter trailersAdapter;
    private ReviewsAdapter reviewsAdapter;

    private RecyclerView recyclerViewTrailers;
    private RecyclerView recyclerViewReviews;

    private ImageView imageViewPoster;
    private TextView textViewTitle;
    private TextView textViewYear;
    private TextView textViewDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        initViews();

        Movie movie = (Movie) getIntent().getSerializableExtra(MOVIE_EXTRA_KEY);

        trailersAdapter = new TrailersAdapter();
        trailersAdapter.setOnClickListener(new TrailersAdapter.OnClickListener() {
            @Override
            public void onClick(Trailer trailer) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(trailer.getUrl()));
                startActivity(intent);
            }
        });
        recyclerViewTrailers.setAdapter(trailersAdapter);
        recyclerViewTrailers.setLayoutManager(new LinearLayoutManager(this));

        reviewsAdapter = new ReviewsAdapter();

        recyclerViewReviews.setAdapter(reviewsAdapter);
        recyclerViewReviews.setLayoutManager(new LinearLayoutManager(this));

        viewModel = new MovieDetailsViewModelFactory(getApplication(), movie.getId()).create(MovieDetailsViewModel.class);

        viewModel.loadTrailers(movie.getId());
        viewModel.getTrailers().observe(this, new Observer<List<Trailer>>() {
            @Override
            public void onChanged(List<Trailer> trailers) {
                trailersAdapter.setTrailers(trailers);
            }
        });

        viewModel.getReviews().observe(this, new Observer<List<Review>>() {
            @Override
            public void onChanged(List<Review> reviews) {
                reviewsAdapter.setReviews(reviews);
            }
        });

        setMovieContent(movie);
    }

    private void initViews() {
        imageViewPoster = findViewById(R.id.imageViewPoster);
        textViewTitle = findViewById(R.id.textViewTitle);
        textViewYear = findViewById(R.id.textViewYear);
        textViewDesc = findViewById(R.id.textViewDesc);
        recyclerViewTrailers = findViewById(R.id.recyclerViewTrailers);
        recyclerViewReviews = findViewById(R.id.recyclerViewReviews);
    }

    private void setMovieContent(Movie movie) {

        textViewTitle.setText(movie.getName());
        textViewYear.setText(String.valueOf(movie.getYear()));
        textViewDesc.setText(movie.getDescription());
        Glide.with(this)
                .load(movie.getPoster().getUrl())
                .into(imageViewPoster);
    }

    public static Intent newIntent(Context context, Movie movie) {
        Intent intent = new Intent(context, MovieDetailsActivity.class);
        intent.putExtra(MOVIE_EXTRA_KEY, movie);
        return intent;
    }
}