package com.wcc.swen.activity;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.wcc.swen.R;
import com.wcc.swen.adapter.DrawerListAdapter;
import com.wcc.swen.model.ItemModelOfDrawerList;
import com.wcc.swen.view.AppleFragment;
import com.wcc.swen.view.NewsFragment;
import com.wcc.swen.view.VideoFragment;
import com.wcc.swen.view.WeatherFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    private List<ItemModelOfDrawerList> items = new ArrayList<>();
    private int currentFragmentId = 0;
    // 用于关闭Drawer
    @BindView(R.id.ll_drawer)
    LinearLayout ll_drawer;
    @BindView(R.id.lv_drawer)
    ListView lv_drawer;
    private FragmentManager fragmentManager;
    private long firstPressTime = 0;
    private Fragment currentFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);

        // 初始化view
        initView(savedInstanceState);
    }

    private Unbinder unbinder;
    @Override
    protected void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }

    private void initView(Bundle savedInstanceState)
    {

        // 主页面默认添加NewsFragment
        fragmentManager = getSupportFragmentManager();

        /**
         * 防止碎片重叠
         */
        Fragment videoFragment;
        Fragment weatherFragment;
        Fragment newsFragment;
        if(savedInstanceState != null)
        {
            currentFragment = fragmentManager.findFragmentByTag("apple");
            newsFragment = fragmentManager.findFragmentByTag("news");
            videoFragment = fragmentManager.findFragmentByTag("video");
            weatherFragment = fragmentManager.findFragmentByTag("weather");
            fragmentManager.beginTransaction().show(currentFragment)
                    .hide(newsFragment)
                    .hide(videoFragment)
                    .hide(weatherFragment)
                    .commit();

        }else
        {
            currentFragment = new AppleFragment();
            newsFragment = new NewsFragment();
            videoFragment = new VideoFragment();
            weatherFragment = new WeatherFragment();
            fragmentManager.beginTransaction().add(R.id.ll_content, currentFragment, "apple")
                    .add(R.id.ll_content, videoFragment, "video")
                    .add(R.id.ll_content, weatherFragment, "weather")
                    .add(R.id.ll_content, newsFragment, "news")
                    .show(currentFragment)
                    .hide(newsFragment)
                    .hide(videoFragment)
                    .hide(weatherFragment)
                    .commit();
        }

        setSupportActionBar(toolbar);
        // 这句话保证title能被修改
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitle("苹果");

        // 这两句显示左边的三条杠，如果要变为白色在toolbar的布局文件里添加这两句：
        // android:popupTheme="@style/ThemeOverlay.AppCompat.Light"
        // app:theme="@style/ThemeOverlay.AppCompat.Dark.ActionBar"
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        items.add(new ItemModelOfDrawerList(R.mipmap.apple, "苹果"));
        items.add(new ItemModelOfDrawerList(R.mipmap.news_icon, "新闻"));
        items.add(new ItemModelOfDrawerList(R.mipmap.video_icon, "视频"));
        items.add(new ItemModelOfDrawerList(R.mipmap.weather_icon, "天气"));

        DrawerListAdapter mAdapter = new DrawerListAdapter(this, items);
        lv_drawer.setAdapter(mAdapter);

        lv_drawer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        switchFragment(position);
            }
        });

        ActionBarDrawerToggle mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);

        mToggle.syncState();
        mDrawerLayout.addDrawerListener(mToggle);
    }

    // 根据所点列表项的下标，切换fragment
    public void switchFragment(int fragmentId) {
        mDrawerLayout.closeDrawer(ll_drawer);
        if(currentFragmentId == fragmentId)
            return;
        currentFragmentId = fragmentId;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment toFragment = null;
        switch (fragmentId)
        {
            case 0:
                toFragment = fragmentManager.findFragmentByTag("apple");
                toolbar.setTitle("苹果");
                break;
            case 1:
                toFragment = fragmentManager.findFragmentByTag("news");
                toolbar.setTitle("新闻资讯");
                break;
            case 2:
                toFragment = fragmentManager.findFragmentByTag("video");
                toolbar.setTitle("视频");
                break;
            case 3:
                toFragment = fragmentManager.findFragmentByTag("weather");
                toolbar.setTitle("天气");
                break;
        }
        fragmentTransaction.hide(currentFragment).show(toFragment).commit();
        currentFragment = toFragment;
    }

    @Override
    public void onBackPressed() {
        if (currentFragmentId == 0) {
            NewsFragment news = (NewsFragment) fragmentManager.findFragmentByTag("news");
            if(news.getIsPopupWindowShowing()) {
                news.onBackPressed();
                return;
            }
        }

        if (mDrawerLayout.isDrawerOpen(ll_drawer)) {
            mDrawerLayout.closeDrawer(ll_drawer);
            return;
        }

        long now = System.currentTimeMillis();
        if((now - firstPressTime) > 2000)
        {
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            firstPressTime = now;
        }else
        {
            finish();
            System.exit(0);
        }
    }
}
