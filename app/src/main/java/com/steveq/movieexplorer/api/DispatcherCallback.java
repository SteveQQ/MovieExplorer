package com.steveq.movieexplorer.api;


import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.steveq.movieexplorer.model.MoviesRoot;
import com.steveq.movieexplorer.model.PersonRoot;

import java.io.BufferedReader;
import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DispatcherCallback implements Callback<ResponseBody> {
    public static final String TAG = DispatcherCallback.class.getSimpleName();
    private static DispatcherCallback instance;
    enum MediaType {MOVIE, PERSON}

    public DispatcherCallback() {
    }

    public static DispatcherCallback getInstance(){
        if(instance == null){
            instance = new DispatcherCallback();
        }
        return instance;
    }

    public MediaType getMediaType(String json){
        Gson gson = new Gson();
        JsonObject root = new JsonParser().parse(json).getAsJsonObject();
        JsonArray results = root.getAsJsonArray("results");
        JsonObject controlObject = results.get(0).getAsJsonObject();
        String mediaType = controlObject.get("media_type").getAsString();
        Log.d(TAG, mediaType);
        return MediaType.valueOf(mediaType.toUpperCase());
    }

    public String getResponseBody(ResponseBody response){
        BufferedReader reader = null;
        StringBuilder builder = new StringBuilder();
        reader = new BufferedReader(response.charStream());
        String line;
        try{
            while((line = reader.readLine()) != null){
                builder.append(line);
            }
        } catch (IOException ioe){
            ioe.printStackTrace();
        }
        return builder.toString();
    }

    @Override
    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
        String body = getResponseBody(response.body());
        MediaType mt = getMediaType(body);
        Gson gson = new Gson();
        if(mt == MediaType.MOVIE){
            MoviesRoot mo = gson.fromJson(body, MoviesRoot.class);
        } else if (mt == MediaType.PERSON){
            PersonRoot po = gson.fromJson(body, PersonRoot.class);
        }
    }

    @Override
    public void onFailure(Call<ResponseBody> call, Throwable t) {
        Log.d(TAG, "Response Failure");
    }
}
