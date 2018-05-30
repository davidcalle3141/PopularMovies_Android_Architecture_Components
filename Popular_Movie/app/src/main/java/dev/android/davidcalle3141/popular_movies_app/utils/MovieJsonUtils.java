package dev.android.davidcalle3141.popular_movies_app.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

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
}