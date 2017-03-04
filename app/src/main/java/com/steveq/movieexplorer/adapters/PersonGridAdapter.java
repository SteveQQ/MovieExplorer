package com.steveq.movieexplorer.adapters;


import android.app.Activity;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.steveq.movieexplorer.R;
import com.steveq.movieexplorer.model.Person;
import com.steveq.movieexplorer.ui.activities.DetailsActivity;
import com.steveq.movieexplorer.ui.activities.PersonDetailsActivity;

import java.util.List;

public class PersonGridAdapter extends BaseAdapter {
    public static final String SELECTED_PERSON = "SELECTED PERSON";
    public static final String BASE_IMAGE_URL = "https://image.tmdb.org/t/p/w300";
    private Activity mContext;
    private List<Person> persons;

    public PersonGridAdapter(Activity context, List<Person> urls) {
        mContext = context;
        this.persons = urls;
    }

    public void setMovies(List<Person> movies) {
        this.persons = persons;
    }

    @Override
    public int getCount() {
        return persons.size();
    }

    @Override
    public Object getItem(int position) {
        return BASE_IMAGE_URL + persons.get(position).getProfile_path();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        DisplayMetrics metrics = new DisplayMetrics();
        mContext.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        if(convertView == null){
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(metrics.widthPixels/3, (int)((metrics.widthPixels/3)*1.5)));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        } else {
            imageView = (ImageView)convertView;
        }

        String url = (String) getItem(position);

        Picasso.with(mContext)
                .load(url)
                .placeholder(R.drawable.ic_load_vec)
                .error(R.drawable.ic_error_vec)
                .resize(metrics.widthPixels/3, (int)((metrics.widthPixels/3)*1.5))
                .centerCrop()
                .into(imageView);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, PersonDetailsActivity.class);
                intent.putExtra(SELECTED_PERSON, persons.get(position));
                mContext.startActivity(intent);
            }
        });

        return imageView;
    }
}
