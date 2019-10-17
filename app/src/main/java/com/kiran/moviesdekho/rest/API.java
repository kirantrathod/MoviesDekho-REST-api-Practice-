package com.kiran.moviesdekho.rest;

import com.kiran.moviesdekho.Config;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class API {
    private static <T> T builder(Class<T> endpoint) {
        return new Retrofit.Builder()
                .baseUrl(Config.API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(endpoint);
    }

    public static MovieServices movies() {
        return builder(MovieServices.class);
    }
}
