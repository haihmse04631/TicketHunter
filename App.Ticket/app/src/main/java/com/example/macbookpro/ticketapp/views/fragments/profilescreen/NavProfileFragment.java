package com.example.macbookpro.ticketapp.views.fragments.profilescreen;


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
public class NavProfileFragment extends NavigationFragment {

    public static NavProfileFragment newInstance() {

        Bundle args = new Bundle();

        NavProfileFragment fragment = new NavProfileFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected BindingFragment onCreateMainFragment() {
        return ProfileFragment.newInstance();
    }

    @Override
    protected int getContentViewId() {
        return R.id.navigation_profile;
    }

    @Override
    protected int layoutRes() {
        return R.layout.fragment_nav_profile;
    }
}
