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
import com.wcc.swen.contract.MainContract;
import com.wcc.swen.model.ItemModelOfDrawerList;
import com.wcc.swen.presenter.MainPresenter;
import com.wcc.swen.view.NewsFragment;
import com.wcc.swen.view.VideoFragment;
import com.wcc.swen.view.WeatherFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MainContract.View{

    private Toolbar toolbar;
    private DrawerLayout mDrawerLayout;
    private List<ItemModelOfDrawerList> items = new ArrayList<>();
    private MainPresenter mPresenter;
    private int currentFragmentId = 0;
    // 用于关闭Drawer
    private LinearLayout ll_drawer;
    private FragmentManager fragmentManager;
    private long firstPressTime = 0;
    private Fragment currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 初始化view
        initView();
    }

    private void initView()
    {
        mPresenter = new MainPresenter(this);

        // 主页面默认添加NewsFragment
        fragmentManager = getSupportFragmentManager();
        currentFragment = new NewsFragment();
        fragmentManager.beginTransaction().add(R.id.ll_content, currentFragment).commit();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // 这句话保证title能被修改
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitle("新闻资讯");

        // 这两句显示左边的三条杠，如果要变为白色在toolbar的布局文件里添加这两句：
        // android:popupTheme="@style/ThemeOverlay.AppCompat.Light"
        // app:theme="@style/ThemeOverlay.AppCompat.Dark.ActionBar"
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ListView lv_drawer = (ListView) findViewById(R.id.lv_drawer);

        items.add(new ItemModelOfDrawerList(R.mipmap.news_icon, "新闻"));
        items.add(new ItemModelOfDrawerList(R.mipmap.video_icon, "视频"));
        items.add(new ItemModelOfDrawerList(R.mipmap.weather_icon, "天气"));

        DrawerListAdapter mAdapter = new DrawerListAdapter(this, items);
        lv_drawer.setAdapter(mAdapter);

        ll_drawer = (LinearLayout) findViewById(R.id.ll_drawer);
        lv_drawer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        mPresenter.switchFragment(position);
            }
        });

        ActionBarDrawerToggle mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);

        mToggle.syncState();
        mDrawerLayout.addDrawerListener(mToggle);
    }

    // 根据所点列表项的下标，切换fragment
    @Override
    public void switchFragment(int fragmentId) {
        mDrawerLayout.closeDrawer(ll_drawer);
        if(currentFragmentId == fragmentId)
            return;
        currentFragmentId = fragmentId;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        switch (fragmentId)
        {
            case 0:
                currentFragment = new NewsFragment();
                toolbar.setTitle("新闻资讯");
                break;
            case 1:
                currentFragment = new VideoFragment();
                toolbar.setTitle("视频");
                break;
            case 2:
                currentFragment = new WeatherFragment();
                toolbar.setTitle("天气");
                break;
        }
        fragmentTransaction.replace(R.id.ll_content, currentFragment).commit();
    }

    @Override
    public void onBackPressed() {
        if (currentFragmentId == 0 && ((NewsFragment) currentFragment).getIsPopupWindowShowing()) {
            ((NewsFragment) currentFragment).onBackPressed();
            return;
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
