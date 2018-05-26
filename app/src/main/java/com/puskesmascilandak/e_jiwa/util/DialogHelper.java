package com.puskesmascilandak.e_jiwa.util;

import android.content.Context;
import android.support.v7.app.AlertDialog;

import com.puskesmascilandak.e_jiwa.R;

public class DialogHelper {
    public static void showDialog(Context context, String title, String message) {
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setIcon(context.getResources().getDrawable(R.mipmap.ic_launcher))
                .show();
    }

}
