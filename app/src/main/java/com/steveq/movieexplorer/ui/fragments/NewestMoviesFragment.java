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

/**
 * A simple {@link Fragment} subclass.
 */
public class NewestMoviesFragment extends Fragment implements Callback<MoviesOutput> {
    private static final String TAG = NewestMoviesFragment.class.getSimpleName();
    TmdbManager mTmdbManager;
    public static final String BASE_IMAGE_URL = "https://image.tmdb.org/t/p/w300";

    @BindView(R.id.gridView) GridView gridView;

    public NewestMoviesFragment() {
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

        mTmdbManager.getNewestMovies(this);
        return viewGroup;
    }

    @Override
    public void onResponse(Call<MoviesOutput> call, Response<MoviesOutput> response) {
        Log.d(TAG, "Request Completed!");
        List<String> urls = new ArrayList<>();
        for(Movie m : response.body().getResults()){
            urls.add(BASE_IMAGE_URL + m.getPoster_path());
        }
        gridView.setAdapter(new ImagesGridAdapter(getContext(), urls));
    }

    @Override
    public void onFailure(Call<MoviesOutput> call, Throwable t) {
        Log.d(TAG, "Request Failed!");
        Log.d(TAG, call.request().url().toString());
    }
}
