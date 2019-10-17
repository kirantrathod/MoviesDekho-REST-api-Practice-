package com.kiran.moviesdekho.rest;

import com.kiran.moviesdekho.model.movies.MovieResponse;
import com.kiran.moviesdekho.model.search.SearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieServices {
    @GET("movie/top_rated")
    Call<MovieResponse> getTopRatedMovies(@Query("api_key") String apikey);

    //MOVIE SEARCH AUTOCOMPLETE
    @GET("/3/search/movie")
    Call<SearchResponse> search(@Query("api_key") String apiKey, @Query("query") String query);

    //MOVIE DETAIL
    @GET("/3/movie/{id}")
    Call<com.kiran.moviesdekho.model.movie.MovieResponse> movieDetails(@Path("id") int movieID, @Query("api_key") String apiKey);
}
