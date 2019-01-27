package com.example.macbookpro.ticketapp.views.fragments.homescreen;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.macbookpro.ticketapp.R;
import com.example.macbookpro.ticketapp.databinding.FragmentHomeBinding;
import com.example.macbookpro.ticketapp.models.Category;
import com.example.macbookpro.ticketapp.viewmodels.fragments.HomeFragmentVM;
import com.example.macbookpro.ticketapp.views.adapter.CategoryAdapter;
import com.example.macbookpro.ticketapp.views.base.BindingFragment;
import com.example.macbookpro.ticketapp.views.customviews.CustomProgress;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends BindingFragment implements CategoryAdapter.CategoryAdapterListened, HomeFragmentVM.HomeFragmentListened {

    private FragmentHomeBinding binding;
    private CategoryAdapter categoryAdapter;
    private RecyclerView recyclerView;
    private HomeFragmentVM homeFragmentVM;

    public static HomeFragment newInstance() {

        Bundle args = new Bundle();

        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = (FragmentHomeBinding) getViewBinding();
        binding.setListened(this);
        homeFragmentVM = new HomeFragmentVM();
        homeFragmentVM.getCategories();
        initRecycleView();
    }

    private void initRecycleView() {
        recyclerView = binding.categotiesView;
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);
        categoryAdapter = new CategoryAdapter(homeFragmentVM.categories, this);
        recyclerView.setAdapter(categoryAdapter);
    }

    @Override
    protected int layoutRes() {
        return R.layout.fragment_home;
    }

    @Override
    protected int containerViewId() {
        return R.id.navigation_home;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onCategoryTapped(Category category) {
        homeFragmentVM.getAppleMusic(category.getId());
        CustomProgress.getInstance().showLoading(getContext());
    }

    @Override
    public void onShowAllCategoriesTapped(View view) {
        showNext(AllCategoriesFragment.newInstance());
    }
}
