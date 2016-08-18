package com.wcc.swen.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wcc.swen.R;
import com.wcc.swen.adapter.NewsDetailAdapter;

/**
 * Created by Administrator on 2016/8/17.
 */
public class NewsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_news, container, false);

        TabLayout tab = (TabLayout) view.findViewById(R.id.tab);
        ViewPager vp = (ViewPager) view.findViewById(R.id.vp_news_main);
        vp.setAdapter(new NewsDetailAdapter(getFragmentManager(), getActivity()));
        vp.setOffscreenPageLimit(1);
        tab.setupWithViewPager(vp);
        tab.setTabMode(TabLayout.MODE_FIXED);
        return view;
    }
}
