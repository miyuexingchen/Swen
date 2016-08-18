package com.wcc.swen.utils;

import android.util.Log;

/**
 * Created by WangChenchen on 2016/8/18.
 */
public class LogUtils {

    private static boolean islog = true;

    public static void v(String tag, String msg) {
        if (islog)
            Log.v(tag, msg);
    }

    public static void i(String tag, String msg) {
        if (islog)
            Log.i(tag, msg);
    }

    public static void d(String tag, String msg) {
        if (islog)
            Log.d(tag, msg);
    }

    public static void w(String tag, String msg) {
        if (islog)
            Log.w(tag, msg);
    }

    public static void e(String tag, String msg) {
        if (islog)
            Log.e(tag, msg);
    }
}
