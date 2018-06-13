package dev.android.davidcalle3141.popular_movies_app.utilities;

import android.content.Context;

import dev.android.davidcalle3141.popular_movies_app.AppExecutors;
import dev.android.davidcalle3141.popular_movies_app.data.PopularMoviesRepository;
import dev.android.davidcalle3141.popular_movies_app.data.database.AppDatabase;
import dev.android.davidcalle3141.popular_movies_app.data.network.MovieNetworkDataSource;
import dev.android.davidcalle3141.popular_movies_app.ui.main.MainViewModelFactory;

public class InjectorUtils {

    private static PopularMoviesRepository provideRepository(Context context){
        AppDatabase database = AppDatabase.getsInstance(context.getApplicationContext());
        AppExecutors executors = AppExecutors.getInstance();
        MovieNetworkDataSource networkDataSource =
                MovieNetworkDataSource.getsInstance(context.getApplicationContext(), executors);
        return PopularMoviesRepository.getsInstance(database.movieDao(), networkDataSource, executors);
    }

    public static MovieNetworkDataSource provideNetworkDataSource(Context context){

        provideRepository(context.getApplicationContext());
        AppExecutors executors = AppExecutors.getInstance();

        return MovieNetworkDataSource.getsInstance(context.getApplicationContext(), executors);
    }


    public static MainViewModelFactory provideMainActivityViewModelFactory(Context context) {

        PopularMoviesRepository repository = provideRepository(context.getApplicationContext());
        return new MainViewModelFactory(repository);
    }
}
