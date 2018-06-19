package dev.android.davidcalle3141.popular_movies_app.data.network;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import dev.android.davidcalle3141.popular_movies_app.data.database.MovieEntry;

public final class MovieJsonUtils {


    MovieResponse parseMovieJson(String json, Boolean sortingPopular, Boolean sortingRating) {


        try {
            JSONObject movie = new JSONObject(json);
            JSONArray results = movie.getJSONArray("results");
            List<MovieEntry> movieModel = new ArrayList<>();
            String posterBaseUrl = "http://image.tmdb.org/t/p/"+ "w185" +"/";


            for(int i = 0; i < results.length(); i++){
                movieModel.add(new MovieEntry());
                movieModel.get(i).setImage_url(
                        posterBaseUrl+results.getJSONObject(i).getString("poster_path")
                );
                movieModel.get(i).setMovie_name(
                        results.getJSONObject(i).getString("title")
                );
                movieModel.get(i).setMovieID(
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
                movieModel.get(i).setPopularity(
                        results.getJSONObject(i).getString("popularity")
                );

                movieModel.get(i).setSortingPopular(sortingPopular);
                movieModel.get(i).setSortingRating(sortingRating);

            }


            return new MovieResponse(movieModel);
        }catch (JSONException e){
            e.printStackTrace();
        }
        return null;
    }
    ReviewsAndTrailersResponse parseMovieTrailerJson(String json){
        try {
            List<HashMap<String, String>> TrailerReviews = new ArrayList<>();
            HashMap<String, String> trailer;
            JSONObject trailersJson = new JSONObject(json);
            JSONArray results = trailersJson.getJSONArray("results");


            for(int i = 0; i < results.length(); i++){
                trailer = new HashMap<>();
                trailer.put("key", results.getJSONObject(i).getString("key"));
                trailer.put("name", results.getJSONObject(i).getString("name"));
                trailer.put("id", results.getJSONObject(i).getString("id"));
                TrailerReviews.add(trailer);
            }


        return new ReviewsAndTrailersResponse(TrailerReviews);
        }catch (JSONException e){
            e.printStackTrace();
        }
        return null;

    }

    ReviewsAndTrailersResponse parseMovieReviewsJson(String json){
        try {
            List<HashMap<String, String>> MovieReviews = new ArrayList<>();
            HashMap<String, String> review;
            JSONObject reviewsJson = new JSONObject(json);
            JSONArray results = reviewsJson.getJSONArray("results");


            for(int i = 0; i < results.length(); i++){
                review = new HashMap<>();
                review.put("author", results.getJSONObject(i).getString("author"));
                review.put("content", results.getJSONObject(i).getString("content"));
                review.put("id", results.getJSONObject(i).getString("id"));
                review.put("url", results.getJSONObject(i).getString("url"));
                MovieReviews.add(review);
            }

            return new ReviewsAndTrailersResponse(MovieReviews);
        }catch (JSONException e){
            e.printStackTrace();
        }

        return null;
    }



}
