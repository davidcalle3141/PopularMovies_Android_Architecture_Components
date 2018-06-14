package dev.android.davidcalle3141.popular_movies_app.data.network;

import android.support.annotation.NonNull;

import java.util.List;

import dev.android.davidcalle3141.popular_movies_app.data.database.MovieEntry;

public class MovieResponse {


    private final List<MovieEntry> mMovieEntries;

    MovieResponse(@NonNull final List<MovieEntry> movieEntries){
        this.mMovieEntries = movieEntries;
    }

    public void AddToMovieResponse(@NonNull List<MovieEntry> movieEntries){
        mMovieEntries.addAll(movieEntries);

    }

    public List<MovieEntry> getMovieEntries(){
        return mMovieEntries;
    }
}
