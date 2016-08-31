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
import com.wcc.swen.model.Daily;

import java.util.List;

/**
 * Created by WangChenchen on 2016/8/30.
 */
public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.ViewHolder> {


    private Context mContext;
    private List<Daily> mDaily;

    public WeatherAdapter(Context context, List<Daily> list) {
        mContext = context;
        mDaily = list;
    }

    @Override
    public WeatherAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_weather, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(WeatherAdapter.ViewHolder holder, int position) {
        Daily daily = mDaily.get(position);
        holder.tv_day.setText(daily.date);
        holder.tv_weather.setText(daily.text_day);
        holder.tv_weather_wd.setText(daily.high + "°/" + daily.low + "°");
        int id = R.mipmap.halu;
        if (daily.text_day.contains("多云"))
            id = R.mipmap.cloud;
        else if (daily.text_day.equals("阴"))
            id = R.mipmap.yintian;
        else if (daily.text_day.contains("阵雨"))
            id = R.mipmap.thunder;
        else if (daily.text_day.equals("小雨")
                || daily.text_day.equals("中雨")
                || daily.text_day.equals("大雨")
                || daily.text_day.equals("冻雨")
                || daily.text_day.equals("雨夹雪"))
            id = R.mipmap.rain;
        else if (daily.text_day.contains("暴雨"))
            id = R.mipmap.stormrain;
        else if (daily.text_day.contains("雪"))
            id = R.mipmap.snow;
        else if (daily.text_day.contains("尘") || daily.text_day.equals("扬沙"))
            id = R.mipmap.sand;
        else if (daily.text_day.equals("雾") || daily.text_day.equals("霾"))
            id = R.mipmap.frog;
        else if (daily.text_day.equals("风") || daily.text_day.equals("大风"))
            id = R.mipmap.wind;
        else if (daily.text_day.equals("飓风") || daily.text_day.equals("热带风暴") || daily.text_day.equals("龙卷风"))
            id = R.mipmap.storm;
        else if (daily.text_day.equals("未知"))
            id = R.mipmap.mistery;

        Glide.with(mContext).load(id).into(holder.iv_weather);

    }

    @Override
    public int getItemCount() {
        return mDaily.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView iv_weather;
        private TextView tv_day;
        private TextView tv_weather;
        private TextView tv_weather_wd;

        public ViewHolder(View itemView) {
            super(itemView);

            iv_weather = (ImageView) itemView.findViewById(R.id.iv_weather);
            tv_day = (TextView) itemView.findViewById(R.id.tv_day);
            tv_weather = (TextView) itemView.findViewById(R.id.tv_weather);
            tv_weather_wd = (TextView) itemView.findViewById(R.id.tv_weather_wd);
        }
    }
}
