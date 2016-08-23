package com.wcc.swen.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.wcc.swen.R;
import com.wcc.swen.adapter.ImageNewsAdapter;
import com.wcc.swen.contract.NewsDetailContract;
import com.wcc.swen.model.Photo;
import com.wcc.swen.presenter.ImageNewsPresenter;
import com.wcc.swen.utils.LogUtils;
import com.wcc.swen.utils.Url;

import java.util.List;

/**
 * Created by WangChenchen on 2016/8/23.
 */
public class ImageNewsActivity extends AppCompatActivity implements NewsDetailContract.View<Photo>, ImageNewsAdapter.OnPageChangeListener {

    private static final String tag = "ImageNewsActivity";

    private ImageNewsPresenter mPresenter;
    private String url;
    private String title;
    private List<Photo> list;
    private Toolbar toolbar;
    private TextView tv_title_image_news;
    private TextView tv_page_image_news;
    private TextView tv_image_news;
    private RecyclerView rv_image_news;
    private ProgressBar pb_image_news;
    private Button btn_image_news;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_image_news);
        initView();

        title = getIntent().getStringExtra("title");
        // 获取url
        url = getIntent().getStringExtra("url");
        // 对url进行重新拼接
        url = url.substring(4);
        url = Url.PHOTO_SET + url.substring(0, 4) + "/" + url.substring(5) + ".json";
        LogUtils.e(tag, url);
        // 通过Presenter获取数据
        mPresenter = new ImageNewsPresenter(this);

        mPresenter.loadData(url);
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        rv_image_news = (RecyclerView) findViewById(R.id.rv_image_news);
        tv_title_image_news = (TextView) findViewById(R.id.tv_title_image_news);
        tv_page_image_news = (TextView) findViewById(R.id.tv_page_image_news);
        tv_image_news = (TextView) findViewById(R.id.tv_image_news);

        pb_image_news = (ProgressBar) findViewById(R.id.pb_image_news);
        btn_image_news = (Button) findViewById(R.id.btn_hint_retry);
    }

    @Override
    public void showView() {

        pb_image_news.setVisibility(View.GONE);
        rv_image_news.setVisibility(View.VISIBLE);
        tv_title_image_news.setVisibility(View.VISIBLE);
        tv_page_image_news.setVisibility(View.VISIBLE);
        tv_image_news.setVisibility(View.VISIBLE);

        tv_image_news.setText(list.get(0).note);
        tv_page_image_news.setText(1 + "/" + list.size());
        tv_title_image_news.setText(title);

        ImageNewsAdapter adapter = new ImageNewsAdapter(this, list);
        adapter.setPageChangeListener(this);

        rv_image_news.setLayoutManager(new LinearLayoutManager(this, LinearLayout.HORIZONTAL, false));
        rv_image_news.setItemAnimator(new DefaultItemAnimator());
        rv_image_news.setAdapter(adapter);
    }

    @Override
    public void retry() {
        pb_image_news.setVisibility(View.GONE);
        btn_image_news.setVisibility(View.VISIBLE);
        btn_image_news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pb_image_news.setVisibility(View.VISIBLE);
                btn_image_news.setVisibility(View.GONE);
                mPresenter.loadData(url);
            }
        });
    }

    @Override
    public void setList(List<Photo> list) {
        this.list = list;
    }

    @Override
    public void setPresenter(Object presenter) {

    }

    @Override
    public void onPageChange(int position) {
        tv_page_image_news.setText((position + 1) + "/" + list.size());
        tv_image_news.setText(list.get(position).note);
    }
}
