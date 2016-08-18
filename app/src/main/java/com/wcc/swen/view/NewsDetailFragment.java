package com.wcc.swen.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.jude.rollviewpager.OnItemClickListener;
import com.jude.rollviewpager.RollPagerView;
import com.wcc.swen.R;
import com.wcc.swen.adapter.LoopAdapter;
import com.wcc.swen.utils.LogUtils;

/**
 * Created by WangChenchen on 2016/8/18.
 */
public class NewsDetailFragment extends Fragment {

    private final String tag = "NewsDetailFragment";

    private String mHint;

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
        TextView tv = (TextView) view.findViewById(R.id.tv_news_detail);
        tv.setText(mHint);

        if ("头条".equals(mHint)) {
            // 实现轮播效果
            RollPagerView rpv = (RollPagerView) view.findViewById(R.id.rpv_news_detail);
            rpv.setVisibility(View.VISIBLE);
            rpv.setAdapter(new LoopAdapter(rpv));
            rpv.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    Toast.makeText(getActivity(), position + " is clicked.", Toast.LENGTH_SHORT).show();
                }
            });
        }
        return view;
    }
}
