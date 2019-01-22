package com.example.macbookpro.ticketapp.views.activitys;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.macbookpro.ticketapp.R;
import com.example.macbookpro.ticketapp.databinding.ActivityLoginBinding;
import com.example.macbookpro.ticketapp.helper.constant.Constant;
import com.example.macbookpro.ticketapp.viewmodels.activitys.LoginActivityVM;
import com.example.macbookpro.ticketapp.views.base.BindingActivity;
import com.facebook.login.Login;

public class LoginActivity extends BindingActivity implements LoginActivityVM.LoginActionListened {

    private LoginActivityVM loginActivityVM;
    private ActivityLoginBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = (ActivityLoginBinding) getViewBinding();
        loginActivityVM = new LoginActivityVM();
        binding.setListened(this);
        binding.setLoginVM(loginActivityVM);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_login;
    }


    @Override
    public void onLoginChangeStateTapped(View view) {
        if (loginActivityVM.isFlagRegisterState()) {
            loginActivityVM.setFlagLoginState(true);
            loginActivityVM.setFlagRegisterState(false);
            loginActivityVM.notifyStateChange();
            showLayout(binding.loginView, true);
            showLayout(binding.registerView, false);
        }
    }

    @Override
    public void onRegisterChangeStateTapped(View view) {
        if (loginActivityVM.isFlagLoginState()) {
            loginActivityVM.setFlagRegisterState(true);
            loginActivityVM.setFlagLoginState(false);
            loginActivityVM.notifyStateChange();
            showLayout(binding.loginView, false);
            showLayout(binding.registerView, true);
        }
    }

    @Override
    public void onLoginTapped(View view) {
        SharedPreferences.Editor editor = getSharedPreferences(Constant.TK_SHARE_PREFERENCE, MODE_PRIVATE).edit();
        editor.putString(Constant.USER_ID, "123456789"); // put user id after login success
        editor.apply();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onRegisterTapped(View view) {
        Toast.makeText(this, "register", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onLoginFacebookTapped(View view) {

    }

    @Override
    public void onLoginTwitterTapped(View view) {

    }

    @Override
    public void onLoginGoogleTapped(View view) {

    }

    private void showLayout( View view, boolean show){
        if (show){
            view.animate()
                    .translationX(0)
                    .setDuration(300)
                    .alpha(1)
                    .start();
            view.setVisibility(View.VISIBLE);
        } else {
            view.animate()
                    .translationX(view.getWidth())
                    .alpha(0)
                    .setDuration(100)
                    .start();
            view.setVisibility(View.GONE);
        }
    }
}
