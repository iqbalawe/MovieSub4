package com.dicoding.picodiploma.moviesubmission4.db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.dicoding.picodiploma.moviesubmission4.Config;
import com.dicoding.picodiploma.moviesubmission4.model.Movie;
import com.dicoding.picodiploma.moviesubmission4.model.TvShow;


@androidx.room.Database(entities = {Movie.class, TvShow.class}, version = 1, exportSchema = false)

abstract class Database extends RoomDatabase {
    abstract MovieDao movieDao();

    abstract TvShowDao tvShowDao();

    private static Database INSTANCE;

    private static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {

        }
    };

    static Database getInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (Database.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            Database.class, Config.DB_MOVIE_NAME)
                            .addMigrations(MIGRATION_1_2)
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}

