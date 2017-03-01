package com.steveq.movieexplorer.ui.activities;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;
import com.steveq.movieexplorer.R;
import com.steveq.movieexplorer.adapters.MyPagerAdapter;
import com.steveq.movieexplorer.api.KeywordsCallback;
import com.steveq.movieexplorer.api.TmdbManager;
import com.steveq.movieexplorer.db.DbOperationManager;
import com.steveq.movieexplorer.model.Genre;
import com.steveq.movieexplorer.model.Keyword;
import com.steveq.movieexplorer.model.KeywordsOutput;
import com.steveq.movieexplorer.model.Movie;
import com.steveq.movieexplorer.ui.fragments.FilterMoviesFragment;
import com.steveq.movieexplorer.ui.fragments.FilteredMoviesFragment;
import com.steveq.movieexplorer.ui.fragments.ImagesGridAdapter;
import com.steveq.movieexplorer.ui.fragments.NewestMoviesFragment;
import com.steveq.movieexplorer.ui.fragments.PopularMoviesFragment;
import com.steveq.movieexplorer.ui.fragments.WishlistFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import butterknife.OnTextChanged;

public class MainActivity extends AppCompatActivity{

    private static final String TAG = MainActivity.class.getSimpleName();
    public FragmentStatePagerAdapter pagerAdapter;
    public static ArrayList<Fragment> fragmentsPoll = new ArrayList<>(Arrays.asList(new NewestMoviesFragment(),
            new PopularMoviesFragment(),
            new FilterMoviesFragment(),
            new WishlistFragment()));

    @BindView(R.id.viewPager) public ViewPager viewPager;
    @BindView(R.id.tabStrip) PagerSlidingTabStrip tabStrip;
    @BindView(R.id.toolbar) Toolbar toolbar;
    //@BindView(R.id.searchView) SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        pagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);

        tabStrip.setViewPager(viewPager);
        setSupportActionBar(toolbar);

//
    }

    private List<Fragment> getCurrentVisible(){
        List<Fragment> visibleFrags = new ArrayList<>();
        for (Fragment f : fragmentsPoll) {
            if (f.isVisible()) {
                visibleFrags.add(f);
            }
        }
        return visibleFrags;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem searchItem = menu.findItem(R.id.search);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        final SearchView searchView = (SearchView) searchItem.getActionView();

        //NOT NECESSARY IN THIS CASE BECAUSE ACTIVITY ISN'T SEARCHED
        //searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(MainActivity.this, query, Toast.LENGTH_SHORT).show();
                searchView.setQuery("", false);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        searchView.clearFocus();
        searchView.setFocusable(false);

        return true;
    }

    @Override
    public void onBackPressed() {
        if(fragmentsPoll.get(2) instanceof FilteredMoviesFragment){
            if(getCurrentVisible().get(1) instanceof FilteredMoviesFragment){
                fragmentsPoll.set(2, new FilterMoviesFragment());
                pagerAdapter.notifyDataSetChanged();
                return;
            }
        }
        super.onBackPressed();
    }
}
