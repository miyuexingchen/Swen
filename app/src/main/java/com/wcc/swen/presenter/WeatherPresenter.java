package com.wcc.swen.presenter;

import android.support.v4.app.Fragment;
import com.wcc.swen.net.WeatherRequest;
import com.wcc.swen.contract.WeatherContract;
import com.wcc.swen.model.WeatherWrapper;
import com.wcc.swen.utils.NetUtils;
import com.wcc.swen.utils.ToastUtils;
import com.wcc.swen.view.WeatherFragment;

import rx.Subscriber;

/**
 * Created by Administrator on 2016/8/17.
 */
public class WeatherPresenter implements WeatherContract.Presenter {

    private WeatherContract.View mView;
    private Fragment mFragment;

    public WeatherPresenter(WeatherContract.View view) {
        mView = view;
        mFragment = (WeatherFragment) mView;
    }

    @Override
    public void loadData() {

        WeatherRequest.getInstance().getWeather(new Subscriber<WeatherWrapper>() {

            private void unSubscribe()
            {
                if(!this.isUnsubscribed())
                    this.unsubscribe();
            }

            @Override
            public void onStart() {
                if (!NetUtils.isNetworkConnected(mFragment.getContext())) {
                    ToastUtils.show("网络不可用，请检查网络后再试。", mFragment.getContext());
                    unSubscribe();
                }

                if (NetUtils.getConnectedType(mFragment.getContext()) == 0) {
                    ToastUtils.show("当前网络为移动网络，请到wifi环境下重试。", mFragment.getContext());
                    unSubscribe();
                }
            }

            @Override
            public void onCompleted() {
                System.out.println("weather onCompleted " + Thread.currentThread().getName());
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("weather onError " + e.getMessage() + " " + Thread.currentThread().getName());
            }

            @Override
            public void onNext(WeatherWrapper weatherWrapper) {
                if (weatherWrapper != null) {
                    mView.refreshUI(weatherWrapper);
                }
                System.out.println("weather onNext " + Thread.currentThread().getName());
            }
        });
    }
}
