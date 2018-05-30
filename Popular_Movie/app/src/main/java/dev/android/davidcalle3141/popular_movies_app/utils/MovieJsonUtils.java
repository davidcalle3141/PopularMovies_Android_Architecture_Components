package dev.android.davidcalle3141.popular_movies_app.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import dev.android.davidcalle3141.popular_movies_app.models.Movie;

public final class MovieJsonUtils {


    public static ArrayList<Movie> parseMovieJson(String json, String posterSize) {


        try {
            JSONObject movie = new JSONObject(json);
            JSONArray results = movie.getJSONArray("results");
            ArrayList<Movie> movieModel = new ArrayList<>();
            String posterBaseUrl = "http://image.tmdb.org/t/p/"+posterSize+"/";


            for(int i = 0; i < results.length(); i++){
                movieModel.add(new Movie());
                movieModel.get(i).setImage_url(
                        posterBaseUrl+results.getJSONObject(i).getString("poster_path")
                );
                movieModel.get(i).setMovie_name(
                        results.getJSONObject(i).getString("title")
                );
                movieModel.get(i).setId(
                        results.getJSONObject(i).getString("id")
                );
                movieModel.get(i).setPlot_synopsis(
                        results.getJSONObject(i).getString("overview")
                );
                movieModel.get(i).setRating(
                        results.getJSONObject(i).getString("vote_average")
                );
                movieModel.get(i).setRelease_date(
                        results.getJSONObject(i).getString("release_date")
                );

            }


            return movieModel;
        }catch (JSONException e){
            e.printStackTrace();
        }
        return null;
    }
    public static void parseMovieTrailerJson(String json, Movie movie){
        try {
            HashMap<String,String> trailer = new HashMap<>();
            JSONObject trailersJson = new JSONObject(json);
            JSONArray results = trailersJson.getJSONArray("results");


            for(int i = 0; i < results.length(); i++){
                trailer.put("key", results.getJSONObject(i).getString("key"));
                trailer.put("name", results.getJSONObject(i).getString("name"));
                trailer.put("id", results.getJSONObject(i).getString("id"));
                movie.addTrailer(trailer);
                trailer.clear();


            }



        }catch (JSONException e){
            e.printStackTrace();
        }

    }

    public static void parseMovieReviewsJson(String json, Movie movie){
        try {
            HashMap<String, String> review = new HashMap<>();
            JSONObject reviewsJson = new JSONObject(json);
            JSONArray results = reviewsJson.getJSONArray("results");


            for(int i = 0; i < results.length(); i++){
                review.put("author", results.getJSONObject(i).getString("author"));
                review.put("content", results.getJSONObject(i).getString("content"));
                review.put("id", results.getJSONObject(i).getString("id"));
                review.put("url", results.getJSONObject(i).getString("url"));
                movie.addReview(review);
                review.clear();
            }



        }catch (JSONException e){
            e.printStackTrace();
        }

    }



}
