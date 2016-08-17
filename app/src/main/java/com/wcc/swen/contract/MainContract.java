package com.wcc.swen.contract;

import com.wcc.swen.BasePresenter;
import com.wcc.swen.BaseView;
import com.wcc.swen.presenter.MainPresenter;

/**
 * Created by Administrator on 2016/8/17.
 */
public interface MainContract {

    public interface View{
        void switchFragment(int fragmentId);
    }

    public interface Presenter extends BasePresenter{
        void switchFragment(int fragmentId);
    }
}
