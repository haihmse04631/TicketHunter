package com.example.macbookpro.ticketapp.helper.apiservice;

import android.app.Service;
import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.example.macbookpro.ticketapp.models.BaseApiModel;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;

import java.io.IOException;
import java.net.SocketTimeoutException;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Hoang Hai on 1/13/19.
 */
public abstract class ApiCallBack<T extends BaseApiModel> implements retrofit2.Callback<T> {
    private static final String TAG = ApiCallBack.class.getSimpleName();
    protected Context mContext;

    public ApiCallBack(Context context) {
        this.mContext = context;
    }

    public abstract void onResponse(Call<T> call, T response);

    protected abstract boolean isVisibleToUser();

    public void onHandleError(int errorCode) {
        onHandleError(errorCode, true);
    }

    public void onHandleError(int errorCode, boolean isShowMessage) {

        if (mContext instanceof Service) {
            // Background service doesnt need to notify ui error
            return;
        }

        if (isVisibleToUser() && isShowMessage) {
            showErrorMessage(errorCode);
        }
    }

    protected void showErrorMessage(int code) {
        String message = ApiResultCode.getMessage(mContext, code);
        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
    }

    private boolean isSuccessResponse(int resultCode) {
        return ApiResultCode.RESPONSE_SUCCESS == resultCode;
    }

    @Override
    public void onResponse(@NonNull Call<T> call, @NonNull Response<T> response) {
        if (!isVisibleToUser()) {
            return;
        }

        if (response.code() != ApiResultCode.HTTP_CODE_SUCSESS && response.errorBody() != null) {
            String errorBodyString = "";
            try {
                errorBodyString = response.errorBody().string();
            } catch (IOException e) {
                e.printStackTrace();
            }
            BaseApiModel errorBody = null;
            try {
                errorBody = new Gson().fromJson(errorBodyString, BaseApiModel.class);
            } catch (JsonParseException e) {
                e.printStackTrace();
            }
            if (errorBody != null) {
                onHandleError(errorBody.getResultCode());
            } else {
                onHandleError(ApiResultCode.ERROR_CODE);
            }
            return;
        }

        T model = response.body();
        if (model == null) {
            onHandleError(ApiResultCode.ERROR_CODE);
            return;
        }
        if (isSuccessResponse(model.getResultCode())) {
            onResponse(call, model);
        } else {
            onHandleError(model.getResultCode());
        }
    }

    @Override
    public void onFailure(@NonNull Call<T> call, @NonNull Throwable t) {
        t.printStackTrace();
        if (t instanceof SocketTimeoutException) {
            onHandleError(ApiResultCode.ERROR_TIME_OUT);
        } else if (t instanceof IOException) {
            onHandleError(ApiResultCode.ERROR_NO_CONNECTION);
        } else {
            onHandleError(ApiResultCode.ERROR_CODE);
        }
    }
}
