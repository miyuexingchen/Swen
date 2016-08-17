package com.wcc.swen.activity;

import android.app.FragmentManager;
import android.graphics.Color;
import android.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.wcc.swen.R;
import com.wcc.swen.contract.MainContract;
import com.wcc.swen.presenter.MainPresenter;
import com.wcc.swen.view.NewsFragment;
import com.wcc.swen.view.VideoFragment;
import com.wcc.swen.view.WeatherFragment;

public class MainActivity extends AppCompatActivity implements MainContract.View{

    private Toolbar toolbar;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private ListView lv_drawer;
    private String[] items = {"新闻","视频","天气"};
    private ArrayAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 初始化view
        initView();
    }

    private MainPresenter mPresenter;
    private int currentFragmentId = 0;
    // 用于关闭Drawer
    private LinearLayout ll_drawer;
    private FragmentManager fragmentManager;
    private void initView()
    {
        mPresenter = new MainPresenter(this);

        // 主页面默认添加NewsFragment
        fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().add(R.id.ll_content, new NewsFragment()).commit();

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
        lv_drawer = (ListView) findViewById(R.id.lv_drawer);
        mAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, items);
        lv_drawer.setAdapter(mAdapter);

        ll_drawer = (LinearLayout) findViewById(R.id.ll_drawer);
        lv_drawer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        mPresenter.switchFragment(position);
            }
        });

        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);

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
                fragmentTransaction.replace(R.id.ll_content, new NewsFragment());
                toolbar.setTitle("新闻资讯");
                break;
            case 1:
                fragmentTransaction.replace(R.id.ll_content, new VideoFragment());
                toolbar.setTitle("视频");
                break;
            case 2:
                fragmentTransaction.replace(R.id.ll_content, new WeatherFragment());
                toolbar.setTitle("天气");
                break;
        }
        fragmentTransaction.commit();
    }

    private long firstPressTime = 0;
    @Override
    public void onBackPressed() {
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

    /*@Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK)
        {
            exit();
        }
        return false;
    }

    private boolean isExit = false;

    private void exit()
    {
        Timer timer = null;
        if(!isExit)
        {
            isExit = true;
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false;
                }
            }, 2000);
        }else{
            finish();
            System.exit(0);
        }
    }*/
}
