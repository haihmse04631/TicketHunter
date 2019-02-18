package com.example.macbookpro.ticketapp.views.activitys;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.macbookpro.ticketapp.R;
import com.example.macbookpro.ticketapp.databinding.ActivityLoginBinding;
import com.example.macbookpro.ticketapp.helper.constant.Constant;
import com.example.macbookpro.ticketapp.helper.ultility.Ultil;
import com.example.macbookpro.ticketapp.models.User;
import com.example.macbookpro.ticketapp.viewmodels.activitys.LoginActivityVM;
import com.example.macbookpro.ticketapp.views.base.BindingActivity;
import com.example.macbookpro.ticketapp.views.customviews.SimpleDialog;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class LoginActivity extends BindingActivity implements LoginActivityVM.LoginActionListened {

    private String TAG = this.getClass().getName();
    private static final int LOCATION_PERMISSTION_REQUEST_CODE = 1;

    private LoginActivityVM loginActivityVM;
    private ActivityLoginBinding binding;
    private CallbackManager callbackManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViewBinding();
        initSetingFacebookAuth();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSTION_REQUEST_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void initViewBinding() {
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
        if (Ultil.isEmpty(loginActivityVM.email)) {
            SimpleDialog.getInstance().show(this, "Hãy điền địa chỉ Email");
        } else if (!Ultil.isEmailValid(loginActivityVM.email)) {
            SimpleDialog.getInstance().show(this, "Sai định dạng email");
        } else if (Ultil.isEmpty(loginActivityVM.password)) {
            SimpleDialog.getInstance().show(this, "Hãy điền Password");
        } else {
            User user = new User("1234567", "Ice Tea", "", Constant.DEFAULT_ACCTION);
            processLoginSuccess(user);
        }
    }

    @Override
    public void onRegisterTapped(View view) {
        if (Ultil.isEmpty(loginActivityVM.regEmail)) {
            SimpleDialog.getInstance().show(this, "Hãy điền địa chỉ Email");
        } else if (!Ultil.isEmailValid(loginActivityVM.regEmail)) {
            SimpleDialog.getInstance().show(this, "Sai định dạng email");
        } else if (Ultil.isEmpty(loginActivityVM.regPassword)) {
            SimpleDialog.getInstance().show(this, "Hãy điền Password");
        } else if (Ultil.isEmpty(loginActivityVM.regConfirmPassword)) {
            SimpleDialog.getInstance().show(this, "Hãy điền Confirm Password");
        } else if (!loginActivityVM.regPassword.equals(loginActivityVM.regConfirmPassword)) {
            SimpleDialog.getInstance().show(this, "Mật khẩu không khớp");
        } else {
            User user = new User("1234567", "Ice Tea", "", Constant.DEFAULT_ACCTION);
            processLoginSuccess(user);
        }
    }

    @Override
    public void onLoginFacebookTapped(View view) {
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "user_friends"));
    }

    @Override
    public void onLoginTwitterTapped(View view) {

    }

    @Override
    public void onLoginGoogleTapped(View view) {

    }

    // FUNCTION

    private void processLoginSuccess(User user) {
        saveToSharePreference(user);
        goToMainScreen();
    }

    private void showLayout(View view, boolean show) {
        if (show) {
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

    // For Facebook

    private void initSetingFacebookAuth() {
        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Toast.makeText(LoginActivity.this, "Login Success", Toast.LENGTH_LONG).show();
                        loadInformation();
                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(LoginActivity.this, "Cancel", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(FacebookException error) {
                        Toast.makeText(LoginActivity.this, "Error", Toast.LENGTH_LONG).show();
                    }
                });
    }

    protected void loadInformation() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        boolean isLoggedIn = accessToken != null && !accessToken.isExpired();

        if (isLoggedIn) {
            Bundle params = new Bundle();
            params.putString("fields", "id,name,email,picture.width(960)");

            new GraphRequest(AccessToken.getCurrentAccessToken(),
                    "/" + accessToken.getUserId() + "/",
                    params,
                    HttpMethod.GET,
                    new GraphRequest.Callback() {
                        public void onCompleted(GraphResponse response) {
                            Log.i("Data: ", response.toString());
                            try {
                                JSONObject obj = response.getJSONObject();
                                Log.d(TAG, "Name: " + obj.getString("name") +
                                        "Avatar: " + obj.getJSONObject("picture").getJSONObject("data").getString("url"));
                                String id = obj.getString("id");
                                String name = obj.getString("name");
                                String avatarUrl = obj.getJSONObject("picture").getJSONObject("data").getString("url");
                                String accountType = Constant.FACEBOOK_ACCOUNT;
                                User user = new User(id, name, avatarUrl, accountType);
                                saveToSharePreference(user);
                                goToMainScreen();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
            ).executeAsync();
        } else {
            Log.i("Data: ", "Not yet");
        }
    }

    private void goToMainScreen() {
        Intent intent = new Intent(this, MainActivity.class);
        finish();
        startActivity(intent);
    }

    private void saveToSharePreference(User user) {
        SharedPreferences.Editor editor = getSharedPreferences(Constant.TK_SHARE_PREFERENCE, MODE_PRIVATE).edit();
        Gson gson = new Gson();
        String json = gson.toJson(user);
        editor.putString(Constant.USER_DATA, json);
        editor.apply();
    }


}
