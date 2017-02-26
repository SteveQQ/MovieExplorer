package com.steveq.movieexplorer.ui.activities;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentPagerAdapter;
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
import com.steveq.movieexplorer.model.Genre;
import com.steveq.movieexplorer.model.Keyword;
import com.steveq.movieexplorer.model.KeywordsOutput;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import butterknife.OnTextChanged;

public class MainActivity extends AppCompatActivity implements MainActivityView{

    private static final String TAG = MainActivity.class.getSimpleName();
    private TmdbManager mTmdbManager;
    private String selectedKeywords = "";
    private FragmentPagerAdapter pagerAdapter;

    @BindView(R.id.viewPager) ViewPager viewPager;
    @BindView(R.id.tabStrip) PagerSlidingTabStrip tabStrip;
    @BindView(R.id.toolbar) Toolbar toolbar;
    //@BindView(R.id.searchView) SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
//        mTmdbManager = new TmdbManager(this);

        pagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);

        tabStrip.setViewPager(viewPager);
        setSupportActionBar(toolbar);

//        keywordsEditText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Log.d(TAG, "selected: " + position);
//                StringBuilder builder = new StringBuilder();
//                builder.append(selectedKeywords);
//                builder.append(keywordsEditText.getText().toString());
//                builder.append(",");
//                selectedKeywords = builder.toString();
//                keywordsTextView.setText(selectedKeywords);
//                keywordsEditText.setText("");
//            }
//        });
    }

//    @OnTextChanged(R.id.keywordsEditText) void completion(){
//        StringBuilder builder = new StringBuilder();
//        builder.append(keywordsEditText.getText().toString());
//        if(builder.length() > 1){
//            String[] tempStr = builder.toString().split(",");
//            mTmdbManager.getAvailableKeywords(tempStr[tempStr.length-1]);
//        }
//        Log.d(TAG, "completion");
//    }


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

        return true;
    }

    @Override
    public void updateAutoCompletion() {
        ArrayList<String> keyStrings = new ArrayList<>();
        for(Keyword k : KeywordsCallback.currentKeywordsOutput.results){
            keyStrings.add(k.getName());
        }
        String[] keywords = keyStrings.toArray(new String[]{});
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, keywords);
//        keywordsEditText.setAdapter(adapter);
//        keywordsEditText.showDropDown();
    }
}
