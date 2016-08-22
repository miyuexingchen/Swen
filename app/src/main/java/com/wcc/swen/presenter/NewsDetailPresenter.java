package com.wcc.swen.presenter;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;

import com.google.gson.Gson;
import com.wcc.swen.contract.NewsDetailContract;
import com.wcc.swen.model.NewsModel;
import com.wcc.swen.model.NewsWrapper;
import com.wcc.swen.utils.LogUtils;
import com.wcc.swen.utils.NetUtils;
import com.wcc.swen.utils.OkHttpUtils;
import com.wcc.swen.utils.ToastUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by WangChenchen on 2016/8/19.
 */
public class NewsDetailPresenter implements NewsDetailContract.Presenter {

    private static final String tag = "NewsDetailPresenter";

    private static final int ON_SUCCESS = 0;
    private static final int ON_FAILURE = 1;
    private static final int NETWORK_CANNOT_ACCESS = 2;

    private NewsDetailContract.View mView;
    Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case ON_SUCCESS:
                    mView.showView(mView.getView());
                    break;
                case ON_FAILURE:
                    mView.retry(mView.getView());
                    break;
                default:
                    super.handleMessage(msg);
                    break;
            }
        }
    };

    public NewsDetailPresenter(NewsDetailContract.View view) {
        mView = view;
    }

    @Override
    public void start() {

    }

    @Override
    public void loadData(final String url) {
        boolean isNetWorkConnected = NetUtils.isNetworkConnected(mView.getView().getContext());
        if (!isNetWorkConnected) {
            ToastUtils.show("网络不可用，请检查网络后再试。", mView.getView().getContext());
            mView.retry(mView.getView());
            return;
        }

        new Thread() {
            @Override
            public void run() {
                try {
                    String str = OkHttpUtils.getResponse(url);
                    Gson gson = new Gson();
                    NewsWrapper nw = gson.fromJson(str, NewsWrapper.class);
                    List<NewsModel> list = nw.T1348647909107;
                    mView.setList(list);
                    if (list.size() > 0)
                        mHandler.sendEmptyMessage(ON_SUCCESS);
                    else
                        mHandler.sendEmptyMessage(ON_FAILURE);

                } catch (Exception e) {
                }
            }
        }.start();
    }
}
