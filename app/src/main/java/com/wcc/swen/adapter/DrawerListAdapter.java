package com.wcc.swen.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wcc.swen.R;
import com.wcc.swen.model.ItemModelOfDrawerList;

import java.util.List;

/**
 * Created by WangChenchen on 2016/9/1.
 */
public class DrawerListAdapter extends BaseAdapter {


    private Context mContext;
    private List<ItemModelOfDrawerList> list;

    public DrawerListAdapter(Context context, List<ItemModelOfDrawerList> list) {
        mContext = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_drawer_lv, parent, false);
            holder = new ViewHolder();
            holder.imageView = (ImageView) convertView.findViewById(R.id.iv_list_drawer);
            holder.textView = (TextView) convertView.findViewById(R.id.tv_list_drawer);
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();

        ItemModelOfDrawerList item = list.get(position);
        Glide.with(mContext).load(item.resId).into(holder.imageView);
        holder.textView.setText(item.tab);

        return convertView;
    }

    public class ViewHolder {
        private ImageView imageView;
        private TextView textView;
    }
}
