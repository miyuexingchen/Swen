package com.wcc.swen.contract;

/**
 * Created by WangChenchen on 2016/8/30.
 */
public interface WeatherContract {

    interface View {
        void setWrapper(Object wrapper);

        void refreshUI();
    }

    interface Presenter {
        void loadData(String url);
    }
}
