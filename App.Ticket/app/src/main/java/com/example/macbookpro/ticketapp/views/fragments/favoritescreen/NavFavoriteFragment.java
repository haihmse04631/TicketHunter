package com.example.macbookpro.ticketapp.views.fragments.favoritescreen;


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
public class NavFavoriteFragment extends NavigationFragment {

    public static NavFavoriteFragment newInstance() {

        Bundle args = new Bundle();

        NavFavoriteFragment fragment = new NavFavoriteFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected BindingFragment onCreateMainFragment() {
        return FavoriteFragment.newInstance();
    }

    @Override
    protected int getContentViewId() {
        return R.id.navigation_favorite;
    }

    @Override
    protected int layoutRes() {
        return R.layout.fragment_nav_favorite;
    }
}
