package com.steveq.movieexplorer.api;


import com.steveq.movieexplorer.model.PopularMovies;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface PopularMoviesAPI {
    @GET("discover/movie")
    Call<List<PopularMovies>> getPopularMovies(@Query("release_date.gte") String release_date_gte,
                                               @Query("release_date.lte") String release_date_lte,
                                               @Query("page") int page,
                                               @Query("api_key") String api_key);
}
