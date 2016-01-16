package com.example.administrator.activity;


import android.os.Bundle;
import android.content.Intent;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.widget.ImageView;

import com.example.administrator.csdn.R;


public class FirstActivity extends BaseActivityImpl {

    private ImageView init_pic;
    private ImageView cto_pic;
    private ImageView blog_house_pic;
    private ImageView iteye_pic;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getBundleExtra("IT");
        final int num = bundle.getInt("information");
        switch (num) {
            case 1:
                setContentView(R.layout.first);
                init_pic = (ImageView) findViewById(R.id.init_pic);
                break;
            case 2:
                setContentView(R.layout.welecome_cto);
                cto_pic = (ImageView) findViewById(R.id.cto_pic);
                break;
            case 3:
                setContentView(R.layout.welecome_bloghouse);
                blog_house_pic = (ImageView) findViewById(R.id.blog_house_pic);
                break;
            case 4:
                setContentView(R.layout.welecome_iteye);
                iteye_pic = (ImageView) findViewById(R.id.iteye_pic);
                break;
        }
        //    setContentView(R.layout.first);
        getCSDNApplication().addActivity(this);
        //     init_pic=(ImageView)findViewById(R.id.init_pic);
        //true 表示使用Animation的interpolator，false则是使用自己的
        final AnimationSet animationSet = new AnimationSet(true);
        //设置动画的透明参数
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
        alphaAnimation.setDuration(3000);
        animationSet.addAnimation(alphaAnimation);
        switch (num) {
            case 1:
                init_pic.startAnimation(animationSet);
                break;
            case 2:
                cto_pic.startAnimation(animationSet);
                break;
            case 3:
                blog_house_pic.startAnimation(animationSet);
                break;
            case 4:
                iteye_pic.startAnimation(animationSet);
                break;
        }
        animationSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Intent intent = new Intent(FirstActivity.this, MainActivity.class);
                Bundle bundle = new Bundle();
                intent.putExtra("IT_select", bundle);
                switch (num) {
                    case 1:
                        bundle.putInt("select", 1);
                        startActivity(intent);
                        break;
                    case 2:
                        bundle.putInt("select", 2);
                        startActivity(intent);
                        break;
                    case 3:
                        bundle.putInt("select", 3);
                        startActivity(intent);
                        break;
                    case 4:
                        bundle.putInt("select", 4);
                        startActivity(intent);
                        break;
                }

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}
