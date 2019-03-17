package com.example.macbookpro.ticketapp.views.activitys;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.example.macbookpro.ticketapp.R;
import com.example.macbookpro.ticketapp.databinding.ActivityMainBinding;
import com.example.macbookpro.ticketapp.databinding.NavHeaderMainBinding;
import com.example.macbookpro.ticketapp.helper.ultility.Ultil;
import com.example.macbookpro.ticketapp.viewmodels.activitys.MainActivityVM;
import com.example.macbookpro.ticketapp.views.adapter.NavigationPagerAdapter;
import com.example.macbookpro.ticketapp.views.base.BindingActivity;
import com.example.macbookpro.ticketapp.views.base.BindingFragment;
import com.example.macbookpro.ticketapp.views.base.NavigationFragment;
import com.example.macbookpro.ticketapp.views.customviews.CustomViewPager;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class MainActivity extends BindingActivity<ActivityMainBinding>
        implements ViewPager.OnPageChangeListener, MainActivityVM.OnSelectedTabCallBack,
        NavigationView.OnNavigationItemSelectedListener {

    private NavigationPagerAdapter adapter;
    private CustomViewPager viewPager;
    private int lastPosition;
    private MainActivityVM viewModel;
    private DrawerLayout mDrawer;
    private ActivityMainBinding activityMainBinding;
    private NavHeaderMainBinding navHeaderMainBinding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = getViewBinding();
        viewPager = activityMainBinding.viewPager;
        setupView();
        viewModel = new MainActivityVM(this);
        activityMainBinding.setViewModel(viewModel);
        activityMainBinding.setListened(this);

        addNavHeaderView();
        mDrawer = activityMainBinding.drawerLayout;

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawer, 0, 0);
        mDrawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = activityMainBinding.navView;
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        viewModel.refreshUseInforData();
        navHeaderMainBinding.setUser(viewModel.user);
    }

    private void addNavHeaderView() {
        navHeaderMainBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.nav_header_main, activityMainBinding.navView, false);
        activityMainBinding.navView.addHeaderView(navHeaderMainBinding.getRoot());
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
                break;
            case R.id.tab_home:
                setCurrentTab(MainActivityVM.TAB_HOME);
                break;
            case R.id.tab_favorite:
                setCurrentTab(MainActivityVM.TAB_FAVORITE);
                break;
            case R.id.tab_profile:
                setCurrentTab(MainActivityVM.TAB_PROFILE);
                break;
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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.nav_add_event:
                Intent intent = new Intent(this, CreateEventActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_user_infor:
                Intent detailUserIntent = new Intent(this, DetailUserActivity.class);
                startActivity(detailUserIntent);
                break;
            case R.id.nav_logout:
                AuthUI.getInstance()
                        .signOut(this)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                backToLoginActivity();
                            }
                        });
                break;
            default:
                break;
        }
        mDrawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void backToLoginActivity() {
        Ultil.clearUserData(this);
        Intent intent = new Intent(this, LoginActivity.class);
        this.finish();
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            getCurrentFragment().showBack();
        }
    }
}
