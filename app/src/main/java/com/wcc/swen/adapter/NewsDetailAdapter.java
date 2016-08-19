package com.wcc.swen.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wcc.swen.R;
import com.wcc.swen.model.NewsModel;
import com.wcc.swen.view.NewsDetailFragment;

import java.util.List;

/**
 * Created by WangChenchen on 2016/8/19.
 */
public class NewsDetailAdapter extends RecyclerView.Adapter<NewsDetailAdapter.ViewHolder> {

    public static final int HEADER = 0;
    public static final int NORMAL = 1;
    public static final int MULTIIMAGE = 2;
    private Context mContext;
    // 将RollPagerView作为HeaderView添加给RecyclerView
    private List<NewsModel> mList;
    private View headerView;
    private OnItemClickListener listener;

    public NewsDetailAdapter(Context context, List<NewsModel> list) {
        mContext = context;
        mList = list;
    }

    public View getHeaderView() {
        return headerView;
    }

    // headerView的getter setter
    public void setHeaderView(View headerView) {
        this.headerView = headerView;
        notifyItemInserted(0);
    }

    @Override
    public int getItemViewType(int position) {
        if (headerView == null) return NORMAL;
        if (position == 0) return HEADER;
        NewsModel currNM = mList.get(position);
        if (currNM.imgextra.size() == 0 || currNM.imgextra == null)
            return NORMAL;
        return MULTIIMAGE;
    }

    @Override
    public NewsDetailAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (headerView != null && viewType == HEADER)
            return new ViewHolder(headerView, viewType);
        if (viewType == NORMAL) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_news_detail_rv, parent, false);
            return new ViewHolder(view, viewType);
        }

        View view = LayoutInflater.from(mContext).inflate(R.layout.item_image_news_detail_rv, parent, false);
        return new ViewHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(NewsDetailAdapter.ViewHolder holder, final int position) {

        if (getItemViewType(position) == HEADER) return;
        final int pos = getRealPosition(holder);

        NewsModel currNM = mList.get(pos);
        int viewType = getItemViewType(pos);

        holder.tv_title.setText(currNM.title);
        holder.tv_author.setText(currNM.source);
        holder.tv_zan.setText(currNM.replyCount);
        Glide.with(mContext).load(currNM.imgsrc).into(holder.iv_item_news_detail_rv);

        if (viewType == MULTIIMAGE) {
            if (currNM.imgextra.size() > 1) {
                Glide.with(mContext).load(currNM.imgextra.get(0)).into(holder.iv_center);
                Glide.with(mContext).load(currNM.imgextra.get(1)).into(holder.iv_right);
            }
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener) {
                    listener.onItemClick(pos, mList.get(pos));
                }
            }
        });
    }

    private int getRealPosition(RecyclerView.ViewHolder holder) {
        int position = holder.getLayoutPosition();
        return headerView == null ? position : position - 1;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        return headerView == null ? mList.size() : mList.size() + 1;
    }

    public interface OnItemClickListener {
        void onItemClick(int position, Object object);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView iv_item_news_detail_rv;
        private ImageView iv_center;
        private ImageView iv_right;
        private TextView tv_title;
        private TextView tv_author;
        private TextView tv_zan;

        public ViewHolder(View itemView, int viewType) {
            super(itemView);

            if (itemView == headerView) return;
            if (viewType == NORMAL) {
                iv_item_news_detail_rv = (ImageView) itemView.findViewById(R.id.iv_item_news_detail_rv);
                tv_title = (TextView) itemView.findViewById(R.id.tv_title_news_detail);
                tv_author = (TextView) itemView.findViewById(R.id.tv_author_news_detail);
                tv_zan = (TextView) itemView.findViewById(R.id.tv_zan_news_detail);
            }

            if (viewType == MULTIIMAGE) {
                iv_item_news_detail_rv = (ImageView) itemView.findViewById(R.id.iv_left_item_image);
                iv_center = (ImageView) itemView.findViewById(R.id.iv_center_item_image);
                iv_right = (ImageView) itemView.findViewById(R.id.iv_right_item_image);
                tv_title = (TextView) itemView.findViewById(R.id.tv_title_item_image);
                tv_author = (TextView) itemView.findViewById(R.id.tv_source_item_image);
                tv_zan = (TextView) itemView.findViewById(R.id.tv_reply_item_image);
            }
        }
    }
}
