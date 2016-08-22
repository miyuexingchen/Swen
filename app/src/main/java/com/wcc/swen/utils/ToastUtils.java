package com.wcc.swen.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by WangChenchen on 2016/8/22.
 */
public class ToastUtils {

    private static Toast toast;

    public static void show(String msg, Context context) {
        if (toast == null) {
            toast = Toast.makeText(context, "", Toast.LENGTH_SHORT);
        }
        toast.setText(msg);
        toast.show();
    }
}
