package com.steveq.movieexplorer.ui.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.steveq.movieexplorer.R;
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

    @BindView(R.id.callButton) Button callButton;
    @BindView(R.id.call2Button) Button call2Button;
    @BindView(R.id.call3Button) Button call3Button;
    @BindView(R.id.keywordsEditText) AutoCompleteTextView keywordsEditText;
    @BindView(R.id.keywordsTextView) TextView keywordsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mTmdbManager = new TmdbManager(this);

        keywordsEditText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "selected: " + position);
                StringBuilder builder = new StringBuilder();
                builder.append(selectedKeywords);
                builder.append(keywordsEditText.getText().toString());
                builder.append(",");
                selectedKeywords = builder.toString();
                keywordsTextView.setText(selectedKeywords);
                keywordsEditText.setText("");
            }
        });
    }

    @OnClick(R.id.callButton) void makeCall(){
        mTmdbManager.getNewestMovies();
    }

    @OnClick(R.id.call2Button) void makeCall2(){
        mTmdbManager.getPopularMovies();
    }

    @OnClick(R.id.call3Button) void makeCall3(){
        String val = "Action";
        mTmdbManager.getFilteredParams(2017, Genre.valueOf(val.toUpperCase()), selectedKeywords.split(","));
    }

    @OnTextChanged(R.id.keywordsEditText) void completion(){
        StringBuilder builder = new StringBuilder();
        builder.append(keywordsEditText.getText().toString());
        if(builder.length() > 1){
            String[] tempStr = builder.toString().split(",");
            mTmdbManager.getAvailableKeywords(tempStr[tempStr.length-1]);
        }
        Log.d(TAG, "completion");
    }

    @Override
    public void updateAutoCompletion() {
        ArrayList<String> keyStrings = new ArrayList<>();
        for(Keyword k : KeywordsCallback.currentKeywordsOutput.results){
            keyStrings.add(k.getName());
        }
        String[] keywords = keyStrings.toArray(new String[]{});
        keywordsEditText.setAdapter(
                new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, keywords)
        );
    }
}
