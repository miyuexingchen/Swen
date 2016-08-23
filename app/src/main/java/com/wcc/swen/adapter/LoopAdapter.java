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
import com.wcc.swen.model.Ads;
import com.wcc.swen.model.NewsModel;

import java.util.List;

/**
 * Created by WangChenchen on 2016/8/18.
 * 图片无限轮播的RollPagerView适配器
 */
public class LoopAdapter extends LoopPagerAdapter {

    private NewsModel nm;
    private List<Ads> adsList;

    public LoopAdapter(RollPagerView viewPager, NewsModel nm) {
        super(viewPager);
        this.nm = nm;
        adsList = nm.ads;
    }

    @Override
    public View getView(final ViewGroup container, final int position) {

        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.layout_rollpagerveiw, container, false);
        final ImageView imageView = (ImageView) view.findViewById(R.id.iv_rpv);
//        imageView.setImageResource(imgs[position]);
        Glide.with(container.getContext()).load(adsList.get(position).imgsrc).into(imageView);

        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        TextView tv_rpv = (TextView) view.findViewById(R.id.tv_rpv);
        tv_rpv.setText(adsList.get(position).title);
        return view;
    }

    @Override
    public int getRealCount() {
        return adsList.size();
    }
}
