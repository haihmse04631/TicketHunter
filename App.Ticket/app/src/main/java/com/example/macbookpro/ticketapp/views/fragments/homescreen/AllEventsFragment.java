package com.example.macbookpro.ticketapp.views.fragments.homescreen;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.macbookpro.ticketapp.R;
import com.example.macbookpro.ticketapp.databinding.FragmentAllEventsBinding;
import com.example.macbookpro.ticketapp.helper.apiservice.ApiClient;
import com.example.macbookpro.ticketapp.models.AppleMusic;
import com.example.macbookpro.ticketapp.models.Event;
import com.example.macbookpro.ticketapp.viewmodels.fragments.HomeFragmentVM;
import com.example.macbookpro.ticketapp.views.adapter.EventListAdapter;
import com.example.macbookpro.ticketapp.views.base.BindingFragment;
import com.example.macbookpro.ticketapp.views.customviews.CustomProgress;
import com.example.macbookpro.ticketapp.views.fragments.DetailEventFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class AllEventsFragment extends BindingFragment implements EventListAdapter.EventListAdapterListened {

    public static final String CATEGORY_ID_PARAM ="category_id_param";
    FragmentAllEventsBinding binding;
    EventListAdapter eventListAdapter;
    RecyclerView recyclerView;
    HomeFragmentVM homeFragmentVM;

    public static AllEventsFragment newInstance(String categoryId) {

        Bundle args = new Bundle();

        AllEventsFragment fragment = new AllEventsFragment();
        args.putString(CATEGORY_ID_PARAM, categoryId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = (FragmentAllEventsBinding) getViewBinding();
        homeFragmentVM = new HomeFragmentVM();
        homeFragmentVM.getCategories();
        String categoryId = getArguments().getString(CATEGORY_ID_PARAM, "0");
        CustomProgress.getInstance().showLoading(getActivity());
        getAppleMusic(categoryId);
        initEventsRecycleView();
    }

    private void initEventsRecycleView() {
        recyclerView = binding.eventsView;
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setNestedScrollingEnabled(false);
        eventListAdapter = new EventListAdapter(homeFragmentVM.events, this);
        recyclerView.setAdapter(eventListAdapter);
    }

    @Override
    protected int layoutRes() {
        return R.layout.fragment_all_events;
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
                CustomProgress.getInstance().hideLoading();
            }

            @Override
            public void onFailure(Call<AppleMusic> call, Throwable t) {

            }
        });
    }

    @Override
    public void onEventTapped(Event event) {
        showNext(DetailEventFragment.newInstance());
    }

    @Override
    protected int containerViewId() {
        return R.id.navigation_home;
    }
}
