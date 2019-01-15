package com.example.macbookpro.ticketapp.views.fragments.homescreen;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.macbookpro.ticketapp.R;
import com.example.macbookpro.ticketapp.views.base.BindingFragment;
import com.example.macbookpro.ticketapp.views.base.NavigationFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class NavHomeFragment extends NavigationFragment {

    public static NavHomeFragment newInstance() {

        Bundle args = new Bundle();

        NavHomeFragment fragment = new NavHomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected BindingFragment onCreateMainFragment() {
        return HomeFragment.newInstance();
    }

    @Override
    protected int getContentViewId() {
        return R.id.navigation_home;
    }

    @Override
    protected int layoutRes() {
        return R.layout.fragment_nav_home;
    }
}
