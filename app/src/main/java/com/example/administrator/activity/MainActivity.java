package com.example.administrator.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;

import com.example.administrator.activity.activity_infor.BlogHouseActivity;
import com.example.administrator.activity.activity_infor.CSDNActivity;
import com.example.administrator.activity.activity_infor.CTOActivity;
import com.example.administrator.activity.activity_infor.ITEYEActivity;
import com.example.administrator.adapter.TabAdapter;
import com.example.administrator.csdn.R;
import com.viewpagerindicator.TabPageIndicator;


/**
 * Created by Administrator on 2015/11/15.
 */
public class MainActivity extends BaseActivityImpl implements View.OnClickListener{
    private TabPageIndicator tabPageIndicator;
    private ViewPager mViewPage;
    private FragmentPagerAdapter mAdapter;
    private int select = 0;

    private CTOActivity cto=null;
    private CSDNActivity csdn=null;
    private BlogHouseActivity blog=null;
    private ITEYEActivity iteye=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getBundleExtra("IT_select");
        int select_Num = bundle.getInt("select");
        select = select_Num;
        switch (select) {
            case 1:
                setContentView(R.layout.csdnmain);
                csdn=new CSDNActivity(this,MainActivity.this);
                csdn.initTabLine();
                initCSDN();
                csdn.setViewPage(mViewPage);
                csdn.initViewPage();
                csdn.setOnClickListener(this);
                csdn.textListener();
                getCSDNApplication().addActivity(this);
                break;
            case 2:
                setContentView(R.layout.ctomain);
                cto=new CTOActivity(this,MainActivity.this);
                cto.initTabLine();
                init_51CTO();
                cto.setViewPage(mViewPage);
                cto.initViewPage();
                cto.setOnClickListener(this);
                cto.textListener();
                getCSDNApplication().addActivity(this);
                break;
            case 3:
                setContentView(R.layout.bloghousemain);
                blog=new BlogHouseActivity(this,MainActivity.this);
                blog.initTabLine();
                init_BLOG_HOUSE();
                blog.setViewPage(mViewPage);
                blog.initViewPage();
                blog.setOnClickListener(this);
                blog.textListener();
                getCSDNApplication().addActivity(this);
                break;
            case 4:
                setContentView(R.layout.iteyemain);
                iteye=new ITEYEActivity(this,MainActivity.this);
                iteye.initTabLine();
                init_ITEYE();
                iteye.setViewPage(mViewPage);
                iteye.initViewPage();
                iteye.setOnClickListener(this);
                iteye.textListener();
                getCSDNApplication().addActivity(this);
                break;
        }

    }

    @Override
    public void onClick(View v) {
        switch (select){
            case 1:
                csdn.csdnClick(v);
                break;
            case 2:
                cto.ctoClick(v);
                break;
            case 3:
                blog.blogClick(v);
                break;
            case 4:
                iteye.iteyeClick(v);
                break;
        }

    }

    /**
     * 初始化CSDN
     */
    public void initCSDN() {
        tabPageIndicator = (TabPageIndicator) findViewById(R.id.id_indicator);
        mViewPage = (ViewPager) findViewById(R.id.id_pager);
        Resources res = getResources();
        String[] array = res.getStringArray(R.array.csdn);
        mAdapter = new TabAdapter(getSupportFragmentManager(), array, select);
        mViewPage.setAdapter(mAdapter);
        tabPageIndicator.setViewPager(mViewPage, 0);
    }

    /**
     * 初始化51CTO博客
     */
    public void init_51CTO() {
        tabPageIndicator = (TabPageIndicator) findViewById(R.id.id_indicator);
        mViewPage = (ViewPager) findViewById(R.id.id_pager);
        Resources res = getResources();
        String[] array = res.getStringArray(R.array.cto);
        mAdapter = new TabAdapter(getSupportFragmentManager(), array, select);
        mViewPage.setAdapter(mAdapter);
        tabPageIndicator.setViewPager(mViewPage, 0);
    }

    /**
     * 初始化博客园
     */
    public void init_BLOG_HOUSE() {
        tabPageIndicator = (TabPageIndicator) findViewById(R.id.id_indicator);
        mViewPage = (ViewPager) findViewById(R.id.id_pager);
        Resources res = getResources();
        String[] array = res.getStringArray(R.array.blog_house);
        mAdapter = new TabAdapter(getSupportFragmentManager(), array, select);
        mViewPage.setAdapter(mAdapter);
        tabPageIndicator.setViewPager(mViewPage, 0);
    }

    /**
     * 初始化ITEYE博客
     */
    public void init_ITEYE() {
        tabPageIndicator = (TabPageIndicator) findViewById(R.id.id_indicator);
        mViewPage = (ViewPager) findViewById(R.id.id_pager);
        Resources res = getResources();
        String[] array = res.getStringArray(R.array.iteye);
        mAdapter = new TabAdapter(getSupportFragmentManager(), array, select);
        mViewPage.setAdapter(mAdapter);
        tabPageIndicator.setViewPager(mViewPage, 0);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            Intent intent=new Intent(this,SelectActivity.class);
            startActivity(intent);
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }
}
