package com.kaelesty.movies;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;

@Dao
public interface MoviesDao {

    @Query("SELECT * FROM movies")
    LiveData<List<Movie>> getAllFavorites();

    @Query("SELECT * FROM movies WHERE id = :movieId")
    LiveData<Movie> getFavorite(int movieId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable add(Movie movie);

    @Query("DELETE FROM movies WHERE id = :movieId")
    Completable remove(int movieId);
}
