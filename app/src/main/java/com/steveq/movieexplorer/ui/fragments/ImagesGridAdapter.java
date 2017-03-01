package com.steveq.movieexplorer.ui.fragments;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.steveq.movieexplorer.R;
import com.steveq.movieexplorer.model.Movie;
import com.steveq.movieexplorer.ui.activities.DetailsActivity;

import java.util.List;

public class ImagesGridAdapter extends BaseAdapter {
    public static final String SELECTED_MOVIE = "SELECTED MOVIE";
    public static final String BASE_IMAGE_URL = "https://image.tmdb.org/t/p/w300";
    private Activity mContext;
    private List<Movie> movies;

    public ImagesGridAdapter(Activity context, List<Movie> urls) {
        mContext = context;
        this.movies = urls;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

    @Override
    public int getCount() {
        return movies.size();
    }

    @Override
    public Object getItem(int position) {
        return BASE_IMAGE_URL + movies.get(position).getPoster_path();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if(convertView == null){
            imageView = new ImageView(mContext);
            //imageView.setLayoutParams(new GridView.LayoutParams(parent.getWidth()/3, (int)((parent.getWidth()/3)*1.5)));
            //imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        } else {
            imageView = (ImageView)convertView;
        }

        String url = (String) getItem(position);

        DisplayMetrics metrics = new DisplayMetrics();
        mContext.getWindowManager().getDefaultDisplay().getMetrics(metrics);

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
                Intent intent = new Intent(mContext, DetailsActivity.class);
                intent.putExtra(SELECTED_MOVIE, movies.get(position));
                mContext.startActivity(intent);
            }
        });

        return imageView;
    }
}
