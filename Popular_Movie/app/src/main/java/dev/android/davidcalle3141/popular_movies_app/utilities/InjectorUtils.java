package dev.android.davidcalle3141.popular_movies_app.utilities;

import android.content.Context;

import dev.android.davidcalle3141.popular_movies_app.AppExecutors;
import dev.android.davidcalle3141.popular_movies_app.data.PopularMoviesRepo;
import dev.android.davidcalle3141.popular_movies_app.data.database.AppDatabase;
import dev.android.davidcalle3141.popular_movies_app.data.network.MovieNetworkDataSource;
import dev.android.davidcalle3141.popular_movies_app.ui.detail.DetailViewModelFactory;
import dev.android.davidcalle3141.popular_movies_app.ui.main.MainViewModelFactory;

public class InjectorUtils {

    private static PopularMoviesRepo provideRepository(Context context){
        AppDatabase database = AppDatabase.getsInstance(context.getApplicationContext());
        AppExecutors executors = AppExecutors.getInstance();
        MovieNetworkDataSource networkDataSource =
                MovieNetworkDataSource.getsInstance(context.getApplicationContext(), executors);
        return PopularMoviesRepo.getsInstance(database.movieDao(),database.favoritesDao(), networkDataSource, executors);
    }

    public static MovieNetworkDataSource provideNetworkDataSource(Context context){

        provideRepository(context.getApplicationContext());
        AppExecutors executors = AppExecutors.getInstance();

        return MovieNetworkDataSource.getsInstance(context.getApplicationContext(), executors);
    }


    public static MainViewModelFactory provideMainActivityViewModelFactory(Context context) {

        PopularMoviesRepo repository = provideRepository(context.getApplicationContext());
        return new MainViewModelFactory(repository);
    }

    public static DetailViewModelFactory provideDetailActivityViewModelFactory(Context context, String movieID) {
        PopularMoviesRepo repository = provideRepository(context.getApplicationContext());
        return new DetailViewModelFactory(repository, movieID);
    }
}
