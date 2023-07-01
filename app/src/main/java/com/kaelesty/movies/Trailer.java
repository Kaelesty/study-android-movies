package com.kaelesty.movies;

import com.google.gson.annotations.SerializedName;

public class Trailer {

    @SerializedName("url")
    private String url;

    @SerializedName("name")
    private String name;

    public String getUrl() {
        return url;
    }

    public String getName() {
        return name;
    }

    public Trailer(String url, String name) {
        this.url = url;
        this.name = name;
    }
}
