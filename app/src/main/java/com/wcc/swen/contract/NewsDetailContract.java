package com.wcc.swen.contract;

import com.wcc.swen.BasePresenter;
import com.wcc.swen.BaseView;
import com.wcc.swen.model.NewsModel;

import java.util.List;

/**
 * Created by WangChenchen on 2016/8/19.
 */
public interface NewsDetailContract {

    interface View extends BaseView {

        void showView(android.view.View view);

        void retry(android.view.View view);

        android.view.View getView();
    }

    interface Presenter extends BasePresenter {
        void loadData(String url, List<NewsModel> list);
    }
}
