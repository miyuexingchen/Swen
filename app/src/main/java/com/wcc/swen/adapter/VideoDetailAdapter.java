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
import com.wcc.swen.model.VideoWrapper;
import com.wcc.swen.model.VideoWrapper.VideoModel;

import java.util.List;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * Created by WangChenchen on 2016/8/19.
 */
public class VideoDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int LOAD_MORE = 4;
    public static final int LOADING = 5;
    public static final int NO_MORE_DATA = 6;
    public final int NORMAL = 1;
    public final int FOOTER = 3;
    private int load_status = LOAD_MORE;
    private Context mContext;
    // 将RollPagerView作为HeaderView添加给RecyclerView
    private List<VideoWrapper.VideoModel> mList;
    private OnItemClickListener listener;

    public VideoDetailAdapter(Context context, List<VideoWrapper.VideoModel> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount()) return FOOTER;
        return NORMAL;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == FOOTER) {
            TextView view = (TextView) LayoutInflater.from(mContext).inflate(R.layout.layout_footer, parent, false);
            return new ViewHolderFooter(view);
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_video_detail_rv, parent, false);
        return new ViewHolderNormal(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        if (getItemViewType(position) == FOOTER) {
            ViewHolderFooter footerHolder = (ViewHolderFooter) holder;
            switch (load_status) {
                case LOAD_MORE:
                    footerHolder.text.setText("上拉加载更多...");
                    break;
                case LOADING:
                    footerHolder.text.setText("正在加载更多数据...");
                    break;
                case NO_MORE_DATA:
                    footerHolder.text.setText("没有更多数据");
                    break;
            }
            return;
        }


        VideoWrapper.VideoModel currVM = mList.get(position);
        if (getItemViewType(position) == NORMAL) {
            ViewHolderNormal normalHolder = (ViewHolderNormal) holder;
            normalHolder.tv_source_video.setText("来源：" + currVM.topicName);
            normalHolder.tv_length_video.setText("时长：" + (currVM.length / 60) + ":" + (currVM.length % 60));
            normalHolder.tv_play_video.setText("点击：" + currVM.playCount + "次");
            boolean setUp = normalHolder.jcvps.setUp(currVM.mp4_url
                    , JCVideoPlayerStandard.SCREEN_LAYOUT_LIST, currVM.title);
            if (setUp)
                Glide.with(mContext).load(currVM.cover).into(normalHolder.jcvps.thumbImageView);
        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener) {
                    listener.onItemClick(position, mList.get(position));
                }
            }
        });
    }

    // 用新数据替换mList内容
    public void changeList(List<VideoWrapper.VideoModel> newData) {
        mList.clear();
        mList.addAll(newData);
        notifyDataSetChanged();
    }

    public List<VideoWrapper.VideoModel> getmList() {
        return mList;
    }

    // 上拉加载更多数据时调用
    public void addAll(List<VideoWrapper.VideoModel> newData) {
        mList.addAll(newData);
        notifyDataSetChanged();
    }

    // 改变脚布局的文字
    public void changeLoadStatus(int status) {
        load_status = status;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        return mList.size() + 1;
    }

    public interface OnItemClickListener {
        void onItemClick(int position, Object object);
    }

    public class ViewHolderNormal extends RecyclerView.ViewHolder {

        private JCVideoPlayerStandard jcvps;
        private TextView tv_source_video;
        private TextView tv_length_video;
        private TextView tv_play_video;

        public ViewHolderNormal(View itemView) {
            super(itemView);
            jcvps = (JCVideoPlayerStandard) itemView.findViewById(R.id.jcvps);
            tv_source_video = (TextView) itemView.findViewById(R.id.tv_source_video);
            tv_length_video = (TextView) itemView.findViewById(R.id.tv_length_video);
            tv_play_video = (TextView) itemView.findViewById(R.id.tv_play_video);
        }
    }

    public class ViewHolderFooter extends RecyclerView.ViewHolder {
        private TextView text;

        public ViewHolderFooter(View itemView) {
            super(itemView);
            text = (TextView) itemView;
        }
    }
}

