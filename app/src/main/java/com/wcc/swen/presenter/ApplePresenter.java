package com.wcc.swen.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.TextView;

import com.wcc.swen.contract.AppleContract;

import java.text.SimpleDateFormat;
import java.util.Random;

/**
 * Created by WangChenchen on 2016/12/13.
 */

public class ApplePresenter implements AppleContract.Presenter {
    String[] thoughts = {
            "颠倒常做的事和逃避的事的做的频率。",
            "如果一个人连自己都不爱惜自己，他（她）更不可能爱惜别人。",
            "逆向思维。",
            "你为什么坚信你所坚信的东西？",
            "多关心一下自己，少关心一下别人怎么看自己。",
            "自己的感受比道理重要。"
    };
    @Override
    public void dailyApple(Activity activity, TextView tv_apple_fragment) {
        SharedPreferences sp = activity.getSharedPreferences("apple", Context.MODE_PRIVATE);
        long dateNumber = sp.getLong("date", 0);
        long currDateNumber = System.currentTimeMillis();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String date = format.format(dateNumber);
        String currDate = format.format(currDateNumber);
        if(date.equals(currDate))
        {
            tv_apple_fragment.setText(sp.getString("apple", "error"));
        }else
        {
            System.out.println(thoughts.length);


//            Random r = new Random(thoughts.length);
//            int index = r.nextInt();
            int index = (int) (Math.random() * thoughts.length);
            System.out.println(index);
            String thought = thoughts[index];
            tv_apple_fragment.setText(thought);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("apple", thought);
            editor.putLong("date", currDateNumber);
            editor.apply();
        }
    }
}
