package com.dicoding.picodiploma.moviesubmission4.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.dicoding.picodiploma.moviesubmission4.db.TvShowRepository;
import com.dicoding.picodiploma.moviesubmission4.model.TvShow;

import java.util.List;


public class TvShowViewModel extends AndroidViewModel {
    private TvShowRepository tvShowRepository;
    private LiveData<List<TvShow>> listLiveData;
    private MutableLiveData<List<TvShow>> listTvShow;

    public TvShowViewModel(Application application) {
        super(application);
        tvShowRepository = new TvShowRepository(application);
        listLiveData = tvShowRepository.getAllTvShow();
        listTvShow = tvShowRepository.getTvShowData();
    }

    public LiveData<List<TvShow>> getIdTvShow(int id) {
        return tvShowRepository.getIdTvShow(id);
    }

    public LiveData<List<TvShow>> getAllTvShow() {
        return listLiveData;
    }

    public void insertTvShow(TvShow tvShow) {
        tvShowRepository.insert(tvShow);
    }

    public void deleteSelectedTvShow(TvShow tvShow) {
        tvShowRepository.deleteSelectedTvShow(tvShow);
    }

    public LiveData<List<TvShow>> getTvShowData() {
        return listTvShow;
    }
}
