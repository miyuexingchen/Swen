package com.wcc.swen.utils;

import android.os.Handler;
import android.os.Looper;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by WangChenchen on 2016/8/19.
 */
public class OkHttpUtils {

    private static OkHttpUtils sInstance;

    private OkHttpClient mOkHttpClient;

    private Gson gson;

    private Handler mHandler;
    private Call call;
    private Call call1;

    private OkHttpUtils() {
        mOkHttpClient = new OkHttpClient();
        mHandler = new Handler(Looper.getMainLooper());
        gson = new Gson();
    }

    public static OkHttpUtils getsInstance() {
        if (sInstance == null) {
            synchronized (OkHttpUtils.class) {
                sInstance = new OkHttpUtils();
            }
        }

        return sInstance;
    }

    public static String getResponse(String url) {
        return getsInstance().getResponseString(url);
    }

    private String getResponseString(String url) {
        String str = "";
        Request request = new Request.Builder().url(url).build();
        call = mOkHttpClient.newCall(request);
        try {
            Response response = call.execute();
            str = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return str;
    }
}
