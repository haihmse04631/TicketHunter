package com.example.macbookpro.ticketapp.helper.apiservice;

import android.content.Context;

import com.example.macbookpro.ticketapp.R;

/**
 * Created by Hoang Hai on 1/13/19.
 */
public class ApiResultCode {
    public static final int HTTP_CODE_SUCSESS = 200;
    public static final int ERROR_CODE = -1;
    public static final int ERROR_NO_CONNECTION = -2;
    public static final int ERROR_TIME_OUT = -3;
    public static final int RESPONSE_SUCCESS = 0;
    public static final int ERROR_UPDATE_APP = 2000;


    public static String getMessage(Context mContext, int resultCode) {
        int res;
        switch (resultCode) {
            case ERROR_NO_CONNECTION:
                res = R.string.alert_msg_noconnection;
                break;
            case ERROR_UPDATE_APP:
                res = R.string.alert_update_version;
                break;
            case ERROR_TIME_OUT:
            default:
                res = R.string.error_message;
                break;
        }
        return mContext.getString(res);
    }
}
