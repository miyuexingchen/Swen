package com.wcc.swen.contract;

import com.wcc.swen.BasePresenter;
import com.wcc.swen.BaseView;

import java.util.List;

/**
 * Created by WangChenchen on 2016/8/30.
 */
public interface WeatherContract {

    interface View extends BaseView {
        void setWrapper(Object wrapper);

        void refreshUI();
    }

    interface Presenter extends BasePresenter {
        void loadData(String url);
    }
}
