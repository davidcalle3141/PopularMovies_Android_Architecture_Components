package dev.android.davidcalle3141.popular_movies_app.data;

import android.arch.lifecycle.LiveData;

import java.util.List;

import dev.android.davidcalle3141.popular_movies_app.AppExecutors;
import dev.android.davidcalle3141.popular_movies_app.data.database.MovieDao;
import dev.android.davidcalle3141.popular_movies_app.data.database.MovieEntry;
import dev.android.davidcalle3141.popular_movies_app.data.network.MovieNetworkDataSource;

public class PopularMoviesRepository {

    private static final Object LOCK = new Object();
    private static PopularMoviesRepository sInstance;
    private final MovieNetworkDataSource mMovieNetworkDataSource;
    private final MovieDao mMovieDao;
    private final AppExecutors mExecutors;
    private boolean mInitialized = false;

    private PopularMoviesRepository(MovieDao movieDao,
                                    MovieNetworkDataSource movieNetworkDataSource,
                                    AppExecutors executors){
        this.mMovieDao = movieDao;
        this.mMovieNetworkDataSource = movieNetworkDataSource;
        this.mExecutors = executors;

        LiveData<List<MovieEntry>> networkData = movieNetworkDataSource.getMovies();
        networkData.observeForever(newMoviesFromNetwork -> mExecutors.diskIO().execute(()->{
            deleteOldData();
            movieDao.bulkInsert(newMoviesFromNetwork);
        }));

    }

    public synchronized static PopularMoviesRepository getsInstance(MovieDao movieDao,
                                                                    MovieNetworkDataSource movieNetworkDataSource,
                                                                    AppExecutors executors){
        if (sInstance == null){
            synchronized (LOCK){
                sInstance = new PopularMoviesRepository(movieDao,movieNetworkDataSource,executors);
            }
        }

        return sInstance;
    }

    private synchronized void initializeData(){
        if(mInitialized) return;
        mInitialized = true;

        mExecutors.diskIO().execute(this::startFetchMovieEntryService);

    }


    private void startFetchMovieEntryService(){

        mMovieNetworkDataSource.startFetchMoviesService();
    }


    private void deleteOldData(){
        mMovieDao.deleteMovies();
    }

    public LiveData<List<MovieEntry>> getPopularMovies() {
        initializeData();
        return mMovieDao.loadPopularSortedMovies();
    }

    public LiveData<List<MovieEntry>> getRatingMovies() {
        initializeData();
        return mMovieDao.loadRatingSortedMovies();
    }


}
