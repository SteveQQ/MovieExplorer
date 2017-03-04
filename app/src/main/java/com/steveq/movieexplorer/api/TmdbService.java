package com.steveq.movieexplorer.api;


import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.steveq.movieexplorer.R;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TmdbService {

    private static final String ENDPOINT_URL = "https://api.themoviedb.org/3/";
    private static final String TAG = TmdbService.class.getSimpleName();
    private Context mContext;

    public TmdbService(Context context) {
        mContext = context;
    }

    public TmdbAPI getService(){
        Gson gson = new GsonBuilder()
                            .setLenient()
                            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                            .setPrettyPrinting()
                            .create();

        OkHttpClient httpClient = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                HttpUrl originalUrl = original.url();

                HttpUrl url = originalUrl.newBuilder()
                                .addQueryParameter("api_key", mContext.getResources().getString(R.string.API_KEY))
                                .build();

                Request.Builder builder = original.newBuilder()
                                            .url(url);

                Request result = builder.build();
                return chain.proceed(result);
            }
        }).build();

        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(ENDPOINT_URL)
                                .addConverterFactory(GsonConverterFactory.create(gson))
                                .client(httpClient)
                                .build();

        return retrofit.create(TmdbAPI.class);
    }


}
