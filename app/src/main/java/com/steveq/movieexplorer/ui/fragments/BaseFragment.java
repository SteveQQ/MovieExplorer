package com.steveq.movieexplorer.ui.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.steveq.movieexplorer.R;
import com.steveq.movieexplorer.api.TmdbManager;
import com.steveq.movieexplorer.model.Movie;
import com.steveq.movieexplorer.model.MoviesOutput;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BaseFragment extends Fragment implements Callback<MoviesOutput> {
    private static final String TAG = NewestMoviesFragment.class.getSimpleName();
    private TmdbManager mTmdbManager;

    @BindView(R.id.gridView)
    GridView gridView;

    public BaseFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTmdbManager = new TmdbManager(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_newest_movies, container, false);
        ButterKnife.bind(this, viewGroup);
        Log.d(TAG, "onCreateView");
        return viewGroup;
    }

    public void newestMovies(){
        mTmdbManager.getNewestMovies(this);
    }

    public void popularMovies(){
        mTmdbManager.getPopularMovies(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResponse(Call<MoviesOutput> call, Response<MoviesOutput> response) {
        Log.d(TAG, "Request Completed!");
        List<String> urls = new ArrayList<>();
//        for(Movie m : response.body().getResults()){
//            urls.add(BASE_IMAGE_URL + m.getPoster_path());
//        }
        gridView.setAdapter(new ImagesGridAdapter(getContext(), response.body().getResults()));
    }

    @Override
    public void onFailure(Call<MoviesOutput> call, Throwable t) {
        Log.d(TAG, "Request Failed!");
        Log.d(TAG, call.request().url().toString());
    }
}
