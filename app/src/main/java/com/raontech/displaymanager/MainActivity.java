package com.raontech.displaymanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private ViewPager2 mViewPager2;
    private BottomNavigationView mBottomNavigationView;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mViewPager2 = findViewById(R.id.view_pager_2);
        mBottomNavigationView = findViewById(R.id.bottom_navigation);

        Viewpager2Adapter adapter = new Viewpager2Adapter(this);
        mViewPager2.setAdapter(adapter);
        mViewPager2.setPageTransformer(new DepthPageTransformer());
        mViewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position) {
                    case 0:
                        mBottomNavigationView.getMenu().findItem(R.id.nav_display).setChecked(true);
                        break;
                    case 1:
                        mBottomNavigationView.getMenu().findItem(R.id.nav_camera).setChecked(true);
                        break;
                    case 2:
                        mBottomNavigationView.getMenu().findItem(R.id.nav_censor).setChecked(true);
                        break;
                }
            }
        });

        mBottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.nav_display:
                    mViewPager2.setCurrentItem(0);
                    break;
                case R.id.nav_camera:
                    mViewPager2.setCurrentItem(1);
                    break;
                case R.id.nav_censor:
                    mViewPager2.setCurrentItem(2);
                    break;
            }
            return true;
        });


    }
}