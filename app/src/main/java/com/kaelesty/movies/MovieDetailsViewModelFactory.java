package com.kaelesty.movies;

import android.app.Application;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class MovieDetailsViewModelFactory implements ViewModelProvider.Factory {
    private Application application;
    private int movieId;


    public MovieDetailsViewModelFactory(Application application, int movieId) {
        this.application = application;
        this.movieId = movieId;
    }


    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new MovieDetailsViewModel(application, movieId);
    }
}
