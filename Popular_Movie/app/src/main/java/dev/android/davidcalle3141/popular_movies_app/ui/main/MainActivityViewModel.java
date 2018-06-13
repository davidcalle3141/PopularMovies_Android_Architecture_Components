package dev.android.davidcalle3141.popular_movies_app.ui.main;

import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import dev.android.davidcalle3141.popular_movies_app.data.PopularMoviesRepository;
import dev.android.davidcalle3141.popular_movies_app.data.database.MovieEntry;
import dev.android.davidcalle3141.popular_movies_app.data.database.MainMoviesRatingEntry;

public class MainActivityViewModel extends ViewModel {

    private final PopularMoviesRepository mRepository;
    private final LiveData<List<MovieEntry>> mPopularMovies;
    private final LiveData<List<MovieEntry>> mRatingMovies;
    private  LiveData<List<MovieEntry>> mCurrentViewMovies;



    MainActivityViewModel(PopularMoviesRepository Repository) {
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
