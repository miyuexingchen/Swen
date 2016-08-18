package com.wcc.swen.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.LoopPagerAdapter;
import com.wcc.swen.R;

/**
 * Created by WangChenchen on 2016/8/18.
 * 图片无限轮播的RollPagerView适配器
 */
public class LoopAdapter extends LoopPagerAdapter {

    private int imgs[] = {
            R.mipmap.i1,
            R.mipmap.i2,
            R.mipmap.i3,
            R.mipmap.i4
    };

    public LoopAdapter(RollPagerView viewPager) {
        super(viewPager);
    }

    @Override
    public View getView(ViewGroup container, int position) {

        ImageView imageView = new ImageView(container.getContext());
        imageView.setImageResource(imgs[position]);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        return imageView;
    }

    @Override
    public int getRealCount() {
        return imgs.length;
    }
}
