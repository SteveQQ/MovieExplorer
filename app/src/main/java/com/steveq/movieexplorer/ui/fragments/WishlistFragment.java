package com.steveq.movieexplorer.ui.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.steveq.movieexplorer.adapters.ImagesGridAdapter;
import com.steveq.movieexplorer.db.DbOperationManager;
import com.steveq.movieexplorer.model.Movie;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class WishlistFragment extends BaseFragment {

    public ImagesGridAdapter mAdapter;

    public WishlistFragment() {
        // Required empty public constructor
    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        View view = super.onCreateView(inflater, container, savedInstanceState);
//        List<Movie> wishes = DbOperationManager.getInstance(getContext()).getWishes();
//        mAdapter = new ImagesGridAdapter(getActivity(), wishes);
//        gridView.setAdapter(mAdapter);
//        return view;
//    }

    @Override
    void execution() {
        List<Movie> wishes = DbOperationManager.getInstance(getContext()).getWishes();
        mAdapter = new ImagesGridAdapter(getActivity(), wishes);
        gridView.setAdapter(mAdapter);
        swipeRefresh.setRefreshing(false);
    }

    @Override
    public void onResume() {
        super.onResume();
        List<Movie> movs = DbOperationManager.getInstance(getContext()).getWishes();
        gridView.setAdapter(new ImagesGridAdapter(getActivity(), movs));
    }
}
