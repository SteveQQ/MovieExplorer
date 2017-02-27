package com.steveq.movieexplorer.ui.fragments;


import android.app.ActionBar;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.steveq.movieexplorer.R;
import com.steveq.movieexplorer.ui.activities.MainActivity;

import java.util.List;

import static android.widget.ImageView.ScaleType.CENTER_CROP;

public class ImagesGridAdapter extends BaseAdapter {
    private Context mContext;
    private final List<String> urls;

    public ImagesGridAdapter(Context context, List<String> urls) {
        mContext = context;
        this.urls = urls;
    }

    @Override
    public int getCount() {
        return urls.size();
    }

    @Override
    public Object getItem(int position) {
        return urls.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if(convertView == null){
            imageView = new ImageView(mContext);
            //imageView.setLayoutParams(new GridView.LayoutParams(parent.getWidth()/3, (int)((parent.getWidth()/3)*1.5)));
            //imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        } else {
            imageView = (ImageView)convertView;
        }

        String url = (String) getItem(position);

        Picasso.with(mContext)
                .load(url)
                .placeholder(R.drawable.ic_load_vec)
                .error(R.drawable.ic_error_vec)
                .resize(parent.getWidth()/3, (int)((parent.getWidth()/3)*1.5))
                .centerCrop()
                .into(imageView);

        return imageView;
    }
}
