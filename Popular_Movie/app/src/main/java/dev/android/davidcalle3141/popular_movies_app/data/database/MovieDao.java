package dev.android.davidcalle3141.popular_movies_app.data.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;


@Dao
public interface MovieDao {
    @Query("SELECT * FROM MovieEntry ORDER BY id")
    LiveData<List<MovieEntry>> loadAllMovies();

    @Query("SELECT * FROM MovieEntry WHERE sortingPopular = 1 ORDER BY popularity DESC")
    LiveData<List<MovieEntry>> loadPopularSortedMovies();

    @Query("SELECT * FROM MovieEntry WHERE sortingRating = 1 ORDER BY rating DESC")
    LiveData<List<MovieEntry>> loadRatingSortedMovies();

    @Query("SELECT * FROM MovieEntry WHERE movieID = :movieID")
    LiveData<MovieEntry> loadMovieEntry(String movieID);

    @Query("DELETE FROM MovieEntry")
    void deleteMovies();


    @Insert
    void bulkInsert(List<MovieEntry> newMoviesFromNetwork);


    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateMovie(MovieEntry movie);

    @Delete
    void deleteMovie(MovieEntry movie);


}
