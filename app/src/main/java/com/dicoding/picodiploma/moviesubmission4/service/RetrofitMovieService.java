package com.dicoding.picodiploma.moviesubmission4.service;

import com.dicoding.picodiploma.moviesubmission4.Config;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitMovieService {
    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(Config.MOVIE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public static <S> S createService(Class<S> serviceClass) {
        return retrofit.create(serviceClass);
    }

}
