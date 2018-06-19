package dev.android.davidcalle3141.popular_movies_app.data.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class FavoritesEntry {
    private String movie_name;
    private String image_url;
    private String plot_synopsis;
    private String release_date;
    @PrimaryKey
    @NonNull
    private String movieID;




    public FavoritesEntry(String movie_name, String image_url,
                          String release_date, String plot_synopsis,
                          @NonNull String movieID){
        this.movie_name = movie_name;
        this.image_url = image_url;
        this.release_date = release_date;
        this.plot_synopsis = plot_synopsis;
        this.movieID = movieID;

    }

    @Ignore
    public FavoritesEntry(){}
    @Ignore
    public FavoritesEntry(MovieEntry movieEntry) {
        this.movie_name = movieEntry.getMovie_name();
        this.image_url = movieEntry.getImage_url();
        this.release_date = movieEntry.getRelease_date();
        this.plot_synopsis = movieEntry.getPlot_synopsis();
        this.movieID = movieEntry.getMovieID();
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





    @NonNull
    public String getMovieID() {
        return movieID;
    }
    public void setMovieID(@NonNull String movieID) {
        this.movieID = movieID;
    }


}
