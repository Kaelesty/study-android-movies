package com.kaelesty.movies;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

public class Response {

    @SerializedName("docs")
    private ArrayList<Movie> movies;

    public ArrayList<Movie> getMovies() {
        return movies;
    }

    public Response(ArrayList<Movie> movies) {
        this.movies = movies;
    }
}
