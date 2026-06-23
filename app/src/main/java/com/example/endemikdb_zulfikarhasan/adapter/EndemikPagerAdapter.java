package com.example.endemikdb_zulfikarhasan.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import com.example.endemikdb_zulfikarhasan.ui.home.EndemikFragment;

public class EndemikPagerAdapter extends FragmentStateAdapter {
    public EndemikPagerAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) {
            return EndemikFragment.newInstance("Hewan");
        } else {
            return EndemikFragment.newInstance("Tumbuhan");
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
