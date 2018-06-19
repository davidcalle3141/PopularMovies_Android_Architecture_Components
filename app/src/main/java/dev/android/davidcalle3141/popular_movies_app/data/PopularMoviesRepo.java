package dev.android.davidcalle3141.popular_movies_app.data;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import java.util.HashMap;
import java.util.List;

import dev.android.davidcalle3141.popular_movies_app.AppExecutors;
import dev.android.davidcalle3141.popular_movies_app.data.database.FavoritesDao;
import dev.android.davidcalle3141.popular_movies_app.data.database.FavoritesEntry;
import dev.android.davidcalle3141.popular_movies_app.data.database.MovieDao;
import dev.android.davidcalle3141.popular_movies_app.data.database.MovieEntry;
import dev.android.davidcalle3141.popular_movies_app.data.network.MovieNetworkDataSource;

public class PopularMoviesRepo {

    private static final Object LOCK = new Object();
    private static PopularMoviesRepo sInstance;
    private final MovieNetworkDataSource mMovieNetworkDataSource;
    private final MovieDao mMovieDao;
    private final FavoritesDao mFavoriteDao;
    private final AppExecutors mExecutors;
    private boolean mInitialized = false;
    private boolean mTrailersAndReviewsInitilized = false;

    private PopularMoviesRepo(MovieDao movieDao, FavoritesDao favoritesDao,
                              MovieNetworkDataSource movieNetworkDataSource,
                              AppExecutors executors){
        this.mMovieDao = movieDao;
        this.mFavoriteDao = favoritesDao;
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

    public synchronized static PopularMoviesRepo getsInstance(MovieDao movieDao, FavoritesDao favoritesDao,
                                                              MovieNetworkDataSource movieNetworkDataSource,
                                                              AppExecutors executors){
        if (sInstance == null){
            synchronized (LOCK){
                sInstance = new PopularMoviesRepo(movieDao,favoritesDao, movieNetworkDataSource,executors);
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



    public LiveData<MovieEntry> getMovieEntry(String moviePK) {
        initializeData();
        return mMovieDao.loadMovieEntry(moviePK);
    }


    public LiveData<List<HashMap<String, String>>> getMovieReviews(String movieID) {
        initializeReviewsAndTrailers(movieID);
        return mMovieNetworkDataSource.getReviews();
    }
    public LiveData<List<HashMap<String, String>>> getMovieTrailers(String movieID){
        initializeReviewsAndTrailers(movieID);
        return mMovieNetworkDataSource.getTrailers();
    }

    public MutableLiveData<Boolean> isFavorite(String moviePK) {
        MutableLiveData<Boolean> liveBool = new MutableLiveData<>();
        if(mFavoriteDao.getFavoriteById(moviePK) != null) liveBool.setValue(false);
        else liveBool.setValue(true);
        return liveBool;
    }

    public void removeFavorite(String primaryKey) {
        mExecutors.diskIO().execute(()-> removeFavoriteService(primaryKey));
    }

    public void addFavorite( MovieEntry movieEntry) {
       mExecutors.diskIO().execute(()-> addFavoriteService(movieEntry));
    }
    private void removeFavoriteService(String primaryKey){
        mFavoriteDao.deleteFavorite(primaryKey);
    }
    private void addFavoriteService(MovieEntry movieEntry){

            mFavoriteDao.Insert(new FavoritesEntry(movieEntry));
    }


    public LiveData<FavoritesEntry> getFavorite(String moviePK) {
      return   mFavoriteDao.getFavoriteById(moviePK);
    }


    public LiveData<List<FavoritesEntry>> getFavoritesList() {
        return mFavoriteDao.loadAllFavorites();
    }
}

