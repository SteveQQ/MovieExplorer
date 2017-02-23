package com.steveq.movieexplorer.api;


import com.steveq.movieexplorer.model.KeywordsOutput;
import com.steveq.movieexplorer.model.MoviesOutput;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface TmdbAPI {
    @GET("discover/movie")
    Call<MoviesOutput> getNewestMovies(@Query("release_date.gte") String release_date_gte,
                                       @Query("release_date.lte") String release_date_lte,
                                       @Query("page") int page);

    @GET("discover/movie")
    Call<MoviesOutput> getPopularMovies(@QueryMap Map<String, String> params);

    @GET("discover/movie")
    Call<MoviesOutput> getFilteredMovies(@QueryMap Map<String, String> params);

    @GET("search/keyword")
    Call<KeywordsOutput> getAvailableKeywords(@Query("query") String query);

}
