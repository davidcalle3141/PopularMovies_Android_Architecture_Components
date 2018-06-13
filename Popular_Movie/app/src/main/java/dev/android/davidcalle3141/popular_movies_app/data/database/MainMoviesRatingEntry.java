package dev.android.davidcalle3141.popular_movies_app.data.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class MainMoviesRatingEntry {
    private String movie_name;
    private String image_url;
    private String plot_synopsis;
    private String release_date;
    private String rating;
    private Boolean sortingPopular;
    private Boolean sortingRating;
    @PrimaryKey
    private String id;

    public MainMoviesRatingEntry(){

    }

    public MainMoviesRatingEntry(String movie_name, String image_url, String release_date, String rating, String plot_synopsis, Boolean sortingPopular,Boolean sortingRating, String id){
        this.movie_name = movie_name;
        this.image_url = image_url;
        this.release_date = release_date;
        this.rating = rating;
        this.plot_synopsis = plot_synopsis;
        this.sortingPopular = sortingPopular;
        this.sortingRating = sortingRating;
        this.id = id;


    }

    public String getMovie_name() {
        return movie_name;
    }

    public void setMovie_name(String movie_name) {
        this.movie_name = movie_name;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getPlot_synopsis() {
        return plot_synopsis;
    }

    public void setPlot_synopsis(String plot_synopsis) {
        this.plot_synopsis = plot_synopsis;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getId(){
        return this.id;
    }

    public void setId(String id) { this.id = id; }

    public Boolean getSortingPopular() {
        return sortingPopular;
    }

    public void setSortingPopular(Boolean sortingPopular) {
        this.sortingPopular = sortingPopular;
    }

    public Boolean getSortingRating() {
        return sortingRating;
    }

    public void setSortingRating(Boolean sortingRating) {
        this.sortingRating = sortingRating;
    }
}
