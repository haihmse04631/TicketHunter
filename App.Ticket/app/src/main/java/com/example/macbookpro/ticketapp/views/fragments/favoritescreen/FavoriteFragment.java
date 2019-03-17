package com.example.macbookpro.ticketapp.views.fragments.favoritescreen;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.example.macbookpro.ticketapp.R;
import com.example.macbookpro.ticketapp.databinding.FragmentFavoriteBinding;
import com.example.macbookpro.ticketapp.viewmodels.fragments.FavoriteFragmentVM;
import com.example.macbookpro.ticketapp.views.adapter.ActivitiesPagerAdapter;
import com.example.macbookpro.ticketapp.views.base.BindingFragment;
import com.example.macbookpro.ticketapp.views.customviews.CustomViewPager;
import com.example.macbookpro.ticketapp.views.fragments.DetailEventFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends BindingFragment implements FavoriteFragmentVM.FavoriteFragmentActionCallBack, ViewPager.OnPageChangeListener {

    private FragmentFavoriteBinding binding;
    private CustomViewPager viewPager;
    private ActivitiesPagerAdapter adapter;
    private FavoriteFragmentVM viewModel;
    private int lastPosition;

    public static FavoriteFragment newInstance() {

        Bundle args = new Bundle();

        FavoriteFragment fragment = new FavoriteFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = (FragmentFavoriteBinding) getViewBinding();
        viewModel = new FavoriteFragmentVM();
        binding.setViewModel(viewModel);
        binding.setListened(this);
        viewPager = binding.viewPager;
        setupView();
    }

    private void setupView() {
        adapter = new ActivitiesPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(2);
        viewPager.addOnPageChangeListener(this);
    }

    public void setCurrentTab(int tab) {
        if (tab != lastPosition) {
            lastPosition = tab;
            viewPager.setCurrentItem(lastPosition);
        }
    }

    @Override
    protected int layoutRes() {
        return R.layout.fragment_favorite;
    }

    @Override
    protected int containerViewId() {
        return R.id.navigation_favorite;
    }

    @Override
    public void onClickDetailTicket(View view) {
        showNext(DetailEventFragment.newInstance());
    }

    @Override
    public void onHistoryEventTapped(View view) {
        setCurrentTab(0);
        viewModel.setAddedEventTapped(false);
    }

    @Override
    public void onAddedEventTapped(View view) {
        setCurrentTab(1);
        viewModel.setAddedEventTapped(true);
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {

    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }
}
