package com.kaelesty.movies;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Videos {

    @SerializedName("trailers")
    private List<Trailer> trailers;

    public Videos(List<Trailer> trailers) {
        this.trailers = trailers;
    }

    public List<Trailer> getTrailers() {
        return trailers;
    }
}
