package com.dicoding.picodiploma.moviesubmission4.db;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.dicoding.picodiploma.moviesubmission4.Config;
import com.dicoding.picodiploma.moviesubmission4.model.TvShow;
import com.dicoding.picodiploma.moviesubmission4.model.TvShowResponse;
import com.dicoding.picodiploma.moviesubmission4.service.Api;
import com.dicoding.picodiploma.moviesubmission4.service.RetrofitTvShowService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TvShowRepository {
    private TvShowDao tvShowDao;
    private LiveData<List<TvShow>> listLiveData;
    private Api api;

    public TvShowRepository(Application application) {
        Database database = Database.getInstance(application);
        tvShowDao = database.tvShowDao();
        listLiveData = tvShowDao.getAllTvShow();
        api = RetrofitTvShowService.createService(Api.class);
    }

    public LiveData<List<TvShow>> getIdTvShow(int id) {
        return tvShowDao.getTvShowId(id);
    }

    public LiveData<List<TvShow>> getAllTvShow() {
        return listLiveData;
    }

    public void insert(TvShow tvShow) {
        new insertAsyncTask(tvShowDao).execute(tvShow);
    }

    private static class insertAsyncTask extends AsyncTask<TvShow, Void, Void> {
        private TvShowDao tvShowDao;

        insertAsyncTask(TvShowDao dao) {
            tvShowDao = dao;
        }

        @Override
        protected Void doInBackground(TvShow... tvShowResults) {
            tvShowDao.insertTvShow(tvShowResults[0]);
            return null;
        }
    }

    public void deleteSelectedTvShow(TvShow tvShow) {
        new deletedSelectedTvAsyncTask(tvShowDao).execute(tvShow);
    }

    private static class deletedSelectedTvAsyncTask extends AsyncTask<TvShow, Void, Void> {
        private TvShowDao tvShowDao;

        deletedSelectedTvAsyncTask(TvShowDao dao) {
            tvShowDao = dao;
        }

        @Override
        protected Void doInBackground(TvShow... tvShowResults) {
            tvShowDao.deleteSelectedTvShow(tvShowResults[0]);
            return null;
        }
    }

    public MutableLiveData<List<TvShow>> getTvShowData() {
        MutableLiveData<List<TvShow>> listTv = new MutableLiveData<>();
        Call<TvShowResponse> call = api.getTvShow(Config.API_KEY);
        call.enqueue(new Callback<TvShowResponse>() {
            @Override
            public void onResponse(Call<TvShowResponse> call, Response<TvShowResponse> response) {
                listTv.setValue(response.body().getResults());
            }

            @Override
            public void onFailure(Call<TvShowResponse> call, Throwable t) {
                listTv.setValue(null);
            }
        });
        return listTv;
    }
}
