package com.kaelesty.movies;

import com.google.gson.annotations.SerializedName;

public class Rating {

    @SerializedName("kp")
    private double rating;

    public double getRating() {
        return rating;
    }

    public Rating(double rating) {
        this.rating = rating;
    }
}
