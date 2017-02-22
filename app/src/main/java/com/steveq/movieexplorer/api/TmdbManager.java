package com.steveq.movieexplorer.api;


import android.content.Context;
import android.util.Log;

import com.steveq.movieexplorer.model.MoviesOutput;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TmdbManager  implements Callback<MoviesOutput> {


    private static final String TAG = TmdbManager.class.getSimpleName();
    private Context mContext;
    private TmdbService mService;

    public TmdbManager(Context context) {
        mContext = context;
        mService = new TmdbService(context);
    }

    public void getNewestMovies(){
        Call<MoviesOutput> call = mService.getService().
                getNewestMovies(
                        getShiftedBackDate(30),
                        getCurrentDate(),
                        1
                );
        call.enqueue(this);
    }

    public void getPopularMovies(){
        Map<String, String> params = new HashMap<>();
        params.put("sort_by", "popularity.desc");
        params.put("primary_release_year", getCurrentDate().substring(0, 4));
        params.put("page", "1");

        Call<MoviesOutput> call = mService.getService()
                                    .getPopularMovies(params);
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
    public void onResponse(Call<MoviesOutput> call, Response<MoviesOutput> response) {
        Log.d(TAG, "Request Completed!");
    }

    @Override
    public void onFailure(Call<MoviesOutput> call, Throwable t) {
        Log.d(TAG, "Request Failed!");
    }
}
