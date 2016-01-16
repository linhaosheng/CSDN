package com.example.administrator.activity;

import android.content.Context;

import com.example.administrator.application.BaseApplication;

/**
 * Created by Administrator on 2015/11/12.
 */
public interface BaseActivity {

    /*
    获取CSDNApplication对象
     */
    public abstract BaseApplication getCSDNApplication();

    /**
     * 检查网络
     */
    public abstract boolean validateInternet();

    /**
     * 检验网络，如果没有网络就返回true;
     *
     * @return
     */
    public abstract boolean hasInternetConnected();

    /**
     * 退出应用
     */
    public abstract void isExit();

    /**
     * 判断GPS是否已经开启
     *
     * @return
     */
    public abstract boolean hasLocationGPS();

    /**
     * 判断是否有开启基站
     *
     * @return
     */
    public abstract boolean hasLocationNetWork();

    /**
     * 检查内存卡
     */
    public abstract void checkMemoryCard();

    /**
     * 显示toast
     *
     * @param text
     * @param longTime
     */
    public abstract void showToast(String text, int longTime);

    public abstract void showToast(String text);

    /**
     * 返回当前Activity的上下文
     *
     * @return
     */
    public abstract Context getContext();

}
