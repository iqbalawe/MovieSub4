package com.dicoding.picodiploma.moviesubmission4.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.dicoding.picodiploma.moviesubmission4.model.Movie;

import java.util.List;


@Dao
public interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMovie(Movie movie);

    @Delete
    void deleteSelectedMovie(Movie movie);

    @Query("SELECT * FROM table_movie")
    LiveData<List<Movie>> getAllMovie();

    @Query("SELECT * FROM table_movie WHERE id = :id")
    LiveData<List<Movie>> getMovieId(int id);
}
