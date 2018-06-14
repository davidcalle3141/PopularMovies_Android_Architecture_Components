package dev.android.davidcalle3141.popular_movies_app.data;

import android.arch.lifecycle.LiveData;
import android.util.Log;

import java.util.HashMap;
import java.util.List;

import dev.android.davidcalle3141.popular_movies_app.AppExecutors;
import dev.android.davidcalle3141.popular_movies_app.data.database.MovieDao;
import dev.android.davidcalle3141.popular_movies_app.data.database.MovieEntry;
import dev.android.davidcalle3141.popular_movies_app.data.network.MovieNetworkDataSource;

public class PopularMoviesRepo {

    private static final Object LOCK = new Object();
    private static PopularMoviesRepo sInstance;
    private final MovieNetworkDataSource mMovieNetworkDataSource;
    private final MovieDao mMovieDao;
    private final AppExecutors mExecutors;
    private boolean mInitialized = false;
    private boolean mTrailersAndReviewsInitilized = false;

    private PopularMoviesRepo(MovieDao movieDao,
                              MovieNetworkDataSource movieNetworkDataSource,
                              AppExecutors executors){
        this.mMovieDao = movieDao;
        this.mMovieNetworkDataSource = movieNetworkDataSource;
        this.mExecutors = executors;

        LiveData<List<MovieEntry>> networkData = movieNetworkDataSource.getMovies();
        networkData.observeForever(newMoviesFromNetwork -> mExecutors.diskIO().execute(()->{
           if(newMoviesFromNetwork != null) {
               deleteOldData();
               movieDao.bulkInsert(newMoviesFromNetwork);

           }

        }));
    }

    public synchronized static PopularMoviesRepo getsInstance(MovieDao movieDao,
                                                              MovieNetworkDataSource movieNetworkDataSource,
                                                              AppExecutors executors){
        if (sInstance == null){
            synchronized (LOCK){
                sInstance = new PopularMoviesRepo(movieDao,movieNetworkDataSource,executors);
            }
        }

        return sInstance;
    }

    private synchronized void initializeData(){
        if(mInitialized) return;
        mInitialized = true;

        mExecutors.diskIO().execute(this::startFetchMovieEntryService);

    }

    private synchronized void initializeReviewsAndTrailers(String movieID){
        if(!mTrailersAndReviewsInitilized){
            Log.d("DDDDDDDDDDDDDD", "repo CreatedR");

            mMovieNetworkDataSource.clearReviewsAndTrailers();
        mMovieNetworkDataSource.fetchTrailersAndReviews(movieID);
        mTrailersAndReviewsInitilized = true;
        return;}
        mTrailersAndReviewsInitilized = false;

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



    public LiveData<MovieEntry> getMovieEntry(int movieID) {
        initializeData();
        return mMovieDao.loadMovieEntry(movieID);
    }


    public LiveData<List<HashMap<String, String>>> getMovieReviews(String movieID) {
        initializeReviewsAndTrailers(movieID);
        return mMovieNetworkDataSource.getReviews();
    }
    public LiveData<List<HashMap<String, String>>> getMovieTrailers(String movieID){
        initializeReviewsAndTrailers(movieID);
        return mMovieNetworkDataSource.getTrailers();
    }
}
