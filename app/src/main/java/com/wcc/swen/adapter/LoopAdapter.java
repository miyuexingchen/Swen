package com.wcc.swen.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
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

    private String urls[] = {
            "http://cms-bucket.nosdn.127.net/797ee1a1d16942c284fbc6059a3344b320160819074356.jpeg",
            "http://cms-bucket.nosdn.127.net/7a1d60ce422d4471afde2e15169915d620160819075007.jpeg",
            "http://cms-bucket.nosdn.127.net/e7d7edc6c07b40378355e4e07fae099120160819075345.jpeg",
            "http://cms-bucket.nosdn.127.net/9c4f83bd0724420fb4a394a1b90a1ace20160819091952.jpeg"
    };

    private String strs[] = {
            "广西持续降雨 民众在被淹绿地拉网捕鱼",
            "印度洪水致多人死亡 逝者在河边被火葬",
            "利比亚与马耳他海域300余名难民获救",
            "叙利亚阿勒颇遭政府军空袭 建筑成废墟"
    };

    public LoopAdapter(RollPagerView viewPager) {
        super(viewPager);
    }

    @Override
    public View getView(final ViewGroup container, final int position) {

        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.layout_rollpagerveiw, container, false);
        final ImageView imageView = (ImageView) view.findViewById(R.id.iv_rpv);
//        imageView.setImageResource(imgs[position]);
        Glide.with(container.getContext()).load(urls[position]).into(imageView);

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
