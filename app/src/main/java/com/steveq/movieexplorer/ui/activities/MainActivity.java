package com.steveq.movieexplorer.ui.activities;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;
import com.squareup.picasso.Picasso;
import com.steveq.movieexplorer.R;
import com.steveq.movieexplorer.adapters.MyPagerAdapter;
import com.steveq.movieexplorer.model.Movie;
import com.steveq.movieexplorer.model.Result;
import com.steveq.movieexplorer.ui.fragments.FilterMoviesFragment;
import com.steveq.movieexplorer.ui.fragments.FilteredMoviesFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity{

    private static final String TAG = MainActivity.class.getSimpleName();
    public static final String QUERY_STRING = "QUERY_STRING";
    public FragmentStatePagerAdapter pagerAdapter;

    public List<Movie> filteredMovies;

    @BindView(R.id.viewPager) public ViewPager viewPager;
    @BindView(R.id.tabStrip) PagerSlidingTabStrip tabStrip;
    @BindView(R.id.toolbar) Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        pagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);

        tabStrip.setViewPager(viewPager);
        setSupportActionBar(toolbar);
    }

    private List<Fragment> getCurrentVisible(){
        List<Fragment> visibleFrags = new ArrayList<>();
        for (Fragment f : MyPagerAdapter.fragmentsPoll) {
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
        //SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        final SearchView searchView = (SearchView) searchItem.getActionView();

        //NOT NECESSARY IN THIS CASE BECAUSE ACTIVITY ISN'T SEARCHED
        //searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent intent = new Intent(MainActivity.this, GlobalSearchResultActivity.class);
                intent.putExtra(QUERY_STRING, query);
                MainActivity.this.startActivity(intent);
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

        MenuItem aboutItem = menu.findItem(R.id.about);
        aboutItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(intent);
                return true;
            }
        });

        return true;
    }

    @Override
    public void onBackPressed() {
        if(MyPagerAdapter.fragmentsPoll.get(2) instanceof FilteredMoviesFragment){
            if(getCurrentVisible().get(1) instanceof FilteredMoviesFragment){
                MyPagerAdapter.fragmentsPoll.set(2, new FilterMoviesFragment());
                pagerAdapter.notifyDataSetChanged();
                return;
            }
        }
        super.onBackPressed();
    }
}
