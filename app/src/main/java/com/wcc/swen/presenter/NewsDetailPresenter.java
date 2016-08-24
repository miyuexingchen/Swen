package com.wcc.swen.presenter;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.Fragment;

import com.google.gson.Gson;
import com.wcc.swen.contract.NewsDetailContract;
import com.wcc.swen.model.NewsModel;
import com.wcc.swen.model.NewsWrapper;
import com.wcc.swen.utils.LogUtils;
import com.wcc.swen.utils.NetUtils;
import com.wcc.swen.utils.OkHttpUtils;
import com.wcc.swen.utils.ToastUtils;
import com.wcc.swen.view.NewsDetailFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WangChenchen on 2016/8/19.
 */
public class NewsDetailPresenter implements NewsDetailContract.Presenter {

    private final String tag = "NewsDetailPresenter";

    private final int ON_SUCCESS = 0;
    private final int ON_FAILURE = 1;

    private NewsDetailContract.View mView;
    Handler mHandler = new Handler(Looper.getMainLooper()) {
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
    public void loadData(final String url, final String tab) {
        boolean isNetWorkConnected = NetUtils.isNetworkConnected(((Fragment) mView).getActivity());
        if (!isNetWorkConnected) {
            ToastUtils.show("网络不可用，请检查网络后再试。", (((Fragment) mView).getActivity()));
            mView.retry();
            return;
        }

        new Thread() {
            @Override
            public void run() {
                try {
                    String str = OkHttpUtils.getResponse(url);
                    Gson gson = new Gson();
                    List<NewsModel> list = new ArrayList<>();
                    switch (tab) {
                        case "头条":
                            NewsWrapper nw = gson.fromJson(str, NewsWrapper.class);
                            list = nw.T1348647909107;
                            break;
                        case "体育":
                            NewsWrapper.SportsWrapper sw = gson.fromJson(str, NewsWrapper.SportsWrapper.class);
                            list = sw.T1348649079062;
                            break;
                    }
                    if (list.size() > 0) {
                        mView.setList(list);
                        mHandler.sendEmptyMessage(ON_SUCCESS);
                    }
                    else
                        mHandler.sendEmptyMessage(ON_FAILURE);

                } catch (Exception e) {
                }
            }
        }.start();
    }

    @Override
    public void loadRefreshData(final String url, final String tab) {
        // 加载数据、解析并给mView的nmList
        boolean isNetWorkAccessed = NetUtils.isNetworkConnected(((Fragment) mView).getActivity());
        if (!isNetWorkAccessed) {
            ToastUtils.show("网络不可用，请检查网络后再试。", (((Fragment) mView).getActivity()));
            return;
        }

        new Thread() {
            @Override
            public void run() {
                String str = OkHttpUtils.getResponse(url);
                Gson gson = new Gson();
                List<NewsModel> list = new ArrayList<>();
                switch (tab) {
                    case "头条":
                        NewsWrapper nw = gson.fromJson(str, NewsWrapper.class);
                        list = nw.T1348647909107;
                        break;
                    case "体育":
                        NewsWrapper.SportsWrapper sw = gson.fromJson(str, NewsWrapper.SportsWrapper.class);
                        list = sw.T1348649079062;
                        break;
                }
                if (list.size() > 0) {
                    mView.setList(list);
                    ((NewsDetailFragment) mView).mHandler.sendEmptyMessage(((NewsDetailFragment) mView).ON_REFRESH_SUCCESS);
                }
                else
                    ((NewsDetailFragment) mView).mHandler.sendEmptyMessage(((NewsDetailFragment) mView).ON_REFRESH_FAILURE);

            }
        }.start();
    }

    @Override
    public void loadMoreData(final String url, final String tab) {
        // 加载数据、解析并给mView的nmList
        boolean isNetWorkAccessed = NetUtils.isNetworkConnected(((Fragment) mView).getActivity());
        if (!isNetWorkAccessed) {
            ToastUtils.show("网络不可用，请检查网络后再试。", (((Fragment) mView).getActivity()));
            return;
        }

        new Thread() {
            @Override
            public void run() {
                String str = OkHttpUtils.getResponse(url);
                LogUtils.e("NewsDetailPresenter", str);
                Gson gson = new Gson();
                List<NewsModel> list = new ArrayList<>();
                switch (tab) {
                    case "头条":
                        NewsWrapper nw = gson.fromJson(str, NewsWrapper.class);
                        list = nw.T1348647909107;
                        break;
                    case "体育":
                        NewsWrapper.SportsWrapper sw = gson.fromJson(str, NewsWrapper.SportsWrapper.class);
                        list = sw.T1348649079062;
                        break;
                }
                if (list.size() > 0) {
                    mView.setList(list);
                    ((NewsDetailFragment) mView).mHandler.sendEmptyMessage(((NewsDetailFragment) mView).ON_LOAD_MORE_SUCCESS);
                } else
                    ((NewsDetailFragment) mView).mHandler.sendEmptyMessage(((NewsDetailFragment) mView).ON_LOAD_MORE_FAILURE);

            }
        }.start();
    }


}
