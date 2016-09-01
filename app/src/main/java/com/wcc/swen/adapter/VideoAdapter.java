package com.wcc.swen.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;
import com.wcc.swen.view.VideoDetailFragment;

import java.util.List;

/**
 * Created by WangChenchen on 2016/8/18.
 * 新闻页ViewPager适配器
 */
public class VideoAdapter extends FragmentPagerAdapter {

    private List<String> tabs;

    public VideoAdapter(FragmentManager fm, List<String> tabs) {
        super(fm);
        this.tabs = tabs;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    public Object instantiateItem(ViewGroup container, int position) {
        VideoDetailFragment f = (VideoDetailFragment) super.instantiateItem(container, position);
        if (tabs != null && position >= 0 && position < tabs.size()) {
            String mHint = tabs.get(position);
            f.resetFragmentData(mHint);
        }

        return f;
    }

    @Override
    public Fragment getItem(int position) {
        return VideoDetailFragment.newInstance(tabs.get(position));
    }

    @Override
    public int getCount() {
        return tabs.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabs.get(position);
    }
}
