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
import com.wcc.swen.utils.ToastUtils;
import com.wcc.swen.utils.Url;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

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

    @BindView(R.id.btn_hint_retry)
    Button btn_hint_retry;
    @BindView(R.id.pb_fragment_news_detail)
    ProgressBar pb;
    @BindView(R.id.rv_news_detail)
    RecyclerView rv_news_detail;
    private View view;

    // 请求数据起始标识
    private int page = 0;
    @BindView(R.id.srl_news_detail)
    SwipeRefreshLayout srl_news_detail;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHint = getArguments().getString("hint");
    }

    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_news_detail, container, false);
        unbinder = ButterKnife.bind(this, view);


        // 创建presenter
        mPresenter = new NewsDetailPresenter(this);
        mPresenter.loadData(getUrl(mHint), mHint);

        return view;
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }

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

    public void resetFragmentData(String hint) {
        this.mHint = hint;
    }

    @NonNull
    private String getUrl(String hint) {
        switch (hint) {
            case "头条":
                return Url.NEWS_DETAIL + Url.HEADLINE_TYPE + Url.HEADLINE_ID + page + "-" + (page + 10) + ".html";
            case "体育":
                return generateUrl(Url.SPORTS_ID);
            case "足球":
                return generateUrl(Url.FOOTBALL_ID);
            case "娱乐":
                return generateUrl(Url.ENTERTAINMENT_ID);
            case "财经":
                return generateUrl(Url.FINANCE_ID);
            case "科技":
                return generateUrl(Url.TECH_ID);
            case "电影":
                return generateUrl(Url.MOVIE_ID);
            case "汽车":
                return generateUrl(Url.CAR_ID);
            case "笑话":
                return generateUrl(Url.JOKE_ID);
            case "游戏":
                return generateUrl(Url.GAME_ID);
            case "时尚":
                return generateUrl(Url.FASHION_ID);
            case "情感":
                return generateUrl(Url.EMOTION_ID);
            case "精选":
                return generateUrl(Url.CHOICE_ID);
            case "电台":
                return generateUrl(Url.RADIO_ID);
            case "NBA":
                return generateUrl(Url.NBA_ID);
            case "数码":
                return generateUrl(Url.DIGITAL_ID);
            case "移动":
                return generateUrl(Url.MOBILE_ID);
            case "彩票":
                return generateUrl(Url.LOTTERY_ID);
            case "教育":
                return generateUrl(Url.EDUCATION_ID);
            case "论坛":
                return generateUrl(Url.FORUM_ID);
            case "旅游":
                return generateUrl(Url.TOUR_ID);
            case "手机":
                return generateUrl(Url.PHONE_ID);
            case "博客":
                return generateUrl(Url.BLOG_ID);
            case "社会":
                return generateUrl(Url.SOCIETY_ID);
            case "家居":
                return generateUrl(Url.FURNISHING_ID);
            case "暴雪游戏":
                return generateUrl(Url.BLIZZARD_ID);
            case "亲子":
                return generateUrl(Url.PATERNITY_ID);
            case "CBA":
                return generateUrl(Url.CBA_ID);
            case "消息":
                return generateUrl(Url.MSG_ID);
            case "军事":
                return generateUrl(Url.MILITARY_ID);
        }
        return "";
    }

    @NonNull
    private String generateUrl(String id) {
        return Url.PRE_NEWS + id + page + "-" + (page + 10) + ".html";
    }

    @Override
    public void setList(List<NewsModel> list) {

        nmList = list;
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
                new Handler().postDelayed(() -> mPresenter.loadData(getUrl(mHint), mHint), 1000);
            }
        });
    }

    @Override
    public void showView() {
        // 隐藏ProgressBar
        pb.setVisibility(View.GONE);

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

        // 显示
        rv_news_detail.setVisibility(View.VISIBLE);
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayout.VERTICAL, false);
        rv_news_detail.setLayoutManager(linearLayoutManager);
        rv_news_detail.setItemAnimator(new DefaultItemAnimator());

        adapter = new NewsDetailAdapter(getActivity(), nmList);


        if (nmList.get(0).ads != null && nmList.get(0).ads.size() > 0) {
            // 实现轮播效果
            setHeader(rv_news_detail);
            rollPagerView.setAdapter(new LoopAdapter(rollPagerView, nmList.get(0).ads));
            rollPagerView.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    final NewsModel newsModel = nmList.get(0);
                    List<Ads> ads = newsModel.ads;
                    Ads ad = ads.get(position);
                    if (ad.url.contains("|"))
                        toImageNewsActivity(ad.url, ad.title);
                    else
                        toWebNewsActivity(ad.url);
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
        Intent intent = new Intent(getActivity(), ImageNewsActivity.class);
        intent.putExtra("url", u);
        intent.putExtra("title", t);
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
            if (nm.photosetID != null && !nm.photosetID.equals(""))
                // 如果是图片新闻，则跳转到图片新闻详情
                toImageNewsActivity(nm.photosetID, nm.title);
            else
                toWebNewsActivity(nm.docid);
        } else {
            // 如果是网页新闻，则用WebView加载网页
            toWebNewsActivity(nm.docid);
        }
    }

    private void toWebNewsActivity(String docid) {
        Intent intent = new Intent(getActivity(), WebNewsActivity.class);
        intent.putExtra("url", Url.TOUCH_HEAD + docid + Url.TOUCH_END);
        startActivity(intent);
    }

}
