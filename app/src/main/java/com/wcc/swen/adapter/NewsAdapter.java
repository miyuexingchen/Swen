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
public class NewsAdapter extends FragmentPagerAdapter {

    private String[] tabs = {"头条", "体育", "娱乐", "财经", "科技", "电影", "汽车", "笑话", "游戏", "足球"
            , "时尚", "情感", "精选", "电台"
            , "NBA", "数码", "移动", "彩票"
            , "教育", "论坛", "旅游", "手机"
            , "博客", "社会", "家居", "暴雪游戏"
            , "亲子", "CBA", "消息", "军事"};

    private Context mContext;

    public NewsAdapter(FragmentManager fm, Context context) {
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
