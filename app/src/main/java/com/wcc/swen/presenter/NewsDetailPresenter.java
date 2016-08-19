package com.wcc.swen.presenter;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.google.gson.Gson;
import com.wcc.swen.contract.NewsDetailContract;
import com.wcc.swen.model.NewsModel;
import com.wcc.swen.utils.OkHttpUtils;

import java.util.List;

/**
 * Created by WangChenchen on 2016/8/19.
 */
public class NewsDetailPresenter implements NewsDetailContract.Presenter {

    private NewsDetailContract.View mView;
    Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    mView.showView(mView.getView());
                    break;
                case 1:
                    mView.retry(mView.getView());
                    break;
            }
            super.handleMessage(msg);
        }
    };

    public NewsDetailPresenter(NewsDetailContract.View view) {
        mView = view;
    }

    @Override
    public void start() {

    }

    @Override
    public void loadData(final String url, final List<NewsModel> list) {
        new Thread() {
            @Override
            public void run() {
                try {
//                    Thread.sleep(2000);
                    String str = OkHttpUtils.getResponse(url);
                    Gson gson = new Gson();

                    // TODO
                    if (list.size() > 0)
                        mHandler.sendEmptyMessage(0);
                    else
                        mHandler.sendEmptyMessage(1);
                } catch (Exception e) {
                }
            }
        }.start();

    }
}
