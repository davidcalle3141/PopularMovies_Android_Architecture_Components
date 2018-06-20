package dev.android.davidcalle3141.popular_movies_app.data.network;


import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import static android.content.ContentValues.TAG;

public class NetworkUtils {
    private static final String KEY = "";

    private static final String POPULAR_MOVIES_URL =
            "https://api.themoviedb.org/3/movie/popular";
    private static final String HIGHEST_RATED_MOVIES_URL =
            "http://api.themoviedb.org/3/movie/top_rated?";
    private static final String MOVIE_REVIEWS_AND_TRAILERS_BASE_URL =
            "https://api.themoviedb.org/3/movie";
    private static final String MOVIE_IMAGE_BASE_URL =
            "http://image.tmdb.org/t/p/";




    final static private String LANGUAGE_PARAM = "language";
    final static private String REGION_PARAM = "region";
    final static private String API_KEY = "api_key";




    public static URL imageUrl(String posterPath, String size){
        Uri buildUri = Uri.parse(MOVIE_IMAGE_BASE_URL).buildUpon()
                .appendPath(size)
                .appendPath("/")
                .appendPath(posterPath)
                .build();
        URL url = null;

        try{
            url = new URL(buildUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.v(TAG,"image URI" + url);
        return url;

    }
    public static URL popularMoviesUrl(String language, String region){
        Uri buildUri = Uri.parse(POPULAR_MOVIES_URL).buildUpon()
                .appendQueryParameter(API_KEY, KEY)
                .appendQueryParameter(LANGUAGE_PARAM, language)
                .appendQueryParameter(REGION_PARAM, region)
                .build();
        URL url = null;

        try{
            url = new URL(buildUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.v(TAG,"popular movie URI" + url);
        return url;

    }

    public static URL ratingMoviesUrl(String language, String region){
        Uri buildUri = Uri.parse(HIGHEST_RATED_MOVIES_URL).buildUpon()
                .appendQueryParameter(API_KEY, KEY)
                .appendQueryParameter(LANGUAGE_PARAM, language)
                .appendQueryParameter(REGION_PARAM, region)
                .build();
        URL url = null;

        try{
            url = new URL(buildUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.v(TAG,"top rated movie URI" + url);
        return url;

    }



    //https://api.themoviedb.org/3/movie/{movie_id}/reviews?api_key=<<api_key>>&language=en-US&page=1
    public static URL movieReviewsUrl(String movie_id, String language, String region){
        Uri buildUri = Uri.parse(MOVIE_REVIEWS_AND_TRAILERS_BASE_URL).buildUpon()
                .appendPath(movie_id)
                .appendPath("reviews")
                .appendQueryParameter(API_KEY, KEY)
                .appendQueryParameter(LANGUAGE_PARAM, language)
                .appendQueryParameter(REGION_PARAM, region)
                .build();
        URL url = null;
        try {
            url = new URL(buildUri.toString());
        }catch (MalformedURLException e){
            e.printStackTrace();
        }
        return url;
    }

    public static URL movieTrailerssUrl(String movie_id, String language, String region){
        Uri buildUri = Uri.parse(MOVIE_REVIEWS_AND_TRAILERS_BASE_URL).buildUpon()
                .appendPath(movie_id)
                .appendPath("videos")
                .appendQueryParameter(API_KEY, KEY)
                .appendQueryParameter(LANGUAGE_PARAM, language)
                .appendQueryParameter(REGION_PARAM, region)
                .build();
        URL url = null;
        try {
            url = new URL(buildUri.toString());
        }catch (MalformedURLException e){
            e.printStackTrace();
        }
        return url;
    }


    public  static String getResponseFromHttpUrl(URL url) throws IOException{
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();
            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput){
                return scanner.next();
            }else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }







}
