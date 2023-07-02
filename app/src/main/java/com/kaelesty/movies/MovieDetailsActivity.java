package com.kaelesty.movies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class MovieDetailsActivity extends AppCompatActivity {

    public static final String MOVIE_EXTRA_KEY = "movie";

    private MovieDetailsViewModel viewModel;

    private TrailersAdapter adapter;

    private RecyclerView recyclerView;
    private ImageView imageViewPoster;
    private TextView textViewTitle;
    private TextView textViewYear;
    private TextView textViewDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        initViews();


        adapter = new TrailersAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        viewModel = new ViewModelProvider(this).get(MovieDetailsViewModel.class);

        Movie movie = (Movie) getIntent().getSerializableExtra(MOVIE_EXTRA_KEY);

        viewModel.loadTrailers(movie.getId());
        viewModel.getTrailers().observe(this, new Observer<List<Trailer>>() {
            @Override
            public void onChanged(List<Trailer> trailers) {
                adapter.setTrailers(trailers);
            }
        });

        setMovieContent(movie);
    }

    private void initViews() {
        imageViewPoster = findViewById(R.id.imageViewPoster);
        textViewTitle = findViewById(R.id.textViewTitle);
        textViewYear = findViewById(R.id.textViewYear);
        textViewDesc = findViewById(R.id.textViewDesc);
        recyclerView = findViewById(R.id.recyclerViewTrailers);
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