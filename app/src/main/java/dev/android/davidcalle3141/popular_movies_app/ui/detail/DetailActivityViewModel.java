package dev.android.davidcalle3141.popular_movies_app.ui.detail;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.HashMap;
import java.util.List;

import dev.android.davidcalle3141.popular_movies_app.data.PopularMoviesRepo;
import dev.android.davidcalle3141.popular_movies_app.data.database.FavoritesEntry;
import dev.android.davidcalle3141.popular_movies_app.data.database.MovieEntry;

public class DetailActivityViewModel extends ViewModel {
    private final PopularMoviesRepo mRepository;
    private LiveData<List<HashMap<String,String>>> mReviews;
    private LiveData<List<HashMap<String, String>>> mTrailers;
    private LiveData<FavoritesEntry> mFavoriteEntry;
    private LiveData<MovieEntry> mMovieEntry;
    private final String APImovieID;
    private MutableLiveData<Boolean> isFavorite;

    DetailActivityViewModel(PopularMoviesRepo Repository, String APImovieID) {
        this.mRepository = Repository;
        this.APImovieID = APImovieID;
        mReviews = mRepository.getMovieReviews(APImovieID);
        mTrailers = mRepository.getMovieTrailers(APImovieID);
        mFavoriteEntry = mRepository.getFavorite(APImovieID);
        mMovieEntry = mRepository.getMovieEntry(APImovieID);
        isFavorite = new MutableLiveData<>();

    }


    public LiveData<List<HashMap<String, String>>> getmReviews() {
        return mReviews;
    }

    public LiveData<List<HashMap<String, String>>> getmTrailers() {
        return mTrailers;
    }


    public synchronized void removeFavorite(){
        mRepository.removeFavorite(APImovieID);
    }
    public synchronized void addFavorite(){
        mRepository.addFavorite(mMovieEntry.getValue());
    }

    public LiveData<MovieEntry> getMovieEntry() {
        return mMovieEntry;
    }

    public synchronized void setIsFavorite(boolean isFavorite) {
        this.isFavorite.setValue(isFavorite);
    }
    public synchronized MutableLiveData<Boolean> getIsFavorite(){
        return isFavorite;
    }
    public synchronized Boolean getIsFavoriteBoolean(){
        return isFavorite.getValue();
    }

    public LiveData<FavoritesEntry> getmFavoriteEntry() {
        return mFavoriteEntry;
    }
}

