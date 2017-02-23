package com.steveq.movieexplorer.api;


import android.util.Log;

import com.steveq.movieexplorer.model.Keyword;
import com.steveq.movieexplorer.model.KeywordsOutput;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KeywordsCallback implements Callback<KeywordsOutput> {
    private static final String TAG = KeywordsCallback.class.getSimpleName();
    private static KeywordsCallback instance;
    private KeywordsCallback(){}

    public static KeywordsCallback getInstance(){
        if(instance == null){
            instance = new KeywordsCallback();
        }
        return instance;
    }

    @Override
    public void onResponse(Call<KeywordsOutput> call, Response<KeywordsOutput> response) {
        Log.d(TAG, "Request Completed!");
        KeywordsOutput.results = response.body().getResults();
    }

    @Override
    public void onFailure(Call<KeywordsOutput> call, Throwable t) {
        Log.d(TAG, "Request Failed!");
        Log.d(TAG, call.request().url().toString());
    }
}
