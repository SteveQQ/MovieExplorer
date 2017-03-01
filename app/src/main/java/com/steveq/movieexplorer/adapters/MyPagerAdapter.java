package com.steveq.movieexplorer.adapters;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.astuetz.PagerSlidingTabStrip;
import com.steveq.movieexplorer.R;
import com.steveq.movieexplorer.ui.activities.MainActivity;
import com.steveq.movieexplorer.ui.fragments.FilterMoviesFragment;
import com.steveq.movieexplorer.ui.fragments.NewestMoviesFragment;
import com.steveq.movieexplorer.ui.fragments.PopularMoviesFragment;
import com.steveq.movieexplorer.ui.fragments.WishlistFragment;

import java.util.ArrayList;
import java.util.Arrays;

public class MyPagerAdapter extends FragmentStatePagerAdapter implements PagerSlidingTabStrip.IconTabProvider{
    private static int NUM_FRAGMENTS = 4;

    private int icons[] = {R.drawable.ic_leaf_vec, R.drawable.ic_star_vec,
                            R.drawable.ic_filter_vec, R.drawable.ic_bookmark_strip_vec};

    public MyPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return MainActivity.fragmentsPoll.get(position);
    }

    @Override
    public int getCount() {
        return NUM_FRAGMENTS;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public int getPageIconResId(int position) {
        return icons[position];
    }
}
