package dev.android.davidcalle3141.popular_movies_app.ui.detail;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import dev.android.davidcalle3141.popular_movies_app.data.PopularMoviesRepo;


public class DetailViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final PopularMoviesRepo mRepository;
    private final String mMovieID;

    public DetailViewModelFactory(PopularMoviesRepo repository, String movieID) {
        this.mRepository = repository;
        this.mMovieID = movieID;

    }

    @NonNull
    @SuppressWarnings("unchecked")
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {

        return (T) new DetailActivityViewModel(mRepository, mMovieID);
    }
}
