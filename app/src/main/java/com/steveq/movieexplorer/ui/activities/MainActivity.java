package com.steveq.movieexplorer.ui.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.steveq.movieexplorer.R;
import com.steveq.movieexplorer.api.TmdbManager;
import com.steveq.movieexplorer.model.Genre;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private TmdbManager mTmdbManager;

    @BindView(R.id.callButton) Button callButton;
    @BindView(R.id.call2Button) Button call2Button;
    @BindView(R.id.call3Button) Button call3Button;
    @BindView(R.id.keywordsEditText) AutoCompleteTextView keywordsEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mTmdbManager = new TmdbManager(this);
    }

    @OnClick(R.id.callButton) void makeCall(){
        mTmdbManager.getNewestMovies();
    }

    @OnClick(R.id.call2Button) void makeCall2(){
        mTmdbManager.getPopularMovies();
    }

    @OnClick(R.id.call3Button) void makeCall3(){
        String val = "Action";
        List<String> list = new ArrayList<>(Arrays.asList("godfather", "animals"));
        mTmdbManager.getFilteredParams(2017, Genre.valueOf(val.toUpperCase()), list);
    }

    @OnTextChanged(R.id.keywordsEditText) void completion(){
        StringBuilder builder = new StringBuilder();
        builder.append(keywordsEditText.getText().toString());
        if(builder.length() > 1){
            mTmdbManager.getAvailableKeywords(builder.toString());
        }
        Log.d(TAG, "completion");
    }
}
