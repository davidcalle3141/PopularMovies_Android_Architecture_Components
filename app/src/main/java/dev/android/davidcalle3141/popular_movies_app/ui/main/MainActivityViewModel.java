package dev.android.davidcalle3141.popular_movies_app.ui.main;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import dev.android.davidcalle3141.popular_movies_app.data.PopularMoviesRepo;
import dev.android.davidcalle3141.popular_movies_app.data.database.FavoritesEntry;
import dev.android.davidcalle3141.popular_movies_app.data.database.MovieEntry;

public class MainActivityViewModel extends ViewModel {

    private final PopularMoviesRepo mRepository;
    private final LiveData<List<MovieEntry>> mPopularMovies;
    private final LiveData<List<MovieEntry>> mRatingMovies;
    private final LiveData<List<FavoritesEntry>> mFavorites;
    private int mCurrentViewMovies;




    MainActivityViewModel(PopularMoviesRepo Repository) {
        this.mRepository = Repository;
        this.mPopularMovies = mRepository.getPopularMovies();
        this.mRatingMovies = mRepository.getRatingMovies();
        this.mCurrentViewMovies = 1;
        this.mFavorites = mRepository.getFavoritesList();

    }

    public LiveData<List<FavoritesEntry>> getFavoriteList() {
        mCurrentViewMovies = 2;
        return mFavorites;
    }


    public LiveData<List<MovieEntry>> getPopularMovies() {
        mCurrentViewMovies = 1;
        return mPopularMovies;
    }

    public LiveData<List<MovieEntry>> getRatingMovies() {
        mCurrentViewMovies = 0;
        return mRatingMovies;
    }


    public int getCurrentViewMovies() {
        return mCurrentViewMovies;
    }


}
