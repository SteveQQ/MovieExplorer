package com.steveq.movieexplorer.adapters;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.astuetz.PagerSlidingTabStrip;
import com.steveq.movieexplorer.R;
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

    public static ArrayList<Fragment> fragmentsPoll = new ArrayList<Fragment>(Arrays.asList(new NewestMoviesFragment(),
            new PopularMoviesFragment(),
            new FilterMoviesFragment(),
            new WishlistFragment()));

    public MyPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentsPoll.get(position);
    }

    @Override
    public int getCount() {
        return NUM_FRAGMENTS;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE; //this value force adapter to reload fragments
                              //otherwise adapter assumes that every fragments is at its right place
    }

    @Override
    public int getPageIconResId(int position) {
        return icons[position];
    }
}
