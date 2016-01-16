package com.example.administrator.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.administrator.csdn.R;

/**
 * Created by Administrator on 2015/12/24.
 */
public class SelectActivity extends BaseActivityImpl implements View.OnClickListener {

    private ImageButton csdn_img;
    private ImageButton cto_img;
    private ImageButton blog_houde_img;
    private ImageButton iteye_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select);
        csdn_img = (ImageButton) findViewById(R.id.csdn);
        cto_img = (ImageButton) findViewById(R.id.cto);
        blog_houde_img = (ImageButton) findViewById(R.id.blog_house);
        iteye_img = (ImageButton) findViewById(R.id.iteye);

        csdn_img.setOnClickListener(this);
        cto_img.setOnClickListener(this);
        blog_houde_img.setOnClickListener(this);
        iteye_img.setOnClickListener(this);

        getCSDNApplication().addActivity(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        intent.putExtra("IT", bundle);
        switch (v.getId()) {
            case R.id.csdn:
                bundle.putInt("information", 1);
                intent.setClass(this, FirstActivity.class);
                startActivity(intent);
                break;
            case R.id.cto:
                bundle.putInt("information", 2);
                intent.setClass(this, FirstActivity.class);
                startActivity(intent);
                break;
            case R.id.blog_house:
                bundle.putInt("information", 3);
                intent.setClass(this, FirstActivity.class);
                startActivity(intent);
                break;
            case R.id.iteye:
                bundle.putInt("information", 4);
                intent.setClass(this, FirstActivity.class);
                startActivity(intent);
                break;
        }
    }

    private long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                getCSDNApplication().exit();
            }
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }
}
