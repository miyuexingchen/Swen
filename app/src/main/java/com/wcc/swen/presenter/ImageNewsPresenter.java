package com.wcc.swen.presenter;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.google.gson.Gson;
import com.wcc.swen.contract.NewsDetailContract;
import com.wcc.swen.model.ImageNewsModel;
import com.wcc.swen.model.Photo;
import com.wcc.swen.utils.NetUtils;
import com.wcc.swen.utils.OkHttpUtils;
import com.wcc.swen.utils.ToastUtils;

import java.util.List;

/**
 * Created by WangChenchen on 2016/8/23.
 */
public class ImageNewsPresenter implements NewsDetailContract.Presenter {

    private static final int ON_SUCCESS = 0;
    private static final int ON_FAILURE = 1;
    private NewsDetailContract.View mView;
    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case ON_SUCCESS:
                    mView.showView();
                    break;
                case ON_FAILURE:
                    mView.retry();
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    };

    public ImageNewsPresenter(NewsDetailContract.View view) {
        mView = view;
    }

    @Override
    public void loadData(final String url, String tab) {

        boolean isNetWorkAccessed = NetUtils.isNetworkConnected(((Activity) mView));
        if (!isNetWorkAccessed) {
            ToastUtils.show("网络不可用，请检查网络后再试。", ((Activity) mView));
            mView.retry();
            return;
        }

        int connectedType = NetUtils.getConnectedType((Activity) mView);
        if (connectedType == 0) {
            ToastUtils.show("当前网络为移动网络，请到wifi环境下重试。", ((Activity) mView));
            mView.retry();
            return;
        }
        new Thread() {
            @Override
            public void run() {
                String str = OkHttpUtils.getResponse(url);
                Gson gson = new Gson();
                ImageNewsModel inm = gson.fromJson(str, ImageNewsModel.class);
                List<Photo> list = inm.photos;
                if (list.size() > 0) {
                    mView.setList(list);
                    mHandler.sendEmptyMessage(ON_SUCCESS);
                }
                else
                    mHandler.sendEmptyMessage(ON_FAILURE);
            }
        }.start();
    }

    @Override
    public void loadRefreshData(final String url, String tab) {

    }

    @Override
    public void loadMoreData(String url, String tab) {

    }

}
