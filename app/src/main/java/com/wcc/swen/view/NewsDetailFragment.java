package com.wcc.swen.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
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
import com.wcc.swen.utils.ToastUtils;
import com.wcc.swen.utils.Url;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WangChenchen on 2016/8/18.
 */
public class NewsDetailFragment extends Fragment implements NewsDetailAdapter.OnItemClickListener, NewsDetailContract.View<NewsModel> {

    public final int ON_REFRESH_SUCCESS = 0;
    public final int ON_REFRESH_FAILURE = 1;
    public final int ON_LOAD_MORE_SUCCESS = 2;
    public final int ON_LOAD_MORE_FAILURE = 3;
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
    private SwipeRefreshLayout srl_news_detail;
    public Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case ON_REFRESH_SUCCESS:

                    adapter.changeList(nmList);
                    ToastUtils.show("共更新了" + nmList.size() + "条数据", NewsDetailFragment.this.getContext());
                    nmList = adapter.getmList();
                    srl_news_detail.setRefreshing(false);
                    break;
                case ON_REFRESH_FAILURE:
                    srl_news_detail.setRefreshing(false);
                    ToastUtils.show("刷新失败", NewsDetailFragment.this.getContext());
                    break;
                case ON_LOAD_MORE_SUCCESS:
                    ToastUtils.show("共加载了" + nmList.size() + "条数据", NewsDetailFragment.this.getContext());
                    adapter.addAll(nmList);
                    nmList = adapter.getmList();
                    adapter.changeLoadStatus(NewsDetailAdapter.LOAD_MORE);
                    break;
                case ON_LOAD_MORE_FAILURE:
                    adapter.changeLoadStatus(NewsDetailAdapter.LOAD_MORE);
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    };
    private LinearLayoutManager linearLayoutManager;
    private int lastVisibleItem = 0;

    public static NewsDetailFragment newInstance(String hint) {
        Bundle data = new Bundle();
        data.putString("hint", hint);
        NewsDetailFragment fragment = new NewsDetailFragment();
        fragment.setArguments(data);
        return fragment;
    }

    @NonNull
    private String getUrl(String hint) {
        switch (hint) {
            case "头条":
                return Url.NEWS_DETAIL + Url.HEADLINE_TYPE + Url.HEADLINE_ID + page + "-" + (page + 10) + ".html";
            case "体育":
                return Url.PRE_NEWS + Url.SPORTS_ID + page + "-" + (page + 10) + ".html";
        }
        return "";
    }

    @Override
    public void setList(List<NewsModel> list) {

        nmList = list;
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


        // 创建presenter
        mPresenter = new NewsDetailPresenter(this);
        mPresenter.loadData(getUrl(mHint), mHint);

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
                mPresenter.loadData(getUrl(mHint), mHint);
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
                page = 0;
                mPresenter.loadRefreshData(getUrl(mHint), mHint);
            }
        });
        // 获取recyclerview
        RecyclerView rv_news_detail = (RecyclerView) view.findViewById(R.id.rv_news_detail);
        // 显示
        rv_news_detail.setVisibility(View.VISIBLE);
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayout.VERTICAL, false);
        rv_news_detail.setLayoutManager(linearLayoutManager);
        rv_news_detail.setItemAnimator(new DefaultItemAnimator());

        adapter = new NewsDetailAdapter(getActivity(), nmList);


        if (nmList.get(0).ads != null && nmList.get(0).ads.size() > 0) {
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

        // 监听RecyclerView滑动状态，如果滑动到最后，实现上拉刷新
        rv_news_detail.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == adapter.getItemCount()) {
                    if (adapter.getItemCount() < 360) {
                        adapter.changeLoadStatus(NewsDetailAdapter.LOADING);
                        page += 10;
                        LogUtils.e("NewsDetailPresenter", getUrl(mHint));
                        mPresenter.loadMoreData(getUrl(mHint), mHint);
                    } else {
                        adapter.changeLoadStatus(NewsDetailAdapter.NO_MORE_DATA);
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
            }
        });
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
        // TODO
        NewsModel nm = nmList.get(position);
        if (nm.imgextra != null && !nm.imgextra.isEmpty()) {
            // 如果是图片新闻，则跳转到图片新闻详情
            toImageNewsActivity(nm.photosetID, nm.title);
        } else {
            // 如果是网页新闻，则用WebView加载网页
            Intent intent = new Intent(getActivity(), WebNewsActivity.class);
            intent.putExtra("url", Url.TOUCH_HEAD + nm.docid + Url.TOUCH_END);
            startActivity(intent);
        }
    }

    @Override
    public void setPresenter(Object presenter) {

    }
}
