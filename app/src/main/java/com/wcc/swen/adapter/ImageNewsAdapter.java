package com.wcc.swen.adapter;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.wcc.swen.model.Photo;

import java.util.List;

/**
 * Created by WangChenchen on 2016/8/23.
 */
public class ImageNewsAdapter extends RecyclerView.Adapter<ImageNewsAdapter.ViewHolder> {

    private Context mContext;
    private List<Photo> mList;
    private OnPageChangeListener listener;
    private int currPos = 0;

    public ImageNewsAdapter(Context context, List<Photo> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public ImageNewsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ImageView image = new ImageView(mContext);
        image.setScaleType(ImageView.ScaleType.CENTER_CROP);
        image.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        return new ViewHolder(image);
    }

    @Override
    public void onBindViewHolder(ImageNewsAdapter.ViewHolder holder, int position) {
        if (listener != null)
            listener.onPageChange(position);
        Glide.with(mContext).load(mList.get(position).imgurl).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void setPageChangeListener(OnPageChangeListener listener) {
        this.listener = listener;
    }

    public interface OnPageChangeListener {
        void onPageChange(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView image;

        public ViewHolder(View itemView) {
            super(itemView);

            image = (ImageView) itemView;
        }
    }
}
