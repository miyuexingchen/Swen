package com.wcc.swen.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.wcc.swen.model.Photo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WangChenchen on 2016/8/23.
 */
public class ImageNewsAdapter extends PagerAdapter {

    private Context mContext;
    private List<Photo> mList;
    private List<ImageView> list = new ArrayList<>();

    public ImageNewsAdapter(Context context, List<Photo> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ImageView view = (ImageView) object;
        container.removeView(view);
        list.add(view);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView view = null;
        if (list.isEmpty()) {
            view = new ImageView(mContext);
            view.setScaleType(ImageView.ScaleType.CENTER_CROP);
        } else {
            view = list.remove(0);
        }
        Glide.with(mContext).load(mList.get(position).imgurl).into(view);
        container.addView(view);
        return view;
    }
}
