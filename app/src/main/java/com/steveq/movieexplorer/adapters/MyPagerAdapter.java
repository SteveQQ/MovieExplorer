package com.steveq.movieexplorer.adapters;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.steveq.movieexplorer.ui.fragments.FilterMoviesFragment;
import com.steveq.movieexplorer.ui.fragments.NewestMoviesFragment;
import com.steveq.movieexplorer.ui.fragments.PopularMoviesFragment;
import com.steveq.movieexplorer.ui.fragments.WishlistFragment;

import java.util.ArrayList;
import java.util.Arrays;

public class MyPagerAdapter extends FragmentPagerAdapter{
    private static int NUM_FRAGMENTS = 4;
    private ArrayList<Fragment> fragmentsPoll;

    public MyPagerAdapter(FragmentManager fm) {
        super(fm);
        fragmentsPoll = new ArrayList<>(Arrays.asList(new NewestMoviesFragment(),
                                                        new PopularMoviesFragment(),
                                                        new FilterMoviesFragment(),
                                                        new WishlistFragment()));
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentsPoll.get(position);
    }

    @Override
    public int getCount() {
        return NUM_FRAGMENTS;
    }
}
