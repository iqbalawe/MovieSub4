package com.dicoding.picodiploma.moviesubmission4.service;

import com.dicoding.picodiploma.moviesubmission4.model.MovieResponse;
import com.dicoding.picodiploma.moviesubmission4.model.TvShowResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface Api {
    @GET("movie")
    Call<MovieResponse> getMovie(@Query("api_key") String apiKey);

    @GET("tv")
    Call<TvShowResponse> getTvShow(@Query("api_key") String apiKey);
}
