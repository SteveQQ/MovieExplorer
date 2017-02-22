package com.steveq.movieexplorer.api;


import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.steveq.movieexplorer.R;
import com.steveq.movieexplorer.model.PopularMovies;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import okhttp3.Credentials;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TmdbController implements Callback<PopularMovies> {

    private static final String ENDPOINT_URL = "https://api.themoviedb.org/3/";
    private static final String TAG = TmdbController.class.getSimpleName();
    private Context mContext;

    public TmdbController(Context context) {
        mContext = context;
    }

    private PopularMoviesAPI prepareApiCall(){
        Gson gson = new GsonBuilder()
                            .setLenient()
                            .setDateFormat("yyyy-MM-dd")
                            .setPrettyPrinting()
                            .create();

        OkHttpClient httpClient = new OkHttpClient.Builder().addNetworkInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                HttpUrl originalUrl = request.url();

                HttpUrl url = originalUrl.newBuilder()
                                .addQueryParameter("api_key", mContext.getResources().getString(R.string.API_KEY))
                                .build();

                Request.Builder builder = request.newBuilder()
                                            .url(url);

                Request result = builder.build();
                return chain.proceed(result);
            }
        }).build();

        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(ENDPOINT_URL)
                                .addConverterFactory(GsonConverterFactory.create(gson))
                                .build();

        return retrofit.create(PopularMoviesAPI.class);
    }

    public void getPopularMovies(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        String currentDate = sdf.format(calendar.getTime());

        calendar.add(Calendar.DAY_OF_MONTH, -30);
        String minusMonth = sdf.format(calendar.getTime());
        Call<PopularMovies> call = prepareApiCall().
                                            getPopularMovies(
                                                    minusMonth,
                                                    currentDate,
                                                    1,
                                                    mContext.getResources().getString(R.string.API_KEY)
                                            );
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<PopularMovies> call, Response<PopularMovies> response) {
        Log.d(TAG, "Request Completed!");
        Log.d(TAG, response.body().toString());
    }

    @Override
    public void onFailure(Call<PopularMovies> call, Throwable t) {
        Log.d(TAG, "Request Failed!");
        Log.d(TAG, call.request().url().toString());
        Log.d(TAG, t.getMessage());
    }
}
