package com.wcc.swen.presenter;

import com.wcc.swen.contract.MainContract;

/**
 * Created by Administrator on 2016/8/17.
 */
public class MainPresenter implements MainContract.Presenter {

    private final MainContract.View mainView;

    public MainPresenter(MainContract.View view)
    {
        mainView = view;
    }

    @Override
    public void switchFragment(int fragmentId) {
        mainView.switchFragment(fragmentId);
    }
}
