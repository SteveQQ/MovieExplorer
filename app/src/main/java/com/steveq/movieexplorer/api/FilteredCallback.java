package com.steveq.movieexplorer.api;


import android.util.Log;

import com.steveq.movieexplorer.model.MoviesOutput;
import com.steveq.movieexplorer.ui.activities.MainActivity;
import com.steveq.movieexplorer.ui.fragments.FilteredMoviesFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FilteredCallback implements Callback<MoviesOutput> {
    private static final String TAG = FilteredCallback.class.getSimpleName();
    private static FilteredCallback instance;
    private FilteredCallback(){}

    public static FilteredCallback getInstance(){
        if(instance == null){
            instance = new FilteredCallback();
        }
        return instance;
    }

    @Override
    public void onResponse(Call<MoviesOutput> call, Response<MoviesOutput> response) {
        Log.d(TAG, "Request Completed!");




    }

    @Override
    public void onFailure(Call<MoviesOutput> call, Throwable t) {
        Log.d(TAG, "Request Failed!");
        Log.d(TAG, call.request().url().toString());
    }
}
