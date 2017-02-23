package com.steveq.movieexplorer.api;


import android.util.Log;

import com.steveq.movieexplorer.model.MoviesOutput;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoviesCallback  implements Callback<MoviesOutput> {
    private static final String TAG = MoviesCallback.class.getSimpleName();
    private static MoviesCallback instance;
    private MoviesCallback(){}

    public static MoviesCallback getInstance(){
        if(instance == null){
            instance = new MoviesCallback();
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
