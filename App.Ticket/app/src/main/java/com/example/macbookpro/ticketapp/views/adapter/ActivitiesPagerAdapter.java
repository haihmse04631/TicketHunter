package com.example.macbookpro.ticketapp.views.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.macbookpro.ticketapp.views.base.BindingFragment;
import com.example.macbookpro.ticketapp.views.fragments.favoritescreen.AddedEventFragment;
import com.example.macbookpro.ticketapp.views.fragments.favoritescreen.HistoryEventFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hoang Hai on 3/18/19.
 */
public class ActivitiesPagerAdapter extends FragmentStatePagerAdapter {

    List<BindingFragment> listFragments;

    public ActivitiesPagerAdapter(FragmentManager fm) {
        super(fm);
        this.listFragments = new ArrayList<>();
        listFragments.add(HistoryEventFragment.newInstance());
        listFragments.add(AddedEventFragment.newInstance());
    }

    @Override
    public Fragment getItem(int i) {
        return listFragments.get(i);
    }

    @Override
    public int getCount() {
        return listFragments.size();
    }
}
