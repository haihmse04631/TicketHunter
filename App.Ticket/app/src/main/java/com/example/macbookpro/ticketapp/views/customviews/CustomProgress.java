package com.example.macbookpro.ticketapp.views.customviews;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;

import com.example.macbookpro.ticketapp.R;

/**
 * Created by Hoang Hai on 1/27/19.
 */
public class CustomProgress {

    private static CustomProgress customProgress = null;
    private Dialog mDialog;

    public static CustomProgress getInstance() {
        if (customProgress == null) {
            customProgress = new CustomProgress();
        }
        return customProgress;
    }

    public void showLoading(Context context) {
        mDialog = new Dialog(context);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.setContentView(R.layout.custom_loading_view);
        mDialog.setCancelable(false);
        mDialog.show();
    }

    public void hideLoading() {
        if (mDialog != null) {
            mDialog.dismiss();
            mDialog = null;
        }
    }

}
