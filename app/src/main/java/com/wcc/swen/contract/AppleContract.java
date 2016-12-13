package com.wcc.swen.contract;

import android.app.Activity;
import android.widget.TextView;

/**
 * Created by WangChenchen on 2016/12/13.
 */

public interface AppleContract {

    interface Presenter{

        void dailyApple(Activity activity, TextView tv_apple_fragment);
    }
}
