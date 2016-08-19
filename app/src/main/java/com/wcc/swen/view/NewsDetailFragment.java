package com.wcc.swen.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.jude.rollviewpager.OnItemClickListener;
import com.jude.rollviewpager.RollPagerView;
import com.wcc.swen.R;
import com.wcc.swen.adapter.LoopAdapter;
import com.wcc.swen.adapter.NewsDetailAdapter;
import com.wcc.swen.utils.LogUtils;

import java.util.Arrays;
import java.util.List;

/**
 * Created by WangChenchen on 2016/8/18.
 */
public class NewsDetailFragment extends Fragment implements NewsDetailAdapter.OnItemClickListener {

    private final String tag = "NewsDetailFragment";
    NewsDetailAdapter adapter;
    RollPagerView rollPagerView;
    private String mHint;
    private RecyclerView rv_news_detail;
    private String[] list = {"纸杯蛋糕（Cupcake）",
            "甜甜圈（Donut）",
            "闪电泡芙（Éclair）",
            "冻酸奶（Froyo）",
            "姜饼（Gingerbread）",
            "蜂巢（Honeycomb）",
            "冰淇淋三明治（Ice Cream Sandwich）",
            "果冻豆（Jelly Bean）",
            "奇巧（KitKat）",
            "棒棒糖（Lollipop）",
            "棉花糖（Marshmallow）。"};

    public static NewsDetailFragment newInstance(String hint) {
        Bundle data = new Bundle();
        data.putString("hint", hint);
        NewsDetailFragment fragment = new NewsDetailFragment();
        fragment.setArguments(data);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHint = getArguments().getString("hint");

        LogUtils.d(tag, "onCreate " + mHint);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        LogUtils.d(tag, "onCreateView " + mHint);
        View view = inflater.inflate(R.layout.fragment_news_detail, container, false);

        // 获取recyclerview
        rv_news_detail = (RecyclerView) view.findViewById(R.id.rv_news_detail);
        rv_news_detail.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayout.VERTICAL, false));
        rv_news_detail.setItemAnimator(new DefaultItemAnimator());
        List<String> sList = Arrays.asList(list);
        adapter = new NewsDetailAdapter(getActivity(), sList);


        if ("头条".equals(mHint)) {
            // 实现轮播效果
            setHeader(rv_news_detail);
            rollPagerView.setAdapter(new LoopAdapter(rollPagerView));
            rollPagerView.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    Toast.makeText(getActivity(), position + " is clicked.", Toast.LENGTH_SHORT).show();
                }
            });
        }
        adapter.setOnItemClickListener(this);
        rv_news_detail.setAdapter(adapter);

        return view;
    }

    private void setHeader(RecyclerView view) {
        rollPagerView = (RollPagerView) LayoutInflater.from(getActivity()).inflate(R.layout.header_news_detail_rv, view, false);
        adapter.setHeaderView(rollPagerView);
    }

    @Override
    public void onItemClick(int position, Object object) {
        Toast.makeText(getActivity(), String.valueOf(object), Toast.LENGTH_SHORT).show();
    }
}
