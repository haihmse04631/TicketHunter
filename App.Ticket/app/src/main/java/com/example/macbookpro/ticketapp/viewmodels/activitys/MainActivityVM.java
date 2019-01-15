package com.example.macbookpro.ticketapp.viewmodels.activitys;

import android.databinding.Bindable;
import android.view.View;

import com.example.macbookpro.ticketapp.BR;
import com.example.macbookpro.ticketapp.viewmodels.base.BaseActivityVM;

/**
 * Created by Hoang Hai on 1/13/19.
 */
public class MainActivityVM extends BaseActivityVM {
    public static final int TAB_HOME = 0;
    public static final int TAB_FAVORITE = 1;
    public static final int TAB_PROFILE = 2;

    @Bindable
    private boolean flagSelectedTabHome = true;
    @Bindable
    private boolean flagSelectedTabFavorite = false;
    @Bindable
    private boolean flagSelectedTabProfile = false;

    public boolean isFlagSelectedTabHome() {
        return flagSelectedTabHome;
    }

    public void setFlagSelectedTabHome(boolean flagSelectedTabHome) {
        this.flagSelectedTabHome = flagSelectedTabHome;
    }

    public boolean isFlagSelectedTabFavorite() {
        return flagSelectedTabFavorite;
    }

    public void setFlagSelectedTabFavorite(boolean flagSelectedTabFavorite) {
        this.flagSelectedTabFavorite = flagSelectedTabFavorite;
    }

    public boolean isFlagSelectedTabProfile() {
        return flagSelectedTabProfile;
    }

    public void setFlagSelectedTabProfile(boolean flagSelectedTabProfile) {
        this.flagSelectedTabProfile = flagSelectedTabProfile;
    }

    public void setSelectedTab(int index) {
        unSelectAllTab();
        switch (index) {
            default:
            case TAB_HOME:
                setFlagSelectedTabHome(true);
                break;
            case TAB_FAVORITE:
                setFlagSelectedTabFavorite(true);
                break;
            case TAB_PROFILE:
                setFlagSelectedTabProfile(true);
                break;
        }
        notifyTabChange();
    }

    private void unSelectAllTab() {
        setFlagSelectedTabFavorite(false);
        setFlagSelectedTabHome(false);
        setFlagSelectedTabProfile(false);
    }

    private void notifyTabChange() {
        notifyPropertyChanged(BR.flagSelectedTabHome);
        notifyPropertyChanged(BR.flagSelectedTabFavorite);
        notifyPropertyChanged(BR.flagSelectedTabProfile);
    }

    public interface OnSelectedTabCallBack {
        void onTabSelected(View view);
    }
}
