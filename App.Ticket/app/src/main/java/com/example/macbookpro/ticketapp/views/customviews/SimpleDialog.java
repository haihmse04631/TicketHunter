package com.example.macbookpro.ticketapp.views.customviews;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by Hoang Hai on 2/18/19.
 */
public class SimpleDialog {

    private static SimpleDialog simpleDialog;
    private AlertDialog.Builder builder;

    public static SimpleDialog getInstance() {
        if (simpleDialog == null) {
            simpleDialog = new SimpleDialog();
        }
        return simpleDialog;
    }

    public void show(Context context, String message) {
        builder = new AlertDialog.Builder(context, android.R.style.Theme_Material_Light_Dialog_Alert);
        builder.setTitle("Ticket Hunter")
                .setMessage(message)
                .setPositiveButton("Đóng", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .show();
    }

}
