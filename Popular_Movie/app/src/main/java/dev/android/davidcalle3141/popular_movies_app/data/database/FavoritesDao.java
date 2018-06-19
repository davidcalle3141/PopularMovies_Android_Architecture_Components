package dev.android.davidcalle3141.popular_movies_app.data.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;


@Dao
public interface FavoritesDao {

    @Query("SELECT * FROM FavoritesEntry")
    LiveData<List<FavoritesEntry>> loadAllFavorites();

    @Query("DELETE FROM FavoritesEntry WHERE movieID = :primaryKey")
    void deleteFavorite(String primaryKey);

    @Query("SELECT * FROM FavoritesEntry WHERE movieID = :primaryKey")
    LiveData<FavoritesEntry> getFavoriteById(String primaryKey);

    @Insert
    void bulkInsert(List<FavoritesEntry> newFavorites);

    @Insert
    void Insert(FavoritesEntry favoritesEntry);

    @Update
    void Update(FavoritesEntry favoritesEntry);


}