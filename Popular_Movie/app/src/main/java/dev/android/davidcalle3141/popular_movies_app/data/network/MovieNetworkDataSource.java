package dev.android.davidcalle3141.popular_movies_app.data.network;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.HashMap;
import java.util.List;

import dev.android.davidcalle3141.popular_movies_app.AppExecutors;
import dev.android.davidcalle3141.popular_movies_app.data.database.MovieEntry;

public class MovieNetworkDataSource {

    private static final Object LOCK = new Object();
    private static  MovieNetworkDataSource sInstance;
    private final Context mContext;


    private final MutableLiveData<List<MovieEntry>> mDownloadedMovieData;
    private  MutableLiveData<List<HashMap<String, String>>> mDownloadedReviewsData;
    private  MutableLiveData<List<HashMap<String, String>>> mDownloadedTrailerData;

    private final AppExecutors mExecutors;

    private MovieNetworkDataSource(Context mContext, AppExecutors mExecutors) {
        this.mContext = mContext;
        this.mExecutors = mExecutors;
        mDownloadedMovieData = new MutableLiveData<>();
        mDownloadedTrailerData = new MutableLiveData<>();
        mDownloadedReviewsData = new MutableLiveData<>();

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
                Log.d("DDDDDDDDDDDDDD", "stuff added");
            } catch (Exception e){
                e.printStackTrace();
            }
        });
    }


    public void fetchTrailersAndReviews(String movieID){


        mExecutors.networkIO().execute(()->{
            try{
                String JsonMovieReviewsData = NetworkUtils.getResponseFromHttpUrl(NetworkUtils.movieReviewsUrl(movieID,"en_Us","840"));
                String JsonMovieTrailersData = NetworkUtils.getResponseFromHttpUrl(NetworkUtils.movieTrailerssUrl(movieID,"en_Us","840"));

                ReviewsAndTrailersResponse trailersResponse = new MovieJsonUtils().parseMovieTrailerJson(JsonMovieTrailersData);
                ReviewsAndTrailersResponse reviewsResponse = new MovieJsonUtils().parseMovieReviewsJson(JsonMovieReviewsData);
                //TODO handle null response
                assert trailersResponse != null;
                //null case handled in activity
                mDownloadedTrailerData.postValue(trailersResponse.getResponse());
                assert reviewsResponse != null;
                mDownloadedReviewsData.postValue(reviewsResponse.getResponse());

            }catch (Exception e){
                e.printStackTrace();
            }
        });
    }

    public LiveData<List<HashMap<String, String>>> getReviews(){
        return mDownloadedReviewsData;
    }

    public LiveData<List<HashMap<String, String>>> getTrailers(){
        return mDownloadedTrailerData;
    }

    public void startFetchMoviesService() {
        Intent intentToFetch= new Intent(mContext, SyncIntentServiceFetchMovies.class);
        mContext.startService(intentToFetch);
    }


    public void clearReviewsAndTrailers() {
        mDownloadedReviewsData = new MutableLiveData<>();
        mDownloadedTrailerData = new MutableLiveData<>();
    }
}

