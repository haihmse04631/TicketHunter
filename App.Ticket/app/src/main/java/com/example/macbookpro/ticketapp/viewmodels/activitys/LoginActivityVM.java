package com.example.macbookpro.ticketapp.viewmodels.activitys;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.util.Log;
import android.view.View;
import com.example.macbookpro.ticketapp.BR;

import com.example.macbookpro.ticketapp.helper.ultility.Ultil;
import com.example.macbookpro.ticketapp.viewmodels.base.BaseActivityVM;

/**
 * Created by Hoang Hai on 1/21/19.
 */
public class LoginActivityVM extends BaseActivityVM {

    @Bindable
    private boolean flagLoginState = true;
    @Bindable
    private boolean flagRegisterState = false;
    public String email = "";
    public String password = "";
    public String regEmail = "";
    public String regPassword = "";
    public String regConfirmPassword = "";

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

    public void afterEmailTextChanged(CharSequence email) {
        this.email = email.toString();
    }

    public void afterPasswordTextChanged(CharSequence password) {
        this.password = password.toString();
    }

    public void afterRegEmailTextChanged(CharSequence regEmail) {
        this.regEmail = regEmail.toString();
    }

    public void afterRegPasswordTextChanged(CharSequence regPassword) {
        this.regPassword = regPassword.toString();
    }

    public void afterRegConfirmPasswordTextChanged(CharSequence regConfirmPassword) {
        this.regConfirmPassword = regConfirmPassword.toString();
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
