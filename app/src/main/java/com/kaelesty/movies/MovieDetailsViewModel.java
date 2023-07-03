package com.kaelesty.movies;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MovieDetailsViewModel extends AndroidViewModel {

    public static final String TAG = "MovieDetailsViewModel";

    private final MutableLiveData<List<Trailer>> trailers = new MutableLiveData<>();
    private final MutableLiveData<List<Review>> reviews = new MutableLiveData<>();

    private final CompositeDisposable subscribes = new CompositeDisposable();

    private MutableLiveData<Boolean> isLoadingReviews = new MutableLiveData<>();

    int reviewsPage = 1;


    public MovieDetailsViewModel(@NonNull Application application) {
        super(application);
        isLoadingReviews.postValue(false);
    }

    public void loadReviews(int movieId) {
        Boolean isLoading = isLoadingReviews.getValue();
        if (isLoading != null) {
            if (isLoading) {
                return;
            }
        }
        Log.d(TAG, "loadReviews()");
        Disposable disposable = ApiFactory.getApiService().loadReviews(movieId, reviewsPage)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Throwable {
                        isLoadingReviews.postValue(true);
                    }
                })
                .doAfterTerminate(new Action() {
                    @Override
                    public void run() throws Throwable {
                        isLoadingReviews.postValue(false);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<ReviewsResponse, List<Review>>() {
                    @Override
                    public List<Review> apply(ReviewsResponse reviewsResponse) throws Throwable {
                        return reviewsResponse.getReviews();
                    }
                })
                .subscribe(new Consumer<List<Review>>() {
                    @Override
                    public void accept(List<Review> reviewsList) throws Throwable {
                        List<Review> currentReviews = reviews.getValue();
                        if (currentReviews != null) {
                            currentReviews.addAll(reviewsList);
                            reviews.postValue(currentReviews);
                        }
                        else {
                            reviews.postValue(reviewsList);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Throwable {
                        Log.d(TAG, "loadReviews() falied " + throwable.toString());
                    }
                });
        subscribes.add(disposable);
        reviewsPage++;
    }

    public void loadTrailers(int movieId) {
        Disposable disposable = ApiFactory.getApiService().loadMovieDetails(movieId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<MovieDetailsResponse, List<Trailer>>() {
                    @Override
                    public List<Trailer> apply(MovieDetailsResponse movieDetailsResponse) throws Throwable {
                        // Kinopoisk API doesn't return back trailers anymore
                        // then there will be three random hardcoded trailers to fill recyclerView
                        // return movieDetailsResponse.getVideos().getTrailers();
                        List<Trailer> result = new ArrayList<>();

                        result.add(new Trailer("https://www.youtube.com/watch?v=RDzw1EKnaIA", "Trailer #1"));
                        result.add(new Trailer("https://www.youtube.com/watch?v=6ZfuNTqbHE8", "Trailer #2"));
                        result.add(new Trailer("https://www.youtube.com/watch?v=q94n3eWOWXM", "Trailer #3"));

                        return result;
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
                        Log.d(TAG, "loadTrailers() failed" + throwable.toString());
                    }
                });
        subscribes.add(disposable);
    }

    public LiveData<List<Review>> getReviews() { return reviews; };

    public LiveData<List<Trailer>> getTrailers() {
        return trailers;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        subscribes.dispose();
    }
}
