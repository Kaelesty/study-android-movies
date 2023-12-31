package com.kaelesty.movies;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("movie?token=DRV1JYY-6CPMT87-MPASB7R-VSVZM39&field=rating.kp&search=7-10&sortField=votes.kp&sortType=-1&limit=40")
    Single<MoviesResponse> loadMovies(@Query("page") int page);

    @GET("movie?token=DRV1JYY-6CPMT87-MPASB7R-VSVZM39&field=id")
    Single<MovieDetailsResponse> loadMovieDetails(@Query("search") int id);

    @GET("review?token=DRV1JYY-6CPMT87-MPASB7R-VSVZM39&limit=15")
    Single<ReviewsResponse> loadReviews(@Query("movieId") int movieId, @Query("page") int page);
}
