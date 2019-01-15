package com.example.macbookpro.ticketapp.views.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Hoang Hai on 1/13/19.
 */
public abstract class BindingActivity<VB extends ViewDataBinding> extends AppCompatActivity {
    private VB viewBinding;

    protected <T extends ViewDataBinding> T setBindingContentView(@LayoutRes int layoutRes) {
        return DataBindingUtil.setContentView(this, layoutRes);
    }

    protected VB getViewBinding() {
        return viewBinding;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int layoutId = getLayoutResourceId();
        if (layoutId > 0) {
            viewBinding = setBindingContentView(layoutId);
        }
    }

    @LayoutRes
    protected abstract int getLayoutResourceId();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        viewBinding = null;
    }
}
