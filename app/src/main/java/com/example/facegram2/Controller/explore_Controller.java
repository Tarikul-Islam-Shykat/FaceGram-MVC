package com.example.facegram2.Controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;

import com.example.facegram2.Explore.Adapter.PagerAdapter;
import com.example.facegram2.R;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

public class explore_Controller extends AppCompatActivity {

    TabLayout tabLayout;
    TabItem mEntertainment, mSports;
    Toolbar mToolbar;
    PagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore_controller);
        getSupportActionBar().hide();

        mToolbar = findViewById(R.id.am_toolbar);
        mEntertainment = findViewById(R.id.am_tabItem_chill);
        mSports = findViewById(R.id.am_tabItem_sports);
        tabLayout = findViewById(R.id.am_tabLayout);
        ViewPager viewPager = findViewById(R.id.fragment_container);

        // set the pagerAdapter
        pagerAdapter = new PagerAdapter(getSupportFragmentManager(),2); // 6 is the number of the tabItem you have
        viewPager.setAdapter(pagerAdapter);

        // getting the individual tabs here
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition()); // every tab has a position
                if(tab.getPosition()>=0 && tab.getPosition()<=5)
                {
                    pagerAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        // for enabling the swipe the tabs option in between
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), DashBoard_Controller.class));
        finish();
    }
}