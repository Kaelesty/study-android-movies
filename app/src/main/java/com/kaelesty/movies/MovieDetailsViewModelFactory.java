package com.kaelesty.movies;

import android.app.Application;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class MovieDetailsViewModelFactory implements ViewModelProvider.Factory {
    private Application application;
    private Movie movie;


    public MovieDetailsViewModelFactory(Application application, Movie movie) {
        this.application = application;
        this.movie = movie;
    }


    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new MovieDetailsViewModel(application, movie);
    }
}
