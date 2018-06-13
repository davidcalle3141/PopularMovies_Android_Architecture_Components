package dev.android.davidcalle3141.popular_movies_app.ui.main;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import dev.android.davidcalle3141.popular_movies_app.data.PopularMoviesRepository;

public class MainViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final PopularMoviesRepository mRepository;

    public MainViewModelFactory(PopularMoviesRepository repository) {
        this.mRepository = repository;
    }

    @NonNull
    @SuppressWarnings("unchecked")
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {

        return (T) new MainActivityViewModel(mRepository);
    }
}
