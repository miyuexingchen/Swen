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
        String weather = daily.text_day;
        holder.tv_weather.setText(weather);
        holder.tv_weather_wd.setText(daily.high + "°/" + daily.low + "°");
        int id = R.mipmap.halu;
        if (weather.contains("多云"))
            id = R.mipmap.cloud;
        else if (weather.equals("阴"))
            id = R.mipmap.yintian;
        else if (weather.contains("阵雨"))
            id = R.mipmap.thunder;
        else if (weather.equals("小雨")
                || weather.equals("中雨")
                || weather.equals("大雨")
                || weather.equals("冻雨")
                || weather.equals("雨夹雪"))
            id = R.mipmap.rain;
        else if (weather.contains("暴雨"))
            id = R.mipmap.stormrain;
        else if (weather.contains("雪"))
            id = R.mipmap.snow;
        else if (weather.contains("尘") || weather.equals("扬沙"))
            id = R.mipmap.sand;
        else if (weather.equals("雾") || weather.equals("霾"))
            id = R.mipmap.frog;
        else if (weather.equals("风") || weather.equals("大风"))
            id = R.mipmap.wind;
        else if (weather.equals("飓风") || weather.equals("热带风暴") || weather.equals("龙卷风"))
            id = R.mipmap.storm;
        else if (weather.equals("未知"))
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
