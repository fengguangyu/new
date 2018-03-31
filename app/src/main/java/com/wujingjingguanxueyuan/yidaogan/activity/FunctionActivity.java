package com.wujingjingguanxueyuan.yidaogan.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.wujingjingguanxueyuan.yidaogan.R;
import com.wujingjingguanxueyuan.yidaogan.base.BaseActivity;
import com.wujingjingguanxueyuan.yidaogan.bean.User;
import com.wujingjingguanxueyuan.yidaogan.fragment.DiscoverFragment;
import com.wujingjingguanxueyuan.yidaogan.fragment.MineFragment;
import com.wujingjingguanxueyuan.yidaogan.fragment.SportFragment;
import com.wujingjingguanxueyuan.yidaogan.fragment.TrainingFragment;
import com.wujingjingguanxueyuan.yidaogan.utils.Constant;
import com.wujingjingguanxueyuan.yidaogan.utils.SaveKeyValues;
import com.wujingjingguanxueyuan.yidaogan.view.ViewPagerIndicator;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import cn.bmob.v3.BmobUser;


/**
 * 功能界面
 */
public class FunctionActivity extends BaseActivity{

    //变量
    private long exitTime;//第一次单机退出键的时间
    private int load_values;//判断加载fragment的变量
    private ViewPagerIndicator indicator;
    private ViewPager viewPager;
    private List<Fragment> fragments = new ArrayList<>();
    //碎片
    private SportFragment sportFragment;//运动
    private TrainingFragment trainingFragment;//发现
    private DiscoverFragment discoverFragment;//心率
    private MineFragment mineFragment;//我的

    private DrawerLayout mDrawerLayout;
    // 使用一个栈记录所有添加的Fragment
    private Stack<Fragment> fragmentStack = new Stack<Fragment>();

    /**
     * 设置标题
     */
    @Override
    protected void setActivityTitle() {

    }

    /**
     * 初始化界面
     */
    @Override
    protected void getLayoutToView() {
        setContentView(R.layout.activity_function);
    }

    /**
     * 初始化相关变量
     */
    @Override
    protected void initValues() {
        //初始化界面

        //如果这个值等于1就加载运动界面，等于2就加载发现界面
        load_values = SaveKeyValues.getIntValues("launch_which_fragment", 0);
        Log.e("加载判断值", load_values + "");
        //实例化相关碎片
        sportFragment = new SportFragment();
        trainingFragment = new TrainingFragment();
        discoverFragment = new DiscoverFragment();
        mineFragment = new MineFragment();

        if (load_values == Constant.TURN_MAIN) {
            Bundle bundle = new Bundle();
            bundle.putBoolean("is_launch", true);
            sportFragment.setArguments(bundle);
        } else {
        }

        fragments.add(sportFragment);
        fragments.add(trainingFragment);
        fragments.add(discoverFragment);
        fragments.add(mineFragment);

     /*   String[] shuoming = DemoApplication.shuoming;
        Traing traing = new Traing();
        for (int i =0 ; i< 5 ; i++){
            traing.shuoming = shuoming[i];
            traing.cishu = "一组六次";
            traing.tj = "添加新计划";
            traing.user = BmobUser.getCurrentUser(User.class);
            traing.save();
        }*/

    }

    /**
     * 初始化控件
     */
    @Override
    protected void initViews() {
        indicator = (ViewPagerIndicator) findViewById(R.id.fun_pageindicator);
        viewPager = findViewById(R.id.fun_viewpager);

        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                if (fragments.get(position) != null) {
                    return fragments.get(position);
                }
                return null;
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        });

        indicator.setViewPager(viewPager, 0);
        indicator.setVisibleTabCount(fragments.size());


        //初始化界面
        if (load_values == Constant.TURN_MAIN) {
            Bundle bundle = new Bundle();
            bundle.putBoolean("is_launch", true);
            indicator.setViewPager(viewPager, 0);
        } else {
            indicator.setViewPager(viewPager, 1);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navView = (NavigationView) findViewById(R.id.navigation_view);
        String name = BmobUser.getCurrentUser(User.class).getNick();// SaveKeyValues.getStringValues("nick", "未填写");//获取名称
        if((TextView)navView.findViewById(R.id.username) != null){
            ((TextView)navView.findViewById(R.id.username)).setText(name+"");
        }
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }
        navView.setCheckedItem(R.id.nav_call);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_location:
                        startActivity(new Intent(FunctionActivity.this, WhereActivity.class));
                        break;
                }
                return true;
            }
        });
    }

    /**
     * 设置监听
     */
    @Override
    protected void setViewsListener() {

    }

    /**
     * 设置功能
     */
    @Override
    protected void setViewsFunction() {
        if (load_values == Constant.TURN_MAIN) {
            indicator.setViewPager(viewPager,0);
        } else {
            indicator.setViewPager(viewPager,1);
        }
    }

    /**
     * 按两次退出按钮退出程序
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            // System.currentTimeMillis()无论何时调用，肯定大于2000
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.backup:
                Toast.makeText(this, "You clicked Backup", Toast.LENGTH_SHORT).show();
                break;
            case R.id.delete:
                Toast.makeText(this, "You clicked Delete", Toast.LENGTH_SHORT).show();
                break;
            case R.id.settings:
                Toast.makeText(this, "You clicked Settings", Toast.LENGTH_SHORT).show();
                break;
            default:
        }
        return true;
    }
}
