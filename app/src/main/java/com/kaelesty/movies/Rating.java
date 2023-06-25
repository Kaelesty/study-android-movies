package com.kaelesty.movies;

import com.google.gson.annotations.SerializedName;

public class Rating {

    @SerializedName("rating")
    private double rating;

    public double getRating() {
        return rating;
    }

    public Rating(float rating) {
        this.rating = rating;
    }
}
