package com.wcc.swen.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.wcc.swen.view.NewsDetailFragment;

/**
 * Created by WangChenchen on 2016/8/18.
 * 新闻页ViewPager适配器
 */
public class NewsDetailAdapter extends FragmentPagerAdapter {

    final int page_count = 4;
    private String[] tabs = {"头条", "体育", "电影", "生活"};
    private Context mContext;

    public NewsDetailAdapter(FragmentManager fm, Context context) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        return NewsDetailFragment.newInstance(tabs[position]);
    }

    @Override
    public int getCount() {
        return tabs.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabs[position];
    }
}
