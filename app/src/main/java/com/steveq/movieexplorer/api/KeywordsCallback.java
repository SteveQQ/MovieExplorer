package com.steveq.movieexplorer.api;


import android.app.Activity;
import android.util.Log;

import com.steveq.movieexplorer.model.KeywordsOutput;
import com.steveq.movieexplorer.ui.activities.MainActivity;
import com.steveq.movieexplorer.ui.activities.MainActivityView;
import com.steveq.movieexplorer.ui.fragments.FilterMoviesFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KeywordsCallback implements Callback<KeywordsOutput> {
    private static final String TAG = KeywordsCallback.class.getSimpleName();
    private MainActivityView mMainActivityView;
    public static KeywordsOutput currentKeywordsOutput;
    private static KeywordsCallback instance;
    private KeywordsCallback(){
        //mMainActivityView = presenter;
    }

    public static KeywordsCallback getInstance(){
        if(instance == null){
            instance = new KeywordsCallback();
        }
        return instance;
    }

    @Override
    public void onResponse(Call<KeywordsOutput> call, Response<KeywordsOutput> response) {
        Log.d(TAG, "Request Completed!");
        currentKeywordsOutput = response.body();
        //mMainActivityView.updateAutoCompletion();
        ((FilterMoviesFragment)MainActivity.fragmentsPoll.get(2)).updateAutoCompletion();
    }

    @Override
    public void onFailure(Call<KeywordsOutput> call, Throwable t) {
        Log.d(TAG, "Request Failed!");
        Log.d(TAG, call.request().url().toString());
    }
}
