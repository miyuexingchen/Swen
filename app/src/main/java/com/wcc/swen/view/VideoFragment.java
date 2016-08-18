package com.wcc.swen.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wcc.swen.R;
import com.wcc.swen.utils.LogUtils;

/**
 * Created by Administrator on 2016/8/17.
 */
public class VideoFragment extends Fragment {
    final String tag = "VideoFragment";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LogUtils.e(tag, "VideoFragment.onCreateView");
        return inflater.inflate(R.layout.fragment_video, container, false);
    }
}
