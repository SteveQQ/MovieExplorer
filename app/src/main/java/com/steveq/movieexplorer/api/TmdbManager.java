package com.steveq.movieexplorer.api;


import android.content.Context;
import android.util.Log;

import com.steveq.movieexplorer.model.Genre;
import com.steveq.movieexplorer.model.KeywordsOutput;
import com.steveq.movieexplorer.model.MoviesOutput;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TmdbManager {


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
        call.enqueue(MoviesCallback.getInstance());
    }

    public void getPopularMovies(){
        Map<String, String> params = new HashMap<>();
        params.put("sort_by", "popularity.desc");
        params.put("primary_release_year", getCurrentDate().substring(0, 4));
        params.put("page", "1");

        Call<MoviesOutput> call = mService.getService()
                                    .getPopularMovies(params);
        call.enqueue(MoviesCallback.getInstance());
    }

    public void getFilteredParams(int year, Genre genre, List<String> keywords){
        Map<String, String> params = new HashMap<>();
        params.put("primary_release_year", String.valueOf(year));
        params.put("with_genres", String.valueOf(genre.getId()));
        params.put("with_keywords", prepareKeywords(keywords));

        Call<MoviesOutput> call = mService.getService()
                                    .getFilteredMovies(params);
        call.enqueue(MoviesCallback.getInstance());
    }

    public void getAvailableKeywords(String str){
        Call<KeywordsOutput> call = mService.getService().
                getAvailableKeywords(str);

        call.enqueue(KeywordsCallback.getInstance());
    }

    private String prepareKeywords(List<String> keywords) {
        final StringBuilder builder = new StringBuilder();
        for(String s : keywords){
            builder.append(s);
            builder.append(",");
        }
        builder.deleteCharAt(builder.length()-1);
        return builder.toString();
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
}
