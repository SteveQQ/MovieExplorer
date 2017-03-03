package com.steveq.movieexplorer.ui.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import com.steveq.movieexplorer.R;
import com.steveq.movieexplorer.adapters.ImagesGridAdapter;
import com.steveq.movieexplorer.api.TmdbManager;
import com.steveq.movieexplorer.model.Movie;
import com.steveq.movieexplorer.model.MoviesRoot;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class BaseFragment extends Fragment implements Callback<MoviesRoot>, SwipeRefreshLayout.OnRefreshListener {
    private static final String TAG = NewestMoviesFragment.class.getSimpleName();
    private TmdbManager mTmdbManager;

    @BindView(R.id.gridView)
    public GridView gridView;

    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;

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
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_grid_movies, container, false);
        ButterKnife.bind(this, viewGroup);
        gridView.setEmptyView(viewGroup.findViewById(android.R.id.empty));
        execution();
        swipeRefresh.setOnRefreshListener(this);
        Log.d(TAG, "onCreateView");
        return viewGroup;
    }

    @Override
    public void onRefresh() {
        execution();
    }

    abstract void execution();

    public void newestMovies(){
        mTmdbManager.getNewestMovies(this);
    }

    public void popularMovies(){
        mTmdbManager.getPopularMovies(this);
    }

    @Override
    public void onResponse(Call<MoviesRoot> call, Response<MoviesRoot> response) {
        Log.d(TAG, "Request Completed!");
        gridView.setAdapter(new ImagesGridAdapter(getActivity(),
                addGenreInfo(response.body().getResults())));
        swipeRefresh.setRefreshing(false);
    }

    public static List<Movie> addGenreInfo(List<Movie> movs){
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
    public void onFailure(Call<MoviesRoot> call, Throwable t) {
        Log.d(TAG, "Request Failed!");
        Log.d(TAG, call.request().url().toString());
        Toast.makeText(getContext(), "No internet connection", Toast.LENGTH_SHORT).show();
        swipeRefresh.setRefreshing(false);
    }
}
