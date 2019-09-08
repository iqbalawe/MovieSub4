package com.dicoding.picodiploma.moviesubmission4.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.dicoding.picodiploma.moviesubmission4.model.TvShow;

import java.util.List;


@Dao
public interface TvShowDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertTvShow(TvShow tvShow);

    @Delete
    void deleteSelectedTvShow(TvShow tvShow);

    @Query("SELECT * FROM table_tv_show")
    LiveData<List<TvShow>> getAllTvShow();

    @Query("SELECT * FROM table_tv_show WHERE id = :id")
    LiveData<List<TvShow>> getTvShowId(int id);
}
