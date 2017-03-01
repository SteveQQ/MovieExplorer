package com.steveq.movieexplorer.ui.fragments;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;
import android.widget.TextView;

import com.steveq.movieexplorer.R;
import com.steveq.movieexplorer.adapters.MyPagerAdapter;
import com.steveq.movieexplorer.model.Genre;
import com.steveq.movieexplorer.ui.activities.MainActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class FilterMoviesFragment extends Fragment {


    private static final String TAG = FilterMoviesFragment.class.getSimpleName();

    @BindView(R.id.yearSpinner) Spinner yearSpinner;
    @BindView(R.id.genreSpinner) Spinner genreSpinner;
    @BindView(R.id.keywordsAutoComplete) AutoCompleteTextView keywordsAutoComplete;
    @BindView(R.id.keywordsTextView) TextView keywordsTextView;
    @BindView(R.id.filterFab) FloatingActionButton filterFab;

    public FilterMoviesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d(TAG, "onCreateView");
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_filter_movies, container, false);
        ButterKnife.bind(this, viewGroup);
        populateSpinners();
        return viewGroup;
    }

    @OnClick(R.id.filterFab)
    public void proceedFilter(View v){
        MainActivity.fragmentsPoll.set(2, new FilteredMoviesFragment());
        ((MainActivity)getActivity()).pagerAdapter.notifyDataSetChanged();
    }

    private void populateSpinners() {
        List<String> years = generateYears();
        yearSpinner.setAdapter(new ArrayAdapter<String>(getContext(), R.layout.support_simple_spinner_dropdown_item, years));

        List<String> genres = generateGenres();
        genreSpinner.setAdapter(new ArrayAdapter<String>(getContext(), R.layout.support_simple_spinner_dropdown_item, genres));
    }

    private List<String> generateGenres() {
        Genre[] gens = Genre.values();
        List<String> results = new ArrayList<>();
        Log.d(TAG, String.valueOf(gens[0]));
        for(Genre g : gens){
            results.add(g.name());
        }
        return results;
    }

    private List<String> generateYears() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        List<String> results = new ArrayList<>();

        while (year >= 1900) {
            results.add(String.valueOf(year));
            year--;
        }
        return results;
    }


}
