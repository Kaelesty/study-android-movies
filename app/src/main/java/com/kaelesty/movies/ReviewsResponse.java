package com.kaelesty.movies;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReviewsResponse {

    @SerializedName("docs")
    private List<Review> reviews;

    public List<Review> getReviews() {
        return reviews;
    }

    public ReviewsResponse(List<Review> reviews) {
        this.reviews = reviews;
    }
}
