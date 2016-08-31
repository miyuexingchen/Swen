package com.wcc.swen.presenter;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.Fragment;

import com.google.gson.Gson;
import com.wcc.swen.contract.NewsDetailContract;
import com.wcc.swen.model.NewsModel;
import com.wcc.swen.model.NewsWrapper;
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
                    List<NewsModel> list = getList(url, tab);
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

    private List<NewsModel> getList(String url, String tab) {
        List<NewsModel> list = new ArrayList<>();
        String str = OkHttpUtils.getResponse(url);
        Gson gson = new Gson();
        switch (tab) {
            case "头条":
                NewsWrapper nw = gson.fromJson(str, NewsWrapper.class);
                list = nw.T1348647909107;
                break;
            case "体育":
                NewsWrapper.SportsWrapper sw = gson.fromJson(str, NewsWrapper.SportsWrapper.class);
                list = sw.T1348649079062;
                break;
            case "足球":
                NewsWrapper.FootballWrapper fw = gson.fromJson(str, NewsWrapper.FootballWrapper.class);
                list = fw.T1399700447917;
                break;
            case "娱乐":
                NewsWrapper.EntertainmentWrapper ew = gson.fromJson(str, NewsWrapper.EntertainmentWrapper.class);
                list = ew.T1348648517839;
                break;
            case "财经":
                NewsWrapper.FinanceWrapper fiw = gson.fromJson(str, NewsWrapper.FinanceWrapper.class);
                list = fiw.T1348648756099;
                break;
            case "科技":
                NewsWrapper.TechWrapper tw = gson.fromJson(str, NewsWrapper.TechWrapper.class);
                list = tw.T1348649580692;
                break;
            case "电影":
                NewsWrapper.MovieWrapper mw = gson.fromJson(str, NewsWrapper.MovieWrapper.class);
                list = mw.T1348648650048;
                break;
            case "汽车":
                NewsWrapper.CarWrapper cw = gson.fromJson(str, NewsWrapper.CarWrapper.class);
                list = cw.T1348654060988;
                break;
            case "笑话":
                NewsWrapper.JokeWrapper jw = gson.fromJson(str, NewsWrapper.JokeWrapper.class);
                list = jw.T1350383429665;
                break;
            case "游戏":
                NewsWrapper.GameWrapper gw = gson.fromJson(str, NewsWrapper.GameWrapper.class);
                list = gw.T1348654151579;
                break;
            case "时尚":
                NewsWrapper.FashionWrapper faw = gson.fromJson(str, NewsWrapper.FashionWrapper.class);
                list = faw.T1348650593803;
                break;
            case "精选":
                NewsWrapper.ChoiceWrapper chw = gson.fromJson(str, NewsWrapper.ChoiceWrapper.class);
                list = chw.T1370583240249;
                break;
            case "情感":
                NewsWrapper.EmotionWrapper emw = gson.fromJson(str, NewsWrapper.EmotionWrapper.class);
                list = emw.T1348650839000;
                break;
            case "电台":
                NewsWrapper.RadioWrapper rw = gson.fromJson(str, NewsWrapper.RadioWrapper.class);
                list = rw.T1379038288239;
                break;
            case "NBA":
                NewsWrapper.NbaWrapper nbw = gson.fromJson(str, NewsWrapper.NbaWrapper.class);
                list = nbw.T1348649145984;
                break;
            case "数码":
                NewsWrapper.DigitalWrapper dw = gson.fromJson(str, NewsWrapper.DigitalWrapper.class);
                list = dw.T1348649776727;
                break;
            case "移动":
                NewsWrapper.MobileWrapper mow = gson.fromJson(str, NewsWrapper.MobileWrapper.class);
                list = mow.T1351233117091;
                break;
            case "彩票":
                NewsWrapper.LotteryWrapper lw = gson.fromJson(str, NewsWrapper.LotteryWrapper.class);
                list = lw.T1356600029035;
                break;
            case "教育":
                NewsWrapper.EducationWrapper edw = gson.fromJson(str, NewsWrapper.EducationWrapper.class);
                list = edw.T1348654225495;
                break;
            case "论坛":
                NewsWrapper.ForumWrapper fow = gson.fromJson(str, NewsWrapper.ForumWrapper.class);
                list = fow.T1349837670307;
                break;
            case "旅游":
                NewsWrapper.TourWrapper tow = gson.fromJson(str, NewsWrapper.TourWrapper.class);
                list = tow.T1348654204705;
                break;
            case "手机":
                NewsWrapper.PhoneWrapper pw = gson.fromJson(str, NewsWrapper.PhoneWrapper.class);
                list = pw.T1348649654285;
                break;
            case "博客":
                NewsWrapper.BlogWrapper bw = gson.fromJson(str, NewsWrapper.BlogWrapper.class);
                list = bw.T1349837698345;
                break;
            case "社会":
                NewsWrapper.SocietyWrapper sow = gson.fromJson(str, NewsWrapper.SocietyWrapper.class);
                list = sow.T1348648037603;
                break;
            case "家居":
                NewsWrapper.FurnishWrapper fuw = gson.fromJson(str, NewsWrapper.FurnishWrapper.class);
                list = fuw.T1348654105308;
                break;
            case "暴雪游戏":
                NewsWrapper.BlizzardWrapper blw = gson.fromJson(str, NewsWrapper.BlizzardWrapper.class);
                list = blw.T1397016069906;
                break;
            case "亲子":
                NewsWrapper.PaternityWrapper paw = gson.fromJson(str, NewsWrapper.PaternityWrapper.class);
                list = paw.T1397116135282;
                break;
            case "CBA":
                NewsWrapper.CbaWrapper cbw = gson.fromJson(str, NewsWrapper.CbaWrapper.class);
                list = cbw.T1348649475931;
                break;
            case "消息":
                NewsWrapper.MsgWrapper msw = gson.fromJson(str, NewsWrapper.MsgWrapper.class);
                list = msw.T1371543208049;
                break;
            case "军事":
                NewsWrapper.MilitaryWrapper miw = gson.fromJson(str, NewsWrapper.MilitaryWrapper.class);
                list = miw.T1348648141035;
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
                List<NewsModel> list = getList(url, tab);
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

        int connectedType = NetUtils.getConnectedType(((Fragment) mView).getActivity());
        if (connectedType == 0) {
            ToastUtils.show("当前网络为移动网络，请到wifi环境下重试。", (((Fragment) mView).getActivity()));
            mView.retry();
            return;
        }

        new Thread() {
            @Override
            public void run() {
                List<NewsModel> list = getList(url, tab);
                if (list.size() > 0) {
                    mView.setList(list);
                    ((NewsDetailFragment) mView).mHandler.sendEmptyMessage(((NewsDetailFragment) mView).ON_LOAD_MORE_SUCCESS);
                } else
                    ((NewsDetailFragment) mView).mHandler.sendEmptyMessage(((NewsDetailFragment) mView).ON_LOAD_MORE_FAILURE);

            }
        }.start();
    }


}
