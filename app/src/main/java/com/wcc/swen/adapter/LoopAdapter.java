package com.wcc.swen.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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

    private String strs[] = {
            "东",
            "西",
            "南",
            "北"
    };

    public LoopAdapter(RollPagerView viewPager) {
        super(viewPager);
    }

    @Override
    public View getView(ViewGroup container, int position) {

        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.layout_rollpagerveiw, container, false);
        ImageView imageView = (ImageView) view.findViewById(R.id.iv_rpv);
        imageView.setImageResource(imgs[position]);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        TextView tv_rpv = (TextView) view.findViewById(R.id.tv_rpv);
        tv_rpv.setText(strs[position]);
        return view;
    }

    @Override
    public int getRealCount() {
        return imgs.length;
    }
}
