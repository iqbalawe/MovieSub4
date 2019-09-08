package com.dicoding.picodiploma.moviesubmission4.db;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.dicoding.picodiploma.moviesubmission4.Config;
import com.dicoding.picodiploma.moviesubmission4.model.Movie;
import com.dicoding.picodiploma.moviesubmission4.model.MovieResponse;
import com.dicoding.picodiploma.moviesubmission4.service.Api;
import com.dicoding.picodiploma.moviesubmission4.service.RetrofitMovieService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MovieRepository {
    private MovieDao movieDao;
    private LiveData<List<Movie>> listLiveData;
    private Api api;

    public MovieRepository(Application application) {
        Database database = Database.getInstance(application);
        movieDao = database.movieDao();
        listLiveData = movieDao.getAllMovie();
        api = RetrofitMovieService.createService(Api.class);
    }

    public LiveData<List<Movie>> getIdMovie(int id) {
        return movieDao.getMovieId(id);
    }

    public LiveData<List<Movie>> getAllMovie() {
        return listLiveData;
    }

    public void insert(Movie movie) {
        new insertAsyncTask(movieDao).execute(movie);
    }

    private static class insertAsyncTask extends AsyncTask<Movie, Void, Void> {
        private MovieDao mAsyncTaskDao;

        insertAsyncTask(MovieDao dao) {
            this.mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Movie... movieResults) {
            mAsyncTaskDao.insertMovie(movieResults[0]);
            return null;
        }
    }

    // do delete selected movie in thread worker
    public void deletedSelectedMovie(Movie movie) {
        new deleteSelectedMovieAsyncTask(movieDao).execute(movie);
    }

    private static class deleteSelectedMovieAsyncTask extends AsyncTask<Movie, Void, Void> {

        private MovieDao mAsyncTaskDao;

        deleteSelectedMovieAsyncTask(MovieDao dao) {
            this.mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Movie... movieResults) {
            mAsyncTaskDao.deleteSelectedMovie(movieResults[0]);
            return null;
        }
    }

    public MutableLiveData<List<Movie>> getMovie() {
        MutableLiveData<List<Movie>> listMovie = new MutableLiveData<>();
        Call<MovieResponse> call = api.getMovie(Config.API_KEY);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful()) {
                    listMovie.setValue(response.body().getResults());
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                listMovie.setValue(null);
            }
        });
        return listMovie;
    }
}
