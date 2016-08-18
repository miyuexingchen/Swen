package com.wcc.swen.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wcc.swen.R;
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
        View v = inflater.inflate(R.layout.fragment_news_detail, container, false);
        TextView tv = (TextView) v.findViewById(R.id.tv_news_detail);
        tv.setText(mHint);

        return v;
    }
}
