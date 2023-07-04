package com.kaelesty.movies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import java.util.List;

public class FavoritesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProgressBar progressBar;

    private FavoritesViewModel viewModel;

    private MoviesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        progressBar.setVisibility(View.VISIBLE);

        viewModel = new ViewModelProvider(this).get(FavoritesViewModel.class);

        adapter = new MoviesAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        adapter.setOnClickListener(new MoviesAdapter.OnClickListener() {
            @Override
            public void OnClick(Movie movie) {
                startActivity(MovieDetailsActivity.newIntent(FavoritesActivity.this, movie));
            }
        });

        viewModel.getMovies().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                adapter.setMovies(movies);
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void initViews() {
        recyclerView = findViewById(R.id.recyclerViewMovies);
        progressBar = findViewById(R.id.progressBarMoviesLoading);
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, FavoritesActivity.class);
    }
}