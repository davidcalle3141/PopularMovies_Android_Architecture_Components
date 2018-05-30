package dev.android.davidcalle3141.popular_movies_app.utils;

import android.os.AsyncTask;

import java.net.URL;

import dev.android.davidcalle3141.popular_movies_app.models.Movie;

public class AsyncUtils extends AsyncTask<URL, Void , String>{
    private MovieAdapter mAdapter;
    private String operation;
    private Movie movie = null;

    public AsyncUtils(MovieAdapter mAdapter, String operation){
        this.mAdapter = mAdapter;
        this.operation = operation;
    }

    public AsyncUtils(MovieAdapter mAdapter, String operation, Movie movie){
        this.mAdapter = mAdapter;
        this.operation = operation;
        this.movie = movie;
    }

    public void updateOperation(String operation){
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
                    mAdapter.setMovieData(movieDataString, "w185");
                    mAdapter.notifyDataSetChanged();
                    break;
                case "trailers":
                    MovieJsonUtils.parseMovieTrailerJson(movieDataString,movie);
                    break;
                case "reviews":
                    MovieJsonUtils.parseMovieReviewsJson(movieDataString,movie);
                    break;
            }

        }
    }


}
