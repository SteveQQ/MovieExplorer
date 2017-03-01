package com.steveq.movieexplorer.ui.fragments;


import android.os.Bundle;
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
import com.steveq.movieexplorer.model.Genre;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class FilterMoviesFragment extends Fragment {


    private static final String TAG = FilterMoviesFragment.class.getSimpleName();

    @BindView(R.id.yearSpinner) Spinner yearSpinner;
    @BindView(R.id.genreSpinner) Spinner genreSpinner;
    @BindView(R.id.keywordsAutoComplete) AutoCompleteTextView keywordsAutoComplete;
    @BindView(R.id.keywordsTextView) TextView keywordsTextView;

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
