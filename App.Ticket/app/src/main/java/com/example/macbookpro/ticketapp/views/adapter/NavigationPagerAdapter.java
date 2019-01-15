package com.example.macbookpro.ticketapp.views.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.example.macbookpro.ticketapp.views.base.NavigationFragment;
import com.example.macbookpro.ticketapp.views.fragments.favoritescreen.FavoriteFragment;
import com.example.macbookpro.ticketapp.views.fragments.favoritescreen.NavFavoriteFragment;
import com.example.macbookpro.ticketapp.views.fragments.homescreen.NavHomeFragment;
import com.example.macbookpro.ticketapp.views.fragments.profilescreen.NavProfileFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hoang Hai on 1/13/19.
 */
public class NavigationPagerAdapter extends FragmentStatePagerAdapter {
    List<NavigationFragment> listFragments;

    public NavigationPagerAdapter(FragmentManager fm) {
        super(fm);
        this.listFragments = new ArrayList<>();
        listFragments.add(NavHomeFragment.newInstance());
        listFragments.add(NavFavoriteFragment.newInstance());
        listFragments.add(NavProfileFragment.newInstance());
    }

    @Override
    public Fragment getItem(int i) {
        return listFragments.get(i);
    }

    @Override
    public int getCount() {
        return listFragments.size();
    }

    public NavigationFragment getMainPageFragment(ViewPager container) {
        int position = container.getCurrentItem();
        Object instantItem = instantiateItem(container, position);
        if (instantItem instanceof NavigationFragment) {
            return (NavigationFragment) instantItem;
        }
        return null;
    }
}
