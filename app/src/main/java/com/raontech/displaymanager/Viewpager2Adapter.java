package com.raontech.displaymanager;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class Viewpager2Adapter extends FragmentStateAdapter {
    public Viewpager2Adapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new DisplayFragment();

            case 1:
                return new CameraFragment();

            case 2:
                return new SensorFragment();

            default:
                return new DisplayFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
