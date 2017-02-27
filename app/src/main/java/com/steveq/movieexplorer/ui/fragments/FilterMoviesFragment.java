package com.steveq.movieexplorer.ui.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.steveq.movieexplorer.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FilterMoviesFragment extends Fragment {


    private static final String TAG = FilterMoviesFragment.class.getSimpleName();

    public FilterMoviesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d(TAG, "onCreateView");
        return inflater.inflate(R.layout.fragment_filter_movies, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("FILTER", "onStart");
    }
    @Override
    public void onResume() {
        super.onResume();
        Log.d("FILTER", "onResume");
    }


}
