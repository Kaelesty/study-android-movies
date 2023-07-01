package com.kaelesty.movies;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

public class MoviesResponse {

    @SerializedName("docs")
    private ArrayList<Movie> movies;

    public ArrayList<Movie> getMovies() {
        return movies;
    }

    public MoviesResponse(ArrayList<Movie> movies) {
        this.movies = movies;
    }
}
