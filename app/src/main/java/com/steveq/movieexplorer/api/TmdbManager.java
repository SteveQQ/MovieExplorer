package com.steveq.movieexplorer.api;


import android.app.Activity;

import com.steveq.movieexplorer.model.Genre;
import com.steveq.movieexplorer.model.Keyword;
import com.steveq.movieexplorer.model.KeywordsOutput;
import com.steveq.movieexplorer.model.MoviesOutput;
import com.steveq.movieexplorer.ui.activities.MainActivityView;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

public class TmdbManager {


    private static final String TAG = TmdbManager.class.getSimpleName();
    private Activity mActivity;
    private TmdbService mService;

    public TmdbManager(Activity context) {
        mActivity = context;
        mService = new TmdbService(context);
    }

    public void getNewestMovies(Callback<MoviesOutput> callback){
        Call<MoviesOutput> call = mService.getService().
                getNewestMovies(
                        getShiftedBackDate(30),
                        getCurrentDate(),
                        1
                );
        call.enqueue(callback);
    }

    public void getPopularMovies(Callback<MoviesOutput> callback){
        Map<String, String> params = new HashMap<>();
        params.put("sort_by", "popularity.desc");
        params.put("primary_release_year", getCurrentDate().substring(0, 4));
        params.put("page", "1");

        Call<MoviesOutput> call = mService.getService()
                                    .getPopularMovies(params);
        call.enqueue(callback);
    }

    public void getFilteredParams(int year, Genre genre, String[] keywords){
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

    public void getSearchedData(String str){
        Call<ResponseBody> call = mService.getService()
                                    .getSearchedData(str);

        call.enqueue(DispatcherCallback.getInstance());
    }

    private String prepareKeywords(String[] keywords) {
        final StringBuilder builder = new StringBuilder();
        for(String s : keywords){
            if(getKeywordId(s) >= 0) {
                builder.append(getKeywordId(s));
                builder.append(",");
            }
        }
        builder.deleteCharAt(builder.length()-1);
        return builder.toString();
    }

    private int getKeywordId(String s) {
        for(Keyword k : KeywordsCallback.currentKeywordsOutput.results){
            if(k.getName().equals(s)){
                return k.getId();
            }
        }
        return -1;
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
