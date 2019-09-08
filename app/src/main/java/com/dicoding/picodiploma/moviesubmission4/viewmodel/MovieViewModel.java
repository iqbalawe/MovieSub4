package com.dicoding.picodiploma.moviesubmission4.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.dicoding.picodiploma.moviesubmission4.db.MovieRepository;
import com.dicoding.picodiploma.moviesubmission4.model.Movie;

import java.util.List;

public class MovieViewModel extends AndroidViewModel {
    private MovieRepository movieRepository;
    private LiveData<List<Movie>> listLiveData;
    private MutableLiveData<List<Movie>> listMovie;

    public MovieViewModel(Application application) {
        super(application);
        movieRepository = new MovieRepository(application);
        listLiveData = movieRepository.getAllMovie();
        listMovie = movieRepository.getMovie();
    }

    public LiveData<List<Movie>> getIdMovie(int id) {
        return movieRepository.getIdMovie(id);
    }

    public LiveData<List<Movie>> getAllMovie() {
        return listLiveData;
    }

    public void insert(Movie movie) {
        movieRepository.insert(movie);
    }

    public void deleteSelectedMovie(Movie movie) {
        movieRepository.deletedSelectedMovie(movie);
    }

    public LiveData<List<Movie>> getMovieData() {
        return listMovie;
    }
}
