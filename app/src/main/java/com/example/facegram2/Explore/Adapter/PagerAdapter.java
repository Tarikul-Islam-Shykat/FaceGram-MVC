package com.example.facegram2.Explore.Adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.facegram2.Explore.Fragment.entertainment_news_fragment;
import com.example.facegram2.Explore.Fragment.sports_news_fragment;


public class PagerAdapter extends FragmentPagerAdapter {
    Context context_;
    int tab_count ; // number of tabs

    public PagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        tab_count =  behavior;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position ==0 ){
            sports_news_fragment sports = new sports_news_fragment();
            return sports;
        }
        if (position ==1 ){
            entertainment_news_fragment entertainment = new entertainment_news_fragment();
            return entertainment;
        }
        return null;
    }

    @Override
    public int getCount() {
        return tab_count;
    }
}
