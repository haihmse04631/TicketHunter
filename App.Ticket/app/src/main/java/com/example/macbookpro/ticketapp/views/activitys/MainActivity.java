package com.example.macbookpro.ticketapp.views.activitys;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.macbookpro.ticketapp.R;
import com.example.macbookpro.ticketapp.databinding.ActivityMainBinding;
import com.example.macbookpro.ticketapp.viewmodels.activitys.MainActivityVM;
import com.example.macbookpro.ticketapp.views.adapter.NavigationPagerAdapter;
import com.example.macbookpro.ticketapp.views.base.BindingActivity;
import com.example.macbookpro.ticketapp.views.base.BindingFragment;
import com.example.macbookpro.ticketapp.views.base.NavigationFragment;
import com.example.macbookpro.ticketapp.views.customviews.CustomViewPager;

public class MainActivity extends BindingActivity implements ViewPager.OnPageChangeListener, MainActivityVM.OnSelectedTabCallBack {

    private NavigationPagerAdapter adapter;
    private CustomViewPager viewPager;
    private int lastPosition;
    private MainActivityVM viewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding activityMainBinding = (ActivityMainBinding) getViewBinding();
        viewPager = activityMainBinding.viewPager;
        setupView();
        viewModel = new MainActivityVM();
        activityMainBinding.setViewModel(viewModel);
        activityMainBinding.setListened(this);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_main;
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
        viewModel.setSelectedTab(i);
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    @Override
    public void onTabSelected(View view) {
        switch (view.getId()) {
            default:
                return;
            case R.id.tab_home:
                setCurrentTab(MainActivityVM.TAB_HOME);
                return;
            case R.id.tab_favorite:
                 setCurrentTab(MainActivityVM.TAB_FAVORITE);
                return;
            case R.id.tab_profile:
                setCurrentTab(MainActivityVM.TAB_PROFILE);
                return;
        }
    }

    public void setupView() {
        adapter = new NavigationPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(3);
        viewPager.addOnPageChangeListener(this);
    }

    public void setCurrentTab(int tab) {
        if (tab != lastPosition) {
            lastPosition = tab;
            viewPager.setCurrentItem(lastPosition);
        } else {
            getCurrentFragment().goBackToBaseFragment();
        }
    }

    public BindingFragment getCurrentFragment() {
        return findSubpageFragment();
    }

    private BindingFragment findSubpageFragment() {
        if (adapter == null || viewPager == null) {
            return null;
        }
        NavigationFragment currentNaviFragment = adapter.getMainPageFragment(viewPager);
        if (currentNaviFragment != null) {
            return currentNaviFragment.getSubpageFragment();
        }
        return null;
    }
}
