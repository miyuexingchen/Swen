package com.wcc.swen.view;

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

import com.wcc.swen.R;
import com.wcc.swen.adapter.NewsDetailAdapter;
import com.wcc.swen.adapter.VideoDetailAdapter;
import com.wcc.swen.contract.NewsDetailContract;
import com.wcc.swen.model.VideoWrapper;
import com.wcc.swen.presenter.VideoDetailPresenter;
import com.wcc.swen.utils.ToastUtils;
import com.wcc.swen.utils.Url;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;

/**
 * Created by WangChenchen on 2016/8/31.
 */
public class VideoDetailFragment extends Fragment implements NewsDetailContract.View<VideoWrapper.VideoModel> {

    public final int ON_REFRESH_SUCCESS = 0;
    public final int ON_REFRESH_FAILURE = 1;
    public final int ON_LOAD_MORE_SUCCESS = 2;
    public final int ON_LOAD_MORE_FAILURE = 3;
    private String mHint;
    private List<VideoWrapper.VideoModel> vmList = new ArrayList<>();
    private VideoDetailPresenter mPresenter;
    private VideoDetailAdapter adapter;
    @BindView(R.id.btn_vedio_retry)
    Button btn_video_retry;
    @BindView(R.id.pb_fragment_video_detail)
    ProgressBar pb;
    private View view;
    // 请求数据起始标识
    private int page = 0;
    @BindView(R.id.srl_video_detail)
    SwipeRefreshLayout srl_video_detail;
    @BindView(R.id.rv_video_detail)
    RecyclerView rv_video_detail;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHint = getArguments().getString("hint");
    }

    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_video_detail, container, false);
        unbinder = ButterKnife.bind(this, view);

        // 创建presenter
        mPresenter = new VideoDetailPresenter(this);
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

                    adapter.changeList(vmList);
                    ToastUtils.show("共更新了" + vmList.size() + "条数据", VideoDetailFragment.this.getContext());
                    vmList = adapter.getmList();
                    srl_video_detail.setRefreshing(false);
                    break;
                case ON_REFRESH_FAILURE:
                    srl_video_detail.setRefreshing(false);
                    ToastUtils.show("刷新失败", VideoDetailFragment.this.getContext());
                    break;
                case ON_LOAD_MORE_SUCCESS:
                    ToastUtils.show("共加载了" + vmList.size() + "条数据", VideoDetailFragment.this.getContext());
                    adapter.addAll(vmList);
                    vmList = adapter.getmList();
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

    public static VideoDetailFragment newInstance(String hint) {
        Bundle data = new Bundle();
        data.putString("hint", hint);
        VideoDetailFragment fragment = new VideoDetailFragment();
        fragment.setArguments(data);
        return fragment;
    }

    public void resetFragmentData(String hint) {
        this.mHint = hint;
    }

    @NonNull
    private String getUrl(String hint) {
        switch (hint) {
            case "热点":
                return generateUrl(Url.VIDEO_HOT_ID);
            case "娱乐":
                return generateUrl(Url.VIDEO_ENTERTAINMENT_ID);
            case "搞笑":
                return generateUrl(Url.VIDEO_FUN_ID);
        }
        return "";
    }

    @NonNull
    private String generateUrl(String id) {
        return Url.HOST + Url.Video + id + Url.VIDEO_CENTER + page + "-" + (page + 10) + ".html";
    }

    @Override
    public void setList(List<VideoWrapper.VideoModel> list) {
        vmList = list;
    }

    @Override
    public void retry() {
        // 隐藏进度条
        pb.setVisibility(View.GONE);
        // 显示重试按钮
        btn_video_retry.setVisibility(View.VISIBLE);
        btn_video_retry.setOnClickListener(v -> {
            pb.setVisibility(View.VISIBLE);
            btn_video_retry.setVisibility(View.GONE);
            new Handler().postDelayed(() -> mPresenter.loadData(getUrl(mHint), mHint), 1000);

        });
    }

    @Override
    public void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }

    @Override
    public void showView() {
        // 隐藏ProgressBar
        pb.setVisibility(View.GONE);

        srl_video_detail.setVisibility(View.VISIBLE);
        srl_video_detail.setProgressBackgroundColorSchemeResource(android.R.color.white);
        srl_video_detail.setColorSchemeResources(android.R.color.holo_blue_light,
                android.R.color.holo_red_light, android.R.color.holo_orange_light,
                android.R.color.holo_green_light);
        srl_video_detail.setProgressViewOffset(false, 0, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
                        .getDisplayMetrics()));
        srl_video_detail.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 0;
                mPresenter.loadRefreshData(getUrl(mHint), mHint);
            }
        });
        // 显示
        rv_video_detail.setVisibility(View.VISIBLE);
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayout.VERTICAL, false);
        rv_video_detail.setLayoutManager(linearLayoutManager);
        rv_video_detail.setItemAnimator(new DefaultItemAnimator());

        adapter = new VideoDetailAdapter(getActivity(), vmList);
        rv_video_detail.setAdapter(adapter);

        // 监听RecyclerView滑动状态，如果滑动到最后，实现上拉刷新
        rv_video_detail.addOnScrollListener(new RecyclerView.OnScrollListener() {
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

}
