package com.wcc.swen.presenter;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.google.gson.Gson;
import com.wcc.swen.contract.NewsDetailContract;
import com.wcc.swen.model.NewsModel;
import com.wcc.swen.model.NewsWrapper;
import com.wcc.swen.utils.LogUtils;
import com.wcc.swen.utils.OkHttpUtils;

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

        new Thread() {
            @Override
            public void run() {
                try {
                    String str = OkHttpUtils.getResponse(url);
//                    LogUtils.e(tag, sb.toString());
                    Gson gson = new Gson();

                    LogUtils.d(tag, "gson start");
                    NewsWrapper nw = gson.fromJson(str, NewsWrapper.class);
                    LogUtils.d(tag, "gson end");
                    List<NewsModel> list = nw.T1348647909107;
                    mView.setList(list);
                    LogUtils.d(tag, list.size() + "");
                    // TODO
                    if (list.size() > 0)
                        mHandler.sendEmptyMessage(0);
                    else
                        mHandler.sendEmptyMessage(1);

//                    Thread.sleep(2000);
                } catch (Exception e) {
                }
            }
        }.start();
    }
}
