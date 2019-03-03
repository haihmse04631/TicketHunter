package com.example.macbookpro.ticketapp.viewmodels.activitys;

import android.databinding.Bindable;
import android.view.View;

import com.example.macbookpro.ticketapp.BR;
import com.example.macbookpro.ticketapp.viewmodels.base.BaseActivityVM;

/**
 * Created by Hoang Hai on 2/12/19.
 */
public class DetailEventActivityVM extends BaseActivityVM {

    @Bindable
    private boolean flagCommentLayoutPresenting = false;

    public boolean isFlagCommentLayoutPresenting() {
        return flagCommentLayoutPresenting;
    }

    public void setFlagCommentLayoutPresenting(boolean flagCommentLayoutPresenting) {
        this.flagCommentLayoutPresenting = flagCommentLayoutPresenting;
        notifyPropertyChanged(BR.flagCommentLayoutPresenting);
    }

    public interface DetailEventListened {
        void onCommentTapped(View view);
    }

}
