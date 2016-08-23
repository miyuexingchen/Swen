package com.wcc.swen.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.jude.rollviewpager.OnItemClickListener;
import com.jude.rollviewpager.RollPagerView;
import com.wcc.swen.R;
import com.wcc.swen.activity.ImageNewsActivity;
import com.wcc.swen.activity.WebNewsActivity;
import com.wcc.swen.adapter.LoopAdapter;
import com.wcc.swen.adapter.NewsDetailAdapter;
import com.wcc.swen.contract.NewsDetailContract;
import com.wcc.swen.model.Ads;
import com.wcc.swen.model.NewsModel;
import com.wcc.swen.presenter.NewsDetailPresenter;
import com.wcc.swen.utils.LogUtils;
import com.wcc.swen.utils.Url;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WangChenchen on 2016/8/18.
 */
public class NewsDetailFragment extends Fragment implements NewsDetailAdapter.OnItemClickListener, NewsDetailContract.View<NewsModel> {

    public final int ON_REFRESH_SUCCESS = 0;
    public final int ON_REFRESH_FAILURE = 1;
    private String mHint;
    private List<NewsModel> nmList = new ArrayList<>();
    private NewsDetailPresenter mPresenter;
    private NewsDetailAdapter adapter;
    private RollPagerView rollPagerView;
    private Button btn_hint_retry;
    private ProgressBar pb;
    private View view;
    // 请求数据起始标识
    private int page = 0;
    String url = Url.NEWS_DETAIL + Url.HEADLINE_TYPE + Url.HEADLINE_ID + page + "-" + (page + 10) + ".html";
    private SwipeRefreshLayout srl_news_detail;
    public Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case ON_REFRESH_SUCCESS:
                    adapter.notifyDataSetChanged();
                    srl_news_detail.setRefreshing(false);
                    break;
                case ON_REFRESH_FAILURE:
                    srl_news_detail.setRefreshing(false);
                    // TODO
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    };

    public static NewsDetailFragment newInstance(String hint) {
        Bundle data = new Bundle();
        data.putString("hint", hint);
        NewsDetailFragment fragment = new NewsDetailFragment();
        fragment.setArguments(data);
        return fragment;
    }

    @Override
    public void setList(List<NewsModel> list) {
        this.nmList = list;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHint = getArguments().getString("hint");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_news_detail, container, false);
        pb = (ProgressBar) view.findViewById(R.id.pb_fragment_news_detail);
        btn_hint_retry = (Button) view.findViewById(R.id.btn_hint_retry);

        if (mHint.equals("头条")) {
            // 创建presenter
            mPresenter = new NewsDetailPresenter(this);
            mPresenter.loadData(url);
        }

        return view;
    }

    @Override
    public void retry() {
        // 隐藏进度条
        pb.setVisibility(View.GONE);
        // 显示重试按钮
        btn_hint_retry.setVisibility(View.VISIBLE);
        btn_hint_retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pb.setVisibility(View.VISIBLE);
                btn_hint_retry.setVisibility(View.GONE);
                mPresenter.loadData(url);
            }
        });
    }

    @Override
    public void showView() {
        // 隐藏ProgressBar
        pb.setVisibility(View.GONE);

        // 获取SwipeRefreshLayout
        srl_news_detail = (SwipeRefreshLayout) view.findViewById(R.id.srl_news_detail);
        srl_news_detail.setVisibility(View.VISIBLE);
        srl_news_detail.setProgressBackgroundColorSchemeResource(android.R.color.white);
        srl_news_detail.setColorSchemeResources(android.R.color.holo_blue_light,
                android.R.color.holo_red_light, android.R.color.holo_orange_light,
                android.R.color.holo_green_light);
        srl_news_detail.setProgressViewOffset(false, 0, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
                        .getDisplayMetrics()));
        srl_news_detail.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.loadRefreshData(url);
                // 加载成功才能刷新
//                adapter.notifyDataSetChanged();
            }
        });
        // 获取recyclerview
        RecyclerView rv_news_detail = (RecyclerView) view.findViewById(R.id.rv_news_detail);
        // 显示
        rv_news_detail.setVisibility(View.VISIBLE);
        rv_news_detail.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayout.VERTICAL, false));
        rv_news_detail.setItemAnimator(new DefaultItemAnimator());

        adapter = new NewsDetailAdapter(getActivity(), nmList);

        if ("头条".equals(mHint)) {
            // 实现轮播效果
            setHeader(rv_news_detail);
            rollPagerView.setAdapter(new LoopAdapter(rollPagerView, nmList.get(0)));
            rollPagerView.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    final NewsModel newsModel = nmList.get(0);
                    List<Ads> ads = newsModel.ads;
                    Ads ad = ads.get(position);
                    toImageNewsActivity(ad.url, ad.title);
                }
            });


        }
        adapter.setOnItemClickListener(this);
        rv_news_detail.setAdapter(adapter);
    }

    // 添加参数跳转到图片新闻详情
    private void toImageNewsActivity(String u, String t) {
        String url = u;
        String title = t;
        Intent intent = new Intent(getActivity(), ImageNewsActivity.class);
        intent.putExtra("url", url);
        intent.putExtra("title", title);
        startActivity(intent);
    }

    private void setHeader(RecyclerView view) {
        rollPagerView = (RollPagerView) LayoutInflater.from(getActivity()).inflate(R.layout.header_news_detail_rv, view, false);
        adapter.setHeaderView(rollPagerView);
    }

    @Override
    public void onItemClick(int position, Object object) {
        NewsModel nm = nmList.get(position);
        if (nm.imgextra != null && !nm.imgextra.isEmpty()) {
            // 如果是图片新闻，则跳转到图片新闻详情
            toImageNewsActivity(nm.photosetID, nm.title);
        } else {
            // 如果是网页新闻，则用WebView加载网页
            LogUtils.e("NewsDetailPresenter", nm.docid);
            Intent intent = new Intent(getActivity(), WebNewsActivity.class);
            intent.putExtra("url", Url.TOUCH_HEAD + nm.docid + Url.TOUCH_END);
            startActivity(intent);
        }
    }

    @Override
    public void setPresenter(Object presenter) {

    }
}
