package com.steveq.movieexplorer.ui.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.stmt.query.In;
import com.steveq.movieexplorer.R;
import com.steveq.movieexplorer.api.TmdbManager;
import com.steveq.movieexplorer.db.DbOperationManager;
import com.steveq.movieexplorer.model.Movie;
import com.steveq.movieexplorer.model.MoviesOutput;

import java.sql.SQLException;
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
    public GridView gridView;

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
        gridView.setAdapter(new ImagesGridAdapter(getActivity(),
                addGenreInfo(response.body().getResults())));
    }

    private List<Movie> addGenreInfo(List<Movie> movs){
        for(Movie m : movs){
            if(m.getGenre_ids().size() > 0) {
                m.setGenre(m.getGenre_ids().get(0));
            } else {
                m.setGenre(-1);
            }
        }
        return movs;
    }

    @Override
    public void onFailure(Call<MoviesOutput> call, Throwable t) {
        Log.d(TAG, "Request Failed!");
        Log.d(TAG, call.request().url().toString());
    }
}
