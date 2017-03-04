package com.steveq.movieexplorer.ui.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.steveq.movieexplorer.R;
import com.steveq.movieexplorer.adapters.ImagesGridAdapter;
import com.steveq.movieexplorer.adapters.PersonGridAdapter;
import com.steveq.movieexplorer.model.Genre;
import com.steveq.movieexplorer.model.Movie;
import com.steveq.movieexplorer.model.Person;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PersonDetailsActivity extends AppCompatActivity {

    @BindView(R.id.profileImageView) ImageView profileImageView;
    @BindView(R.id.nameTextView) TextView nameTextView;
    @BindView(R.id.popularityTextView) TextView popularityTextView;
    @BindView(R.id.knownforGridView) GridView knownforGridView;

    private Person thePerson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_details);

        ButterKnife.bind(this);
        Intent intent = getIntent();
        thePerson = (Person) intent.getSerializableExtra(PersonGridAdapter.SELECTED_PERSON);

        injectPoster();
        injectInfo();
    }

    public void injectPoster(){
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        Log.d("DETAILS", String.valueOf(metrics.widthPixels));
        Picasso.with(this)
                .load(PersonGridAdapter.BASE_IMAGE_URL + thePerson.getProfile_path())
                .placeholder(R.drawable.ic_load_vec)
                .error(R.drawable.ic_error_vec)
                .resize(metrics.widthPixels/2, (int)((metrics.widthPixels/2)*1.5))
                .centerCrop()
                .into(profileImageView);
    }

    public void injectInfo(){
//        if("tv".equals(theMovie.getMedia_type())){
//            titleTextView.setText(theMovie.getName());
//        } else {
//            titleTextView.setText(theMovie.getTitle());
//        }
//
//        if(theMovie.getGenre() >= 0) {
//            if(theMovie.getGenre_ids() != null) {
//                for (int i = 0; i < theMovie.getGenre_ids().size(); i++) {
//                    if (genreExists(theMovie.getGenre_ids().get(i))) {
//                        genreTextView.setText(Genre.of(theMovie.getGenre_ids().get(i)).toString().toLowerCase());
//                        break;
//                    }
//                }
//            } else {
//                genreTextView.setText(Genre.of(theMovie.getGenre()).toString().toLowerCase());
//            }
//        } else {
//            genreTextView.setText(getResources().getString(R.string.unknown_genre));
//        }

        nameTextView.setText(String.valueOf(thePerson.getName()));
        popularityTextView.setText(String.valueOf(thePerson.getPopularity().intValue()));
        knownforGridView.setAdapter(new ImagesGridAdapter(this, thePerson.getKnown_for()));
    }
}
