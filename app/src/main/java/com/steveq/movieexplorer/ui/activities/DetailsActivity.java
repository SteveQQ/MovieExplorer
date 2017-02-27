package com.steveq.movieexplorer.ui.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.steveq.movieexplorer.R;
import com.steveq.movieexplorer.model.Genre;
import com.steveq.movieexplorer.model.Movie;
import com.steveq.movieexplorer.ui.fragments.ImagesGridAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsActivity extends AppCompatActivity {

    @BindView(R.id.posterImageView) ImageView posterImageView;
    @BindView(R.id.titleTextView) TextView titleTextView;
    @BindView(R.id.genreTextView) TextView genreTextView;
    @BindView(R.id.ratingTextView) TextView ratingTextView;
    @BindView(R.id.overviewTextView) TextView overviewTextView;

    private Movie theMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        theMovie = (Movie) intent.getSerializableExtra(ImagesGridAdapter.SELECTED_MOVIE);

        injectPoster();
        injectInfo();
    }

    public void injectPoster(){
        Picasso.with(this)
                .load(ImagesGridAdapter.BASE_IMAGE_URL + theMovie.getPoster_path())
                .placeholder(R.drawable.ic_load_vec)
                .error(R.drawable.ic_error_vec)
                .resize(200, (int)(200*1.5))
                .centerCrop()
                .into(posterImageView);
    }

    public void injectInfo(){
        titleTextView.setText(theMovie.getTitle());
        genreTextView.setText(Genre.of(theMovie.getGenre_ids().get(0)).toString());
        ratingTextView.setText(String.valueOf(theMovie.getVote_average()));
        overviewTextView.setText(theMovie.getOverview());
    }
}
