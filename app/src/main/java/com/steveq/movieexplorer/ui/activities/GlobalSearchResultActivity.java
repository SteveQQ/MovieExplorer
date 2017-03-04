package com.steveq.movieexplorer.ui.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.steveq.movieexplorer.R;
import com.steveq.movieexplorer.adapters.ImagesGridAdapter;
import com.steveq.movieexplorer.api.DispatcherCallback;
import com.steveq.movieexplorer.api.TmdbManager;
import com.steveq.movieexplorer.db.DbOperationManager;
import com.steveq.movieexplorer.model.Movie;
import com.steveq.movieexplorer.model.MoviesRoot;
import com.steveq.movieexplorer.model.PersonRoot;
import com.steveq.movieexplorer.ui.fragments.GlobalSearchFragment;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GlobalSearchResultActivity extends AppCompatActivity implements Callback<ResponseBody> {

    private TmdbManager mTmdbManager;
    private String phrase;
    enum MediaType {MOVIE, PERSON}

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.phraseTextView) TextView phraseTextView;
    @BindView(R.id.progressBar) ProgressBar progressBar;
    @BindView(R.id.globalGridView) GridView globalGridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_global_search_result);
        ButterKnife.bind(this);

        mTmdbManager = new TmdbManager(this);
        phrase = getIntent().getStringExtra(MainActivity.QUERY_STRING);

        phraseTextView.setText(phrase);
        mTmdbManager.getSearchedData(phrase, this);
        progressBar.setVisibility(View.VISIBLE);
        globalGridView.setEmptyView(findViewById(android.R.id.empty));
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem searchItem = menu.findItem(R.id.search);
        final SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
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

    public GlobalSearchResultActivity.MediaType getMediaType(String json){
        Gson gson = new Gson();
        JsonObject root = new JsonParser().parse(json).getAsJsonObject();
        JsonArray results = root.getAsJsonArray("results");
        JsonObject controlObject = results.get(0).getAsJsonObject();
        String mediaType = controlObject.get("media_type").getAsString();
        return GlobalSearchResultActivity.MediaType.valueOf(mediaType.toUpperCase());
    }

    public String getResponseBody(ResponseBody response){
        BufferedReader reader = null;
        StringBuilder builder = new StringBuilder();
        reader = new BufferedReader(response.charStream());
        String line;
        try{
            while((line = reader.readLine()) != null){
                builder.append(line);
            }
        } catch (IOException ioe){
            ioe.printStackTrace();
        }
        return builder.toString();
    }

    @Override
    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
        progressBar.setVisibility(View.GONE);
        String body = getResponseBody(response.body());
        GlobalSearchResultActivity.MediaType mt = getMediaType(body);
        Gson gson = new Gson();
        if(mt == GlobalSearchResultActivity.MediaType.MOVIE){
            MoviesRoot mo = gson.fromJson(body, MoviesRoot.class);
            globalGridView.setAdapter(new ImagesGridAdapter(this,
                    addGenreInfo(mo.getResults())));
        } else if (mt == GlobalSearchResultActivity.MediaType.PERSON){
            PersonRoot po = gson.fromJson(body, PersonRoot.class);
        }

    }

    public static List<Movie> addGenreInfo(List<Movie> movs){
        for(Movie m : movs){
            if(m.getGenre_ids().size() > 0) {
                m.setGenre(m.getGenre_ids().get(0));
            } else {
                m.setGenre(-1);
            }
        }
        return movs;
    }

    @Override
    public void onFailure(Call<ResponseBody> call, Throwable t) {
        progressBar.setVisibility(View.GONE);
    }
}
