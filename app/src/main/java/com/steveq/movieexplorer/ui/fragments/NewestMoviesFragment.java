package com.steveq.movieexplorer.ui.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewestMoviesFragment extends BaseFragment{
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View view =  super.onCreateView(inflater, container, savedInstanceState);
//
//        return view;
//    }

    @Override
    void execution() {
        newestMovies();
    }
}
