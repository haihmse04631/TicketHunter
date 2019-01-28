package com.example.macbookpro.ticketapp.views.fragments.homescreen;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.macbookpro.ticketapp.R;
import com.example.macbookpro.ticketapp.databinding.FragmentAllCategoriesBinding;
import com.example.macbookpro.ticketapp.helper.ultility.GridSpacingItemDecoration;
import com.example.macbookpro.ticketapp.helper.ultility.Ultil;
import com.example.macbookpro.ticketapp.models.Category;
import com.example.macbookpro.ticketapp.viewmodels.fragments.HomeFragmentVM;
import com.example.macbookpro.ticketapp.views.adapter.AllCategoiesAdapter;
import com.example.macbookpro.ticketapp.views.base.BindingFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class AllCategoriesFragment extends BindingFragment implements AllCategoiesAdapter.AllCategoryAdapterListened {

    private FragmentAllCategoriesBinding binding;
    private AllCategoiesAdapter allCategoiesAdapter;
    private RecyclerView recyclerView;
    private HomeFragmentVM homeFragmentVM;

    public static AllCategoriesFragment newInstance() {

        Bundle args = new Bundle();

        AllCategoriesFragment fragment = new AllCategoriesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = (FragmentAllCategoriesBinding) getViewBinding();
        homeFragmentVM = new HomeFragmentVM();
        homeFragmentVM.getCategories();
        initRecycleView();
    }

    private void initRecycleView() {
        recyclerView = binding.categotiesView;
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, Ultil.dpToPx(getResources(),8), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);
        allCategoiesAdapter = new AllCategoiesAdapter(homeFragmentVM.categories, this);
        recyclerView.setAdapter(allCategoiesAdapter);
    }

    @Override
    protected int layoutRes() {
        return R.layout.fragment_all_categories;
    }

    @Override
    public void onCategoryTapped(Category category) {
        showNext(AllEventsFragment.newInstance(category.getId()));
    }

    @Override
    protected int containerViewId() {
        return R.id.navigation_home;
    }
}
