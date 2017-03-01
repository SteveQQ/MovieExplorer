package com.steveq.movieexplorer.ui.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;
import android.widget.TextView;

import com.steveq.movieexplorer.R;
import com.steveq.movieexplorer.adapters.MyPagerAdapter;
import com.steveq.movieexplorer.api.KeywordsCallback;
import com.steveq.movieexplorer.api.TmdbManager;
import com.steveq.movieexplorer.model.Genre;
import com.steveq.movieexplorer.model.Keyword;
import com.steveq.movieexplorer.model.MoviesOutput;
import com.steveq.movieexplorer.ui.activities.MainActivity;
import com.steveq.movieexplorer.ui.activities.MainActivityView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class FilterMoviesFragment extends Fragment implements Callback<MoviesOutput>{


    private static final String TAG = FilterMoviesFragment.class.getSimpleName();
    private TmdbManager mTmdbManager;
    private String selectedKeywords = "";

    @BindView(R.id.yearSpinner) Spinner yearSpinner;
    @BindView(R.id.genreSpinner) Spinner genreSpinner;
    @BindView(R.id.keywordsAutoComplete) AutoCompleteTextView keywordsAutoComplete;
    @BindView(R.id.keywordsTextView) TextView keywordsTextView;
    @BindView(R.id.filterFab) FloatingActionButton filterFab;

    public FilterMoviesFragment() {
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
        Log.d(TAG, "onCreateView");
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_filter_movies, container, false);
        ButterKnife.bind(this, viewGroup);
        populateSpinners();

        keywordsAutoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "selected: " + position);
                StringBuilder builder = new StringBuilder();
                builder.append(selectedKeywords);
                builder.append(keywordsAutoComplete.getText().toString());
                builder.append(",");
                selectedKeywords = builder.toString();
                keywordsTextView.setText(selectedKeywords);
                keywordsAutoComplete.setText("");
            }
        });

        return viewGroup;
    }

    @OnClick(R.id.filterFab)
    public void proceedFilter(View v){
        mTmdbManager.getFilteredParams(
                Integer.valueOf(yearSpinner.getSelectedItem().toString()),
                Genre.valueOf(genreSpinner.getSelectedItem().toString()),
                selectedKeywords.split(","),
                this
        );

    }

    @OnTextChanged(R.id.keywordsAutoComplete) void completion(){
        StringBuilder builder = new StringBuilder();
        builder.append(keywordsAutoComplete.getText().toString());
        if(builder.length() > 1){
            String[] tempStr = builder.toString().split(",");
            mTmdbManager.getAvailableKeywords(tempStr[tempStr.length-1]);
        }
        Log.d(TAG, "completion");
    }


    @Override
    public void onResponse(Call<MoviesOutput> call, Response<MoviesOutput> response) {
        Log.d(TAG, "Request Completed!");

        ((MainActivity)getActivity()).filteredMovies = response.body().getResults();

        MainActivity.fragmentsPoll.set(2, new FilteredMoviesFragment());
        ((MainActivity)getActivity()).pagerAdapter.notifyDataSetChanged();
    }

    @Override
    public void onFailure(Call<MoviesOutput> call, Throwable t) {
        Log.d(TAG, "Request Failed!");
        Log.d(TAG, call.request().url().toString());
    }

    public void updateAutoCompletion() {
        ArrayList<String> keyStrings = new ArrayList<>();
        for(Keyword k : KeywordsCallback.currentKeywordsOutput.results){
            keyStrings.add(k.getName());
        }
        String[] keywords = keyStrings.toArray(new String[]{});
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.support_simple_spinner_dropdown_item, keywords);
        keywordsAutoComplete.setAdapter(adapter);
        keywordsAutoComplete.showDropDown();
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
