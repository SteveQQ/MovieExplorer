package com.steveq.movieexplorer.ui.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.steveq.movieexplorer.R;
import com.steveq.movieexplorer.api.TmdbManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private TmdbManager mTmdbManager;
    @BindView(R.id.callButton) Button callButton;
    @BindView(R.id.call2Button) Button call2Button;

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
}
