package com.wcc.swen.contract;

import com.wcc.swen.BasePresenter;
import com.wcc.swen.BaseView;
import java.util.List;

/**
 * Created by WangChenchen on 2016/8/19.
 */
public interface NewsDetailContract {

    interface View<T> extends BaseView {

        void showView();

        void retry();

        void setList(List<T> list);
    }

    interface Presenter extends BasePresenter {
        void loadData(String url, String tab);

        void loadRefreshData(String url, String tab);

        void loadMoreData(String url, String tab);
    }
}
