package com.kaelesty.movies;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieDetailsResponse {

    @SerializedName("videos")
    private Videos videos;

    public MovieDetailsResponse(Videos videos) {
        this.videos = videos;
    }

    public Videos getTrailers() {
        return videos;
    }
}
