package com.wcc.swen.contract;

import com.wcc.swen.BasePresenter;
import com.wcc.swen.BaseView;
import com.wcc.swen.model.NewsModel;

import java.util.List;

/**
 * Created by WangChenchen on 2016/8/19.
 */
public interface NewsDetailContract<T> {

    interface View<T> extends BaseView {

        void showView();

        void retry();

        void setList(List<T> list);
    }

    interface Presenter extends BasePresenter {
        void loadData(String url);

        void loadRefreshData(String url);

        void loadMoreData(String url);
    }
}
