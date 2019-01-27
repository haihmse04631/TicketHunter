package com.example.macbookpro.ticketapp.views.fragments.homescreen;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.macbookpro.ticketapp.R;
import com.example.macbookpro.ticketapp.databinding.FragmentHomeBinding;
import com.example.macbookpro.ticketapp.helper.apiservice.ApiClient;
import com.example.macbookpro.ticketapp.models.AppleMusic;
import com.example.macbookpro.ticketapp.models.Category;
import com.example.macbookpro.ticketapp.models.Event;
import com.example.macbookpro.ticketapp.viewmodels.fragments.HomeFragmentVM;
import com.example.macbookpro.ticketapp.views.adapter.CategoryAdapter;
import com.example.macbookpro.ticketapp.views.adapter.EventListAdapter;
import com.example.macbookpro.ticketapp.views.base.BindingFragment;
import com.example.macbookpro.ticketapp.views.customviews.CustomProgress;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends BindingFragment implements CategoryAdapter.CategoryAdapterListened, HomeFragmentVM.HomeFragmentListened, EventListAdapter.EventListAdapterListened {

    private FragmentHomeBinding binding;
    private CategoryAdapter categoryAdapter;
    private RecyclerView recyclerView;
    private RecyclerView eventsRecycleView;
    private HomeFragmentVM homeFragmentVM;
    private EventListAdapter eventListAdapter;

    public static HomeFragment newInstance() {

        Bundle args = new Bundle();

        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        CustomProgress.getInstance().showLoading(getActivity());
        binding = (FragmentHomeBinding) getViewBinding();
        binding.setListened(this);
        homeFragmentVM = new HomeFragmentVM();
        homeFragmentVM.getCategories();
        initRecycleView();
        getAppleMusic(homeFragmentVM.categories.get(0).getId());
        initEventsRecycleView();
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

    private void initEventsRecycleView() {
        Log.e("entries: ", homeFragmentVM.appleMusic.getFeed().getEntries().size() + "");
        eventsRecycleView = binding.eventsView;
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        eventsRecycleView.setLayoutManager(layoutManager);
        //eventsRecycleView.setItemAnimator(new SlideInLeftAnimator());
        eventsRecycleView.setNestedScrollingEnabled(false);
        eventListAdapter = new EventListAdapter(homeFragmentVM.events, this);
        eventsRecycleView.setAdapter(eventListAdapter);
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
        getAppleMusic(category.getId());
        CustomProgress.getInstance().showLoading(getContext());
    }

    @Override
    public void onShowAllCategoriesTapped(View view) {
        showNext(AllCategoriesFragment.newInstance());
    }

    @Override
    public void onEventTapped(Event event) {
        Toast.makeText(getActivity(), event.getName(), Toast.LENGTH_LONG).show();
    }

    public void getAppleMusic(String id) {
        final Call<AppleMusic> appleMusicCall = ApiClient.getInstance().getApi().getAppleMusic(id);
        appleMusicCall.enqueue(new Callback<AppleMusic>() {
            @Override
            public void onResponse(Call<AppleMusic> call, Response<AppleMusic> response) {
                Log.e("responseStatus: ", "Load Done!");
                homeFragmentVM.appleMusic = response.body();
                homeFragmentVM.prepareDataEventList();
                eventListAdapter.notifyDataSetChanged();
                eventsRecycleView.smoothScrollToPosition(0);
                CustomProgress.getInstance().hideLoading();
            }

            @Override
            public void onFailure(Call<AppleMusic> call, Throwable t) {

            }
        });
    }
}
