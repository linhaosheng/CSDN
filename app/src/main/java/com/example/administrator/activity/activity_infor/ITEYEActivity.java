package com.example.administrator.activity.activity_infor;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.csdn.R;

/**
 * Created by linhao on 2016/1/13.
 */
public class ITEYEActivity {

    private TextView information_text;
    private TextView elite_text;
    private TextView blog_text;
    private TextView special_text;
    private ImageView mTabLine;
    private Context context;
    private int mCurrentPageIndex;
    Activity activity;
    private int mScreen;
    private ViewPager mViewPage;
    private View.OnClickListener onClickListener;

    public ITEYEActivity(Context context, Activity activity){
        this.context=context;
        this.activity=activity;
    }

    public void setViewPage(ViewPager mViewPage){
        this.mViewPage=mViewPage;
    }

    public void setOnClickListener(View.OnClickListener onClickListener){
        this.onClickListener=onClickListener;
    }

    public void iteyeClick(View v){

        int position=v.getId();
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mTabLine.getLayoutParams();
        switch (position){
            case R.id.information:
                resetTextView();
                mViewPage.setCurrentItem(0);
                lp.leftMargin = (int) (0* mScreen);
                information_text.setTextColor(Color.parseColor("#008000"));
                mTabLine.setLayoutParams(lp);
                break;
            case R.id.elite:
                resetTextView();
                mViewPage.setCurrentItem(1);
                elite_text.setTextColor(Color.parseColor("#008000"));
                lp.leftMargin = (int) (1* mScreen);
                mTabLine.setLayoutParams(lp);
                break;
            case R.id.blog:
                resetTextView();
                mViewPage.setCurrentItem(2);
                blog_text.setTextColor(Color.parseColor("#008000"));
                lp.leftMargin = (int) (2* mScreen);
                mTabLine.setLayoutParams(lp);
                break;
            case R.id.special:
                resetTextView();
                mViewPage.setCurrentItem(3);
                special_text.setTextColor(Color.parseColor("#008000"));
                lp.leftMargin = (int) (3* mScreen);
                mTabLine.setLayoutParams(lp);
                break;
        }
    }

    public void initViewPage(){

        resetTextView();
        information_text.setTextColor(Color.parseColor("#008000"));
        mViewPage.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                LinearLayout.LayoutParams lp=(LinearLayout.LayoutParams)mTabLine.getLayoutParams();
                if (mCurrentPageIndex == 0 && position == 0) {    // 0-->1
                    lp.leftMargin = (int) (positionOffset * mScreen + mCurrentPageIndex * mScreen);
                } else if (mCurrentPageIndex == 1 && position == 0) {   //1--->0
                    lp.leftMargin = (int) (mCurrentPageIndex * mScreen + (positionOffset - 1) * mScreen);
                } else if (mCurrentPageIndex == 1 && position == 1) {    //1--->2
                    lp.leftMargin = (int) (positionOffset * mScreen + mCurrentPageIndex * mScreen);
                } else if (mCurrentPageIndex == 2 && position == 1) {
                    lp.leftMargin = (int) (mCurrentPageIndex * mScreen + (positionOffset - 1) * mScreen);
                } else if (mCurrentPageIndex == 2 && position == 2) {    //2->3
                    lp.leftMargin = (int) (positionOffset * mScreen + mCurrentPageIndex * mScreen);
                }
                else if (mCurrentPageIndex == 3 && position == 2) {
                    lp.leftMargin = (int) (mCurrentPageIndex * mScreen + (positionOffset - 1) * mScreen);
                }
                mTabLine.setLayoutParams(lp);
            }

            @Override
            public void onPageSelected(int position) {
                resetTextView();
                switch (position){
                    case 0:
                        information_text.setTextColor(Color.parseColor("#008000"));
                        break;
                    case 1:
                        elite_text.setTextColor(Color.parseColor("#008000"));
                        break;
                    case 2:
                        blog_text.setTextColor(Color.parseColor("#008000"));
                        break;
                    case 3:
                        special_text.setTextColor(Color.parseColor("#008000"));
                        break;
                }
                mCurrentPageIndex = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
     /**
     * 初始化TabLine
     */
    public void initTabLine(){
        mTabLine=(ImageView)activity.findViewById(R.id.id_iv_tabline);
        information_text=(TextView)activity.findViewById(R.id.information);
        elite_text=(TextView)activity.findViewById(R.id.elite);
        blog_text=(TextView)activity.findViewById(R.id.blog);
        special_text=(TextView)activity.findViewById(R.id.special);
        WindowManager manager=(WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics=new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(metrics);
        mScreen=metrics.widthPixels/4;
        LinearLayout.LayoutParams lp=(LinearLayout.LayoutParams)mTabLine.getLayoutParams();
        lp.weight=mScreen;
        mTabLine.setLayoutParams(lp);
    }
    public void textListener(){
        information_text.setOnClickListener(onClickListener);
        elite_text.setOnClickListener(onClickListener);
        blog_text.setOnClickListener(onClickListener);
        special_text.setOnClickListener(onClickListener);
    }

    protected void resetTextView() {
        information_text.setTextColor(Color.BLACK);
        elite_text.setTextColor(Color.BLACK);
        blog_text.setTextColor(Color.BLACK);
        special_text.setTextColor(Color.BLACK);
    }
}
