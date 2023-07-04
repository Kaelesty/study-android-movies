package com.kaelesty.movies;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class FavoritesViewModel extends AndroidViewModel {

    private MovieDatabase db;

    public FavoritesViewModel(@NonNull Application application) {
        super(application);
        db = MovieDatabase.getInstance(application);
    }

    public LiveData<List<Movie>> getMovies() {
        return db.moviesDao().getAllFavorites();
    }
}
