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
import com.wcc.swen.adapter.VideoAdapter;
import com.wcc.swen.utils.LogUtils;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2016/8/17.
 */
public class VideoFragment extends Fragment {

    private String tabs[] = {"热点", "娱乐", "搞笑"};

    private List<String> myChannels;
    private TabLayout tab;
    private ViewPager vp;
    private VideoAdapter vpAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_video, container, false);

        myChannels = Arrays.asList(tabs);

        tab = (TabLayout) view.findViewById(R.id.tab_video);
        vp = (ViewPager) view.findViewById(R.id.vp_video_main);

        vpAdapter = new VideoAdapter(getChildFragmentManager(), getActivity(), myChannels);
        vp.setAdapter(vpAdapter);
        vp.setOffscreenPageLimit(1);
        tab.setupWithViewPager(vp);
        tab.setTabMode(TabLayout.MODE_FIXED);

        return view;
    }
}
