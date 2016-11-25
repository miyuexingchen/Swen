package com.wcc.swen.contract;

import com.wcc.swen.model.WeatherWrapper;

/**
 * Created by WangChenchen on 2016/8/30.
 */
public interface WeatherContract {

    interface View {
        void setWrapper(WeatherWrapper wrapper);

        void refreshUI();
    }

    interface Presenter {
        void loadData();
    }
}
