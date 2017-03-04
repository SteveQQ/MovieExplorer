package com.steveq.movieexplorer.ui.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.steveq.movieexplorer.R;
import com.steveq.movieexplorer.db.DbOperationManager;
import com.steveq.movieexplorer.model.Genre;
import com.steveq.movieexplorer.model.Movie;
import com.steveq.movieexplorer.adapters.ImagesGridAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailsActivity extends AppCompatActivity {

    @BindView(R.id.posterImageView) ImageView posterImageView;
    @BindView(R.id.titleTextView) TextView titleTextView;
    @BindView(R.id.genreTextView) TextView genreTextView;
    @BindView(R.id.ratingTextView) TextView ratingTextView;
    @BindView(R.id.overviewTextView) TextView overviewTextView;
    @BindView(R.id.wishImageButton) ImageButton wishImageButton;
    @BindView(R.id.wishNotImageButton) ImageButton wishNotImageButton;

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
        initWishToogle();
    }

    private void initWishToogle() {
        if(DbOperationManager.getInstance(this).isWished(theMovie.getId())){
            wishImageButton.setVisibility(View.VISIBLE);
            wishNotImageButton.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.wishNotImageButton)
    public void wishNotToggle(View v){
        wishNotImageButton.setVisibility(View.GONE);
        wishImageButton.setVisibility(View.VISIBLE);
        DbOperationManager.getInstance(this).addWish(theMovie);
    }

    @OnClick(R.id.wishImageButton)
    public void wishToggle(View v){
        wishImageButton.setVisibility(View.GONE);
        wishNotImageButton.setVisibility(View.VISIBLE);
        DbOperationManager.getInstance(this).removeWish(theMovie.getId());
    }

    public void injectPoster(){
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        Log.d("DETAILS", String.valueOf(metrics.widthPixels));
        Picasso.with(this)
                .load(ImagesGridAdapter.BASE_IMAGE_URL + theMovie.getPoster_path())
                .placeholder(R.drawable.ic_load_vec)
                .error(R.drawable.ic_error_vec)
                .resize(metrics.widthPixels/2, (int)((metrics.widthPixels/2)*1.5))
                .centerCrop()
                .into(posterImageView);
    }

    public void injectInfo(){
        if("tv".equals(theMovie.getMedia_type())){
            titleTextView.setText(theMovie.getName());
        } else {
            titleTextView.setText(theMovie.getTitle());
        }

        if(theMovie.getGenre() >= 0) {
            for(int i=0; i < theMovie.getGenre_ids().size(); i++){
                if(genreExists(theMovie.getGenre_ids().get(i))){
                    genreTextView.setText(Genre.of(theMovie.getGenre_ids().get(i)).toString().toLowerCase());
                    break;
                }
            }
        } else {
            genreTextView.setText(getResources().getString(R.string.unknown_genre));
        }
        ratingTextView.setText(String.valueOf(theMovie.getVote_average()));
        overviewTextView.setText(theMovie.getOverview());
    }

    private boolean genreExists(Integer num) {
        Genre[] genres = Genre.values();
        for(Genre g : genres){
            if(num == g.getId()){
                return true;
            }
        }
        return false;
    }
}
