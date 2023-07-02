package com.kaelesty.movies;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MovieDetailsViewModel extends AndroidViewModel {

    public static final String TAG = "MovieDetailsViewModel";

    private final MutableLiveData<List<Trailer>> trailers = new MutableLiveData<>();
    private final CompositeDisposable subscribes = new CompositeDisposable();

    public MovieDetailsViewModel(@NonNull Application application) {
        super(application);
    }

    public void loadTrailers(int movieId) {
        Disposable disposable = ApiFactory.getApiService().loadMovieDetails(movieId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<MovieDetailsResponse, List<Trailer>>() {
                    @Override
                    public List<Trailer> apply(MovieDetailsResponse movieDetailsResponse) throws Throwable {
                        return movieDetailsResponse.getVideos().getTrailers();
                    }
                })
                .subscribe(new Consumer<List<Trailer>>() {
                    @Override
                    public void accept(List<Trailer> trailersList) throws Throwable {
                        trailers.postValue(trailersList);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Throwable {
                        Log.d(TAG, "loadTrailers() failed");
                    }
                });
        subscribes.add(disposable);
    }

    public LiveData<List<Trailer>> getTrailers() {
        return trailers;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        subscribes.dispose();
    }
}
