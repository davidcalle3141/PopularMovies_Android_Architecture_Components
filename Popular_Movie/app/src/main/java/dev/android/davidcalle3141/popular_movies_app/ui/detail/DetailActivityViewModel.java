package dev.android.davidcalle3141.popular_movies_app.ui.detail;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import java.util.HashMap;
import java.util.List;


import dev.android.davidcalle3141.popular_movies_app.data.PopularMoviesRepo;

public class DetailActivityViewModel extends ViewModel {
    private final PopularMoviesRepo mRepository;
    private LiveData<List<HashMap<String,String>>> mReviews;
    private LiveData<List<HashMap<String, String>>> mTrailers;

    DetailActivityViewModel(PopularMoviesRepo Repository, String movieID) {
        this.mRepository = Repository;
        mReviews = mRepository.getMovieReviews(movieID);
        mTrailers = mRepository.getMovieTrailers(movieID);

        Log.d("ksdjfal","DetailActivity Created SON");
    }


    public LiveData<List<HashMap<String, String>>> getmReviews() {
        return mReviews;
    }

    public LiveData<List<HashMap<String, String>>> getmTrailers() {
        return mTrailers;
    }
}

