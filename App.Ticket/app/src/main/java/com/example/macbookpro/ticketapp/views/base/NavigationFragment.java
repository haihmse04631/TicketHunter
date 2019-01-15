package com.example.macbookpro.ticketapp.views.base;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

/**
 * Created by Hoang Hai on 1/13/19.
 */
public abstract class NavigationFragment extends BindingFragment {
    protected abstract BindingFragment onCreateMainFragment();

    protected abstract int getContentViewId();

    @SuppressLint("ResourceType")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        int contentViewId = getContentViewId();
        Fragment fragment = onCreateMainFragment();

        //add main fragment in parent fragment.
        if (layoutRes() > 0 && contentViewId > 0 && fragment != null) {
            getChildFragmentManager().beginTransaction()
                    .add(contentViewId, fragment)
                    .commit();
        }
    }

    public BindingFragment getSubpageFragment() {
        if (isAdded()) {
            return (BindingFragment) getChildFragmentManager().findFragmentById(getContentViewId());
        }
        return null;
    }
}
