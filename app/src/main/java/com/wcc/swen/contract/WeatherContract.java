package com.wcc.swen.contract;

import com.wcc.swen.model.WeatherWrapper;

/**
 * Created by WangChenchen on 2016/8/30.
 */
public interface WeatherContract {

    interface View {
        void refreshUI(WeatherWrapper weatherWrapper);
    }

    interface Presenter {
        void loadData();
    }
}
