package com.wcc.swen.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wcc.swen.R;
import com.wcc.swen.adapter.WeatherAdapter;
import com.wcc.swen.contract.WeatherContract;
import com.wcc.swen.model.Daily;
import com.wcc.swen.model.Weather;
import com.wcc.swen.model.WeatherWrapper;
import com.wcc.swen.presenter.WeatherPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/8/17.
 */
public class WeatherFragment extends Fragment implements WeatherContract.View {

    private static final String url = "https://api.thinkpage.cn/v3/weather/daily.json?key=wwl9dtjoxf2ydspl&location=nanjing&language=zh-Hans&unit=c&start=0&days=5";
    private WeatherWrapper mWrapper;

    @BindView(R.id.tv_today_wd)
    TextView wd;
    @BindView(R.id.tv_today_tq)
    TextView tq;
    @BindView(R.id.tv_fxfs)
    TextView fxfs;
    @BindView(R.id.rv_tq)
    RecyclerView rv_tq;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather, container, false);
        ButterKnife.bind(this, view);

        WeatherPresenter mPresenter = new WeatherPresenter(this);
        mPresenter.loadData(url);

        return view;
    }

    @Override
    public void setWrapper(Object wrapper) {
        mWrapper = (WeatherWrapper) wrapper;
    }

    @Override
    public void refreshUI() {
        if (mWrapper != null) {
            List<Weather> results = mWrapper.results;
            List<Daily> daily = results.get(0).daily;
            Daily d = daily.get(0);

            wd.setText(d.high + "°");
            tq.setText("北京|" + d.text_day);
            if (d.wind_direction.equals("无持续风向"))
                fxfs.setText(d.wind_scale + "级");
            else
                fxfs.setText(d.wind_direction + "风" + d.wind_scale + "级");

            WeatherAdapter adapter = new WeatherAdapter(getActivity(), daily);
            LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
            rv_tq.setLayoutManager(manager);
            rv_tq.setItemAnimator(new DefaultItemAnimator());

            rv_tq.setAdapter(adapter);
        }
    }
}
