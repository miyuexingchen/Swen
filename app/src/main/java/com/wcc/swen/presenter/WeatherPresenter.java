package com.wcc.swen.presenter;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.Fragment;

import com.google.gson.Gson;
import com.wcc.swen.contract.WeatherContract;
import com.wcc.swen.model.WeatherWrapper;
import com.wcc.swen.utils.NetUtils;
import com.wcc.swen.utils.OkHttpUtils;
import com.wcc.swen.utils.ToastUtils;
import com.wcc.swen.view.WeatherFragment;

/**
 * Created by Administrator on 2016/8/17.
 */
public class WeatherPresenter implements WeatherContract.Presenter {

    private static final int ON_SUCCESS = 0;
    private static final int ON_FAILURE = 1;
    private WeatherContract.View mView;
    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case ON_SUCCESS:
                    mView.refreshUI();
                    break;
                case ON_FAILURE:
                    // TODO
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    };

    public WeatherPresenter(WeatherContract.View view) {
        mView = view;
    }

    @Override
    public void start() {

    }

    @Override
    public void loadData(final String url) {
        boolean isNetWorkAccessed = NetUtils.isNetworkConnected(((WeatherFragment) mView).getContext());
        if (!isNetWorkAccessed) {
            ToastUtils.show("网络不可用，请检查网络后再试。", ((WeatherFragment) mView).getContext());
            return;
        }

        int connectedType = NetUtils.getConnectedType(((Fragment) mView).getActivity());
        if (connectedType == 0) {
            ToastUtils.show("当前网络为移动网络，请到wifi环境下重试。", (((Fragment) mView).getActivity()));
            return;
        }

        new Thread() {
            @Override
            public void run() {
                String str = OkHttpUtils.getResponse(url);
                Gson gson = new Gson();
                WeatherWrapper ww = gson.fromJson(str, WeatherWrapper.class);

                if (ww != null) {
                    mView.setWrapper(ww);
                    mHandler.sendEmptyMessage(ON_SUCCESS);
                } else
                    mHandler.sendEmptyMessage(ON_FAILURE);
            }
        }.start();
    }
}
