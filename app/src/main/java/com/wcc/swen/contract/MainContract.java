package com.wcc.swen.contract;

import com.wcc.swen.BasePresenter;

/**
 * Created by Administrator on 2016/8/17.
 */
public interface MainContract {

    interface View {
        void switchFragment(int fragmentId);
    }

    interface Presenter extends BasePresenter {
        void switchFragment(int fragmentId);
    }
}
