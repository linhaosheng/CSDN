package com.example.administrator.adapter;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.administrator.activity.MainFragment;


/**
 * Created by Administrator on 2015/11/12.
 */
public class TabAdapter extends FragmentPagerAdapter {

    //  public static final String[] title=new String[]{"业界","移动","研发","程序员杂志","云计算" };
    private String[] title;
    /**
     * 选择是选择哪个博客  1：CSDN  2：51CTO   3：博客园    4：ITEYE博客
     */
    private int select;

    public TabAdapter(FragmentManager fm, String[] title, int select) {
        super(fm);
        this.title = title;
        this.select = select;
    }


  /*  @Override
    public CharSequence getPageTitle(int position) {
        return title[position % title.length];
    }
*/
    @Override
    public int getCount() {
        return title == null ? 0 : title.length;
    }

    @Override
    public Fragment getItem(int i) {
        MainFragment fragment = new MainFragment(i + 1, select);
        return fragment;
    }
}
