package com.steveq.movieexplorer.api;


import com.steveq.movieexplorer.model.KeywordsRoot;
import com.steveq.movieexplorer.model.MoviesRoot;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface TmdbAPI {
    @GET("discover/movie")
    Call<MoviesRoot> getNewestMovies(@Query("release_date.gte") String release_date_gte,
                                     @Query("release_date.lte") String release_date_lte,
                                     @Query("page") int page);

    @GET("discover/movie")
    Call<MoviesRoot> getPopularMovies(@QueryMap Map<String, String> params);

    @GET("discover/movie")
    Call<MoviesRoot> getFilteredMovies(@QueryMap Map<String, String> params);

    @GET("search/keyword")
    Call<KeywordsRoot> getAvailableKeywords(@Query("query") String query);

    @GET("search/multi")
    Call<ResponseBody> getSearchedData(@Query(value="query", encoded = true) String query);

}
