package com.hrcosta.simpleworkoutlogger;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;

public class RoutinesViewPagerAdapter extends FragmentPagerAdapter {


    private final List<RoutinesFragment> fragmentList = new ArrayList<>();
    private final List<String> titleList = new ArrayList<>();

    public RoutinesViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public RoutinesFragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return titleList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titleList.get(position);
    }

    @Override
    public int getItemPosition(Object object){
        return PagerAdapter.POSITION_NONE;
    }

    public void AddFragment(RoutinesFragment fragment, String title) {
        fragmentList.add(fragment);
        titleList.add(title);
    }

    public void removeFragment(RoutinesFragment fragment, int position) {
        fragmentList.remove(position);
        titleList.remove(position);
    }


}
