package dev.android.davidcalle3141.popular_movies_app.ui.main;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import dev.android.davidcalle3141.popular_movies_app.data.PopularMoviesRepo;
import dev.android.davidcalle3141.popular_movies_app.data.database.MovieEntry;

public class MainActivityViewModel extends ViewModel {

    private final PopularMoviesRepo mRepository;
    private final LiveData<List<MovieEntry>> mPopularMovies;
    private final LiveData<List<MovieEntry>> mRatingMovies;
    private  LiveData<List<MovieEntry>> mCurrentViewMovies;



    MainActivityViewModel(PopularMoviesRepo Repository) {
        this.mRepository = Repository;
        this.mPopularMovies = mRepository.getPopularMovies();
        this.mRatingMovies = mRepository.getRatingMovies();
        this.mCurrentViewMovies = mPopularMovies;
    }



    public LiveData<List<MovieEntry>> getPopularMovies() {
        mCurrentViewMovies = mPopularMovies;
        return mPopularMovies;
    }

    public LiveData<List<MovieEntry>> getRatingMovies() {
        mCurrentViewMovies = mRatingMovies;
        return mRatingMovies;
    }


    public LiveData<List<MovieEntry>> getCurrentViewMovies() {
        return mCurrentViewMovies;
    }

}
