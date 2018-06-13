package dev.android.davidcalle3141.popular_movies_app.data.network;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.content.Intent;

import java.util.List;

import dev.android.davidcalle3141.popular_movies_app.AppExecutors;
import dev.android.davidcalle3141.popular_movies_app.data.database.MovieEntry;

public class MovieNetworkDataSource {

    private static final Object LOCK = new Object();
    private static  MovieNetworkDataSource sInstance;
    private final Context mContext;

    private final MutableLiveData<List<MovieEntry>> mDownloadedMovieData;
    private final AppExecutors mExecutors;

    private MovieNetworkDataSource(Context mContext, AppExecutors mExecutors) {
        this.mContext = mContext;
        this.mExecutors = mExecutors;
        mDownloadedMovieData = new MutableLiveData<>();
    }

    public static MovieNetworkDataSource getsInstance(Context context, AppExecutors executors){
        if(sInstance == null){
            synchronized (LOCK){
                sInstance = new MovieNetworkDataSource(context.getApplicationContext(),executors);
            }
        }
        return sInstance;
    }


    public LiveData<List<MovieEntry>> getMovies() {
        return mDownloadedMovieData;
    }


    void fetchMovies(){
        mExecutors.networkIO().execute(()->{
            try {
                String jsonMovieRatingSortData = NetworkUtils.getResponseFromHttpUrl(NetworkUtils.ratingMoviesUrl("en_Us", "840"));
                String jsonMoviePopularSortData = NetworkUtils.getResponseFromHttpUrl(NetworkUtils.popularMoviesUrl("en_Us", "840"));

                MovieResponse response = new MovieJsonUtils().parseMovieJson(jsonMoviePopularSortData, true ,false);
                assert response != null;
                //TODO handle null response cases
                response.AddToMovieResponse(new MovieJsonUtils().parseMovieJson(jsonMovieRatingSortData,false,true).getMovieEntries());


                mDownloadedMovieData.postValue(response.getMovieEntries());
            } catch (Exception e){
                e.printStackTrace();
            }
        });
    }

    public void startFetchMoviesService() {
        Intent intentToFetch= new Intent(mContext, SyncIntentService.class);
        mContext.startService(intentToFetch);
    }
}

