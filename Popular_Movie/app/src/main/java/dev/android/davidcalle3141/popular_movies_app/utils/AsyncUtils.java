package dev.android.davidcalle3141.popular_movies_app.utils;

import android.os.AsyncTask;

import java.net.URL;

import dev.android.davidcalle3141.popular_movies_app.adapters.MovieAdapter;
import dev.android.davidcalle3141.popular_movies_app.adapters.ReviewsAdapter;
import dev.android.davidcalle3141.popular_movies_app.models.Movie;

public class AsyncUtils extends AsyncTask<URL, Void , String>{
    private MovieAdapter mMovieAdapter;
    private ReviewsAdapter mReviewsAdapter;
    private String operation;

    public AsyncUtils(MovieAdapter mAdapter, String operation){
        this.mMovieAdapter = mAdapter;
        this.operation = operation;
    }

    public AsyncUtils(ReviewsAdapter mReviewsAdapter, String operation){
        this.mReviewsAdapter = mReviewsAdapter;
        this.operation = operation;
    }




    @Override
    protected String doInBackground(URL... params) {
        int i =0;
        URL url = params[0];
        try{
            return NetworkUtils.getResponseFromHttpUrl(url);
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    @Override
    protected void onPostExecute(String movieDataString) {
        super.onPostExecute(movieDataString);
        if(movieDataString != null){
            switch (operation){
                case "main":
                    mMovieAdapter.setMovieData(movieDataString, "w185");
                    mMovieAdapter.notifyDataSetChanged();
                    break;
                case "trailers":

                    break;
                case "reviews":
                    mReviewsAdapter.setMovieReviews(movieDataString);
                    mReviewsAdapter.notifyDataSetChanged();
                    break;
            }

        }
    }


}
