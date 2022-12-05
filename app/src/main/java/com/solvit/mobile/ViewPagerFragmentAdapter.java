package com.solvit.mobile;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ViewPagerFragmentAdapter extends FragmentStateAdapter {

    public ViewPagerFragmentAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    // return fragments at every position
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return new PendingFragment();
    }

    // return total number of tabs in our case we have 3
    @Override
    public int getItemCount() {
        return 2;
    }
}
