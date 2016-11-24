package com.wcc.swen.activity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.wcc.swen.R;
import com.wcc.swen.adapter.ImageNewsAdapter;
import com.wcc.swen.contract.NewsDetailContract;
import com.wcc.swen.model.Photo;
import com.wcc.swen.presenter.ImageNewsPresenter;
import com.wcc.swen.utils.Url;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by WangChenchen on 2016/8/23.
 */
public class ImageNewsActivity extends AppCompatActivity implements NewsDetailContract.View<Photo> {

    private ImageNewsPresenter mPresenter;
    private String url;
    private String title;
    private List<Photo> list;
    @BindView(R.id.tv_title_image_news)
    TextView tv_title_image_news;
    @BindView(R.id.tv_page_image_news)
    TextView tv_page_image_news;
    @BindView(R.id.tv_image_news)
    TextView tv_image_news;
    @BindView(R.id.vp_image_news)
    ViewPager vp_image_news;
    @BindView(R.id.pb_image_news)
    ProgressBar pb_image_news;
    @BindView(R.id.btn_hint_retry)
    Button btn_image_news;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_news);
        ButterKnife.bind(this);


        initView();

        title = getIntent().getStringExtra("title");
        // 获取url
        url = getIntent().getStringExtra("url");
        // 对url进行重新拼接
        url = url.substring(4);
        url = Url.PHOTO_SET + url.substring(0, 4) + "/" + url.substring(5) + ".json";
        // 通过Presenter获取数据
        mPresenter = new ImageNewsPresenter(this);

        mPresenter.loadData(url, "");
    }

    private void initView() {


        // 当版本大于5.0，调用方法更改状态栏颜色
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.BLACK);   //这里动态修改颜色
        }

        setSupportActionBar(toolbar);
        toolbar.setBackgroundColor(Color.BLACK);
        // 这句话保证title能被修改
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        // 给HomeButton设置点击事件
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void showView() {

        pb_image_news.setVisibility(View.GONE);
        vp_image_news.setVisibility(View.VISIBLE);
        tv_title_image_news.setVisibility(View.VISIBLE);
        tv_page_image_news.setVisibility(View.VISIBLE);
        tv_image_news.setVisibility(View.VISIBLE);

        tv_image_news.setText(list.get(0).note);
        tv_page_image_news.setText(1 + "/" + list.size());
        tv_title_image_news.setText(title);

        ImageNewsAdapter adapter = new ImageNewsAdapter(this, list);
        vp_image_news.setAdapter(adapter);
        vp_image_news.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tv_page_image_news.setText((position + 1) + "/" + list.size());
                tv_image_news.setText(list.get(position).note);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
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
                mPresenter.loadData(url, "");
            }
        });
    }

    @Override
    public void setList(List<Photo> list) {
        this.list = list;
    }

}
