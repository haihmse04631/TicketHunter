package com.example.macbookpro.ticketapp.viewmodels.activitys;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.view.View;
import com.example.macbookpro.ticketapp.BR;

import com.example.macbookpro.ticketapp.viewmodels.base.BaseActivityVM;

/**
 * Created by Hoang Hai on 1/21/19.
 */
public class LoginActivityVM extends BaseActivityVM {

    @Bindable
    private boolean flagLoginState = true;
    @Bindable
    private boolean flagRegisterState = false;

    public boolean isFlagLoginState() {
        return flagLoginState;
    }

    public void setFlagLoginState(boolean flagLoginState) {
        this.flagLoginState = flagLoginState;
    }

    public boolean isFlagRegisterState() {
        return flagRegisterState;
    }

    public void setFlagRegisterState(boolean flagRegisterState) {
        this.flagRegisterState = flagRegisterState;
    }

    public void notifyStateChange() {
        notifyPropertyChanged(BR.flagLoginState);
        notifyPropertyChanged(BR.flagRegisterState);
    }

    public interface LoginActionListened {
        void onLoginChangeStateTapped(View view);
        void onRegisterChangeStateTapped(View view);
        void onLoginTapped(View view);
        void onRegisterTapped(View view);
        void onLoginFacebookTapped(View view);
        void onLoginTwitterTapped(View view);
        void onLoginGoogleTapped(View view);
    }

}
