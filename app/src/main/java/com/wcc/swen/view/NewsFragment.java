package com.wcc.swen.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;

import com.wcc.swen.R;
import com.wcc.swen.adapter.NewsAdapter;
/**
 * Created by Administrator on 2016/8/17.
 */
public class NewsFragment extends Fragment {

    final String tag = "NewsFragment";
    private View btn_tab_select;
    private TabLayout tab;
    private ViewPager vp;
    private PopupWindow pop;
    private boolean isPopupWindowShowing = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_news, container, false);

        // 实现tab效果
        tab = (TabLayout) view.findViewById(R.id.tab);
        vp = (ViewPager) view.findViewById(R.id.vp_news_main);
        vp.setAdapter(new NewsAdapter(getChildFragmentManager(), getActivity()));
        vp.setOffscreenPageLimit(1);
        tab.setupWithViewPager(vp);
        tab.setTabMode(TabLayout.MODE_SCROLLABLE);

        btn_tab_select = view.findViewById(R.id.btn_tab_select);
        btn_tab_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                int currPos = tab.getSelectedTabPosition();
//                tab.setScrollPosition(currPos, 1 - currPos, true);
//                vp.setCurrentItem(1);
                View popView = LayoutInflater.from(getActivity()).inflate(R.layout.layout_popupwindow_selecttab, null);
                View parent = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_news, null);
                Button btn_popup = (Button) popView.findViewById(R.id.btn_popup);
                pop = new PopupWindow(popView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
                btn_popup.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pop.dismiss();
                    }
                });
                pop.setBackgroundDrawable(null);
                pop.setFocusable(false);
                pop.showAsDropDown(btn_tab_select, 0, -btn_tab_select.getHeight());
                isPopupWindowShowing = true;
//                pop.showAtLocation(parent, Gravity.CENTER, 0, 0);
            }
        });

        return view;
    }

    public boolean getIsPopupWindowShowing() {
        return isPopupWindowShowing;
    }

    public void onBackPressed() {
        if (pop != null) {
            if (pop.isShowing()) {
                pop.dismiss();
                isPopupWindowShowing = false;
            }
        }
    }
}
