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
public class CSDNActivity {

    private TextView industry_text;
    private TextView mobile_text;
    private TextView mazagine_text;
    private TextView research_text;
    private TextView cloud_text;
    private ImageView mTabLine;
    private Context context;
    private int mCurrentPageIndex;
    Activity activity;
    private int mScreen;
    private ViewPager mViewPage;
    private View.OnClickListener onClickListener;

    public CSDNActivity(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
    }

    public void setViewPage(ViewPager mViewPage) {
        this.mViewPage = mViewPage;
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public void csdnClick(View v) {

        int position = v.getId();
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mTabLine.getLayoutParams();
        switch (position) {
            case R.id.industry:
                resetTextView();
                mViewPage.setCurrentItem(0);
                lp.leftMargin = (int) (0 * mScreen);
                industry_text.setTextColor(Color.parseColor("#008000"));
                mTabLine.setLayoutParams(lp);
                break;
            case R.id.mobile:
                resetTextView();
                mViewPage.setCurrentItem(1);
                mobile_text.setTextColor(Color.parseColor("#008000"));
                lp.leftMargin = (int) (1 * mScreen);
                mTabLine.setLayoutParams(lp);
                break;
            case R.id.research:
                resetTextView();
                mViewPage.setCurrentItem(2);
                research_text.setTextColor(Color.parseColor("#008000"));
                lp.leftMargin = (int) (2 * mScreen);
                mTabLine.setLayoutParams(lp);
                break;
            case R.id.mazagine:
                resetTextView();
                mViewPage.setCurrentItem(3);
                mazagine_text.setTextColor(Color.parseColor("#008000"));
                lp.leftMargin = (int) (3 * mScreen);
                mTabLine.setLayoutParams(lp);
                break;
            case R.id.clour:
                resetTextView();
                mViewPage.setCurrentItem(4);
                cloud_text.setTextColor(Color.parseColor("#008000"));
                lp.leftMargin = (int) (4 * mScreen);
                mTabLine.setLayoutParams(lp);
                break;
        }
    }

    public void initViewPage() {

        resetTextView();
        industry_text.setTextColor(Color.parseColor("#008000"));
        mViewPage.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mTabLine.getLayoutParams();
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
                } else if (mCurrentPageIndex == 3 && position == 2) {
                    lp.leftMargin = (int) (mCurrentPageIndex * mScreen + (positionOffset - 1) * mScreen);
                } else if (mCurrentPageIndex == 3 && position == 3) {    //2->3
                    lp.leftMargin = (int) (positionOffset * mScreen + mCurrentPageIndex * mScreen);
                } else if (mCurrentPageIndex == 4 && position == 3) {
                    lp.leftMargin = (int) (mCurrentPageIndex * mScreen + (positionOffset - 1) * mScreen);
                }
                mTabLine.setLayoutParams(lp);
            }

            @Override
            public void onPageSelected(int position) {
                resetTextView();
                switch (position) {
                    case 0:
                        industry_text.setTextColor(Color.parseColor("#008000"));
                        break;
                    case 1:
                        mobile_text.setTextColor(Color.parseColor("#008000"));
                        break;
                    case 2:
                        research_text.setTextColor(Color.parseColor("#008000"));
                        break;
                    case 3:
                        mazagine_text.setTextColor(Color.parseColor("#008000"));
                        break;
                    case 4:
                        cloud_text.setTextColor(Color.parseColor("#008000"));
                        break;
                }
                mCurrentPageIndex = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public void initTabLine() {
        mTabLine = (ImageView) activity.findViewById(R.id.id_iv_tabline);
        industry_text = (TextView) activity.findViewById(R.id.industry);
        mobile_text = (TextView) activity.findViewById(R.id.mobile);
        research_text = (TextView) activity.findViewById(R.id.research);
        mazagine_text = (TextView) activity.findViewById(R.id.mazagine);
        cloud_text = (TextView) activity.findViewById(R.id.clour);
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(metrics);
        mScreen = metrics.widthPixels / 5;
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mTabLine.getLayoutParams();
        lp.weight = mScreen;
        mTabLine.setLayoutParams(lp);
    }

    public void textListener() {
        industry_text.setOnClickListener(onClickListener);
        mobile_text.setOnClickListener(onClickListener);
        research_text.setOnClickListener(onClickListener);
        mazagine_text.setOnClickListener(onClickListener);
        cloud_text.setOnClickListener(onClickListener);
    }

    protected void resetTextView() {
        industry_text.setTextColor(Color.BLACK);
        mobile_text.setTextColor(Color.BLACK);
        research_text.setTextColor(Color.BLACK);
        mazagine_text.setTextColor(Color.BLACK);
        cloud_text.setTextColor(Color.BLACK);
    }
}
