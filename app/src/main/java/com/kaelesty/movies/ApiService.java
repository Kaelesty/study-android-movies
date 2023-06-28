package com.kaelesty.movies;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("movie?token=DRV1JYY-6CPMT87-MPASB7R-VSVZM39&field=rating.kp&search=7-10&sortField=votes.kp&sortType=-1&limit=40")
    Single<Response> loadMovies(@Query("page") int page);
}
