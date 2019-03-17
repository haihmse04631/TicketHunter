package com.example.macbookpro.ticketapp.viewmodels.activitys;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.util.Log;
import android.view.View;
import com.example.macbookpro.ticketapp.BR;

import com.example.macbookpro.ticketapp.helper.apiservice.ApiClient;
import com.example.macbookpro.ticketapp.helper.ultility.Ultil;
import com.example.macbookpro.ticketapp.models.ResponseMessage;
import com.example.macbookpro.ticketapp.models.User;
import com.example.macbookpro.ticketapp.models.UserParam;
import com.example.macbookpro.ticketapp.viewmodels.base.BaseActivityVM;
import com.example.macbookpro.ticketapp.views.activitys.LoginActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Hoang Hai on 1/21/19.
 */
public class LoginActivityVM extends BaseActivityVM {

    private Context mContext;
    private LoginApiCallBack listened;

    public UserParam userParam = new UserParam();

    public LoginActivityVM(Context mContext, LoginApiCallBack listened) {
        this.mContext = mContext;
        this.listened = listened;
    }

    public void pushUserInforToServer() {
        final Call<ResponseMessage> userCall = ApiClient.getInstance().getApi().createUser(userParam);
        userCall.enqueue(new Callback<ResponseMessage>() {
            @Override
            public void onResponse(Call<ResponseMessage> call, Response<ResponseMessage> response) {
                User user = new User(userParam.getId(), userParam.getFirstName(), userParam.getLastName() ,userParam.getEmail(), userParam.getPhone(), userParam.getAvatarUrl());
                Ultil.saveUserToSharedPreference(user, mContext);
                listened.onCreateUserSuccess();
            }

            @Override
            public void onFailure(Call<ResponseMessage> call, Throwable t) {
                listened.onFailure("Có lỗi xảy ra, Đăng nhập không thành công!");
            }
        });

    }

    public interface LoginApiCallBack {
        void onCreateUserSuccess();
        void onFailure(String message);
    }

    public interface LoginActionListened {
        void onLoginTapped(View view);
    }

}
