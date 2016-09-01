package com.wcc.swen.presenter;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.Fragment;

import com.google.gson.Gson;
import com.wcc.swen.contract.NewsDetailContract;
import com.wcc.swen.model.VideoWrapper;
import com.wcc.swen.utils.NetUtils;
import com.wcc.swen.utils.OkHttpUtils;
import com.wcc.swen.utils.ToastUtils;
import com.wcc.swen.view.VideoDetailFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WangChenchen on 2016/8/19.
 */
public class VideoDetailPresenter implements NewsDetailContract.Presenter {

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

    public VideoDetailPresenter(NewsDetailContract.View view) {
        mView = view;
    }

    @Override
    public void loadData(final String url, final String tab) {
        boolean isNetWorkConnected = NetUtils.isNetworkConnected(((Fragment) mView).getActivity());
        if (!isNetWorkConnected) {
            ToastUtils.show("网络不可用，请检查网络后再试。", (((Fragment) mView).getActivity()));
            mView.retry();
            return;
        }

        int connectedType = NetUtils.getConnectedType(((Fragment) mView).getActivity());
        if (connectedType == 0) {
            ToastUtils.show("当前网络为移动网络，请到wifi环境下重试。", (((Fragment) mView).getActivity()));
            mView.retry();
            return;
        }

        new Thread() {
            @Override
            public void run() {
                try {
                    List<VideoWrapper.VideoModel> list = getList(url, tab);
                    if (list.size() > 0) {
                        mView.setList(list);
                        mHandler.sendEmptyMessage(ON_SUCCESS);
                    } else
                        mHandler.sendEmptyMessage(ON_FAILURE);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private List<VideoWrapper.VideoModel> getList(String url, String tab) {
        List<VideoWrapper.VideoModel> list = new ArrayList<>();
        String str = OkHttpUtils.getResponse(url);
        Gson gson = new Gson();
        switch (tab) {
            case "热点":
                VideoWrapper.HotWrapper hw = gson.fromJson(str, VideoWrapper.HotWrapper.class);
                list = hw.V9LG4B3A0;
                break;
            case "娱乐":
                VideoWrapper.EntertainWrapper ew = gson.fromJson(str, VideoWrapper.EntertainWrapper.class);
                list = ew.V9LG4CHOR;
                break;
            case "搞笑":
                VideoWrapper.FunWrapper fw = gson.fromJson(str, VideoWrapper.FunWrapper.class);
                list = fw.V9LG4E6VR;
                break;
        }
        return list;
    }

    @Override
    public void loadRefreshData(final String url, final String tab) {
        // 加载数据、解析并给mView的nmList
        boolean isNetWorkAccessed = NetUtils.isNetworkConnected(((Fragment) mView).getActivity());
        if (!isNetWorkAccessed) {
            ToastUtils.show("网络不可用，请检查网络后再试。", (((Fragment) mView).getActivity()));
            return;
        }

        int connectedType = NetUtils.getConnectedType(((Fragment) mView).getActivity());
        if (connectedType == 0) {
            ToastUtils.show("当前网络为移动网络，请到wifi环境下重试。", (((Fragment) mView).getActivity()));
            mView.retry();
            return;
        }

        new Thread() {
            @Override
            public void run() {
                List<VideoWrapper.VideoModel> list = getList(url, tab);
                if (list.size() > 0) {
                    mView.setList(list);
                    ((VideoDetailFragment) mView).mHandler.sendEmptyMessage(((VideoDetailFragment) mView).ON_REFRESH_SUCCESS);
                } else
                    ((VideoDetailFragment) mView).mHandler.sendEmptyMessage(((VideoDetailFragment) mView).ON_REFRESH_FAILURE);

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

        int connectedType = NetUtils.getConnectedType(((Fragment) mView).getActivity());
        if (connectedType == 0) {
            ToastUtils.show("当前网络为移动网络，请到wifi环境下重试。", (((Fragment) mView).getActivity()));
            mView.retry();
            return;
        }

        new Thread() {
            @Override
            public void run() {
                List<VideoWrapper.VideoModel> list = getList(url, tab);
                if (list.size() > 0) {
                    mView.setList(list);
                    ((VideoDetailFragment) mView).mHandler.sendEmptyMessage(((VideoDetailFragment) mView).ON_LOAD_MORE_SUCCESS);
                } else
                    ((VideoDetailFragment) mView).mHandler.sendEmptyMessage(((VideoDetailFragment) mView).ON_LOAD_MORE_FAILURE);

            }
        }.start();
    }


}
