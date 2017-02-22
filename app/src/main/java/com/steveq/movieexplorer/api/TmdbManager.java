package com.steveq.movieexplorer.api;


import android.content.Context;
import android.util.Log;

import com.steveq.movieexplorer.R;
import com.steveq.movieexplorer.model.PopularMovies;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TmdbManager  implements Callback<PopularMovies> {


    private static final String TAG = TmdbManager.class.getSimpleName();
    private Context mContext;
    private TmdbService mService;

    public TmdbManager(Context context) {
        mContext = context;
        mService = new TmdbService(context);
    }

    public void getPopularMovies(){
        Call<PopularMovies> call = mService.getService().
                getPopularMovies(
                        getShiftedBackDate(30),
                        getCurrentDate(),
                        1
                );
        call.enqueue(this);
    }

    private String getCurrentDate(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        return sdf.format(calendar.getTime());
    }

    private String getShiftedBackDate(int numDays){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();

        calendar.add(Calendar.DAY_OF_MONTH, -numDays);
        return sdf.format(calendar.getTime());
    }

    @Override
    public void onResponse(Call<PopularMovies> call, Response<PopularMovies> response) {
        Log.d(TAG, "Request Completed!");
        Log.d(TAG, response.headers().toString());
    }

    @Override
    public void onFailure(Call<PopularMovies> call, Throwable t) {
        Log.d(TAG, "Request Failed!");
        Log.d(TAG, call.request().url().toString());
        Log.d(TAG, t.getMessage());
    }
}
