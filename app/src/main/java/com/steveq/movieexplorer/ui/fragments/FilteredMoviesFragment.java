package com.steveq.movieexplorer.ui.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import com.steveq.movieexplorer.R;
import com.steveq.movieexplorer.ui.activities.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class FilteredMoviesFragment extends Fragment {

    @BindView(R.id.gridView)
    public GridView gridView;

    public FilteredMoviesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_newest_movies, container, false);

        ButterKnife.bind(this, view);
        gridView.setAdapter(new ImagesGridAdapter(getActivity(),
                ((MainActivity)getActivity()).filteredMovies));
        return view;
    }

}
