package com.example.macbookpro.ticketapp.views.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.macbookpro.ticketapp.R;

import static android.support.v4.app.FragmentManager.POP_BACK_STACK_INCLUSIVE;

/**
 * Created by Hoang Hai on 1/13/19.
 */
public abstract class BindingFragment<VB extends ViewDataBinding> extends Fragment {
    public static final String PARENT_CONTAINER_VIEW_ID = "parent_container_view_id";
    private VB viewBinding;

    public static BindingFragment newInstance(Class<? extends BindingFragment> aClass, int parentContentViewId) {
        //create bundle to put fragment info to transfer.
        Bundle bundle = new Bundle();
        bundle.putInt(PARENT_CONTAINER_VIEW_ID, parentContentViewId);

        BindingFragment fragment = null;
        try {
            fragment = aClass.newInstance();
            fragment.setArguments(bundle);
        } catch (java.lang.InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return fragment;
    }

    @LayoutRes
    protected abstract int layoutRes();

    protected VB getViewBinding() {
        return viewBinding;
    }

    protected <T extends ViewDataBinding> T createContentView(LayoutInflater inflater,
                                                              ViewGroup parent,
                                                              @LayoutRes int layoutResourceId) {
        return DataBindingUtil.inflate(inflater, layoutResourceId, parent, false);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        int layoutRes = layoutRes();
        if (layoutRes > 0) {
            viewBinding = createContentView(inflater, container, layoutRes);
            return viewBinding.getRoot();
        } else {
            return super.onCreateView(inflater, container, savedInstanceState);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        viewBinding = null;
    }

    /**
     * replace fragment
     *
     * @param fragment
     */
    public void showNext(BindingFragment fragment) {
        int containerViewId = containerViewId();
        if (containerViewId == -1) {
            throw new IllegalArgumentException("Container view id was not set.");
        }

        FragmentTransaction transaction;
        if (getFragmentManager() != null) {
            transaction = getFragmentManager().beginTransaction();
            transaction.setCustomAnimations(
                    R.anim.fragment_slide_right_enter,
                    R.anim.fragment_slide_right_exit,
                    R.anim.fragment_slide_left_enter,
                    R.anim.fragment_slide_left_exit).replace(containerViewId, fragment)
                    .addToBackStack(fragment.getClass().getName())
                    .commitAllowingStateLoss();
        }
    }

    /**
     * back to previous fragment
     *
     * @return
     */
    public boolean showBack() {
        int entryCount = 0;
        if (getFragmentManager() != null) {
            entryCount = getFragmentManager().getBackStackEntryCount();
        }
        if (entryCount == 0) {
            return false;
        }
        getFragmentManager().popBackStackImmediate();
        return true;
    }

    /**
     * back to base fragment
     */
    public void goBackToBaseFragment() {
        int entryCount = 0;
        if (getFragmentManager() != null) {
            entryCount = getFragmentManager().getBackStackEntryCount();
        }
        if (entryCount == 0) {
            return;
        }
        String name = getFragmentManager().getBackStackEntryAt(0).getName();
        getFragmentManager().popBackStack(name, POP_BACK_STACK_INCLUSIVE);

    }

    protected int containerViewId() {
        Bundle arguments = getArguments();
        if (arguments != null) {
            return arguments.getInt(PARENT_CONTAINER_VIEW_ID, -1);
        }
        return -1;
    }
}
