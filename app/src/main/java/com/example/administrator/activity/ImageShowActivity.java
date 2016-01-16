package com.example.administrator.activity;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.administrator.csdn.R;
import com.example.administrator.util.FileUtil;
import com.example.administrator.util.HttpUtil;
import com.polites.android.GestureImageView;

/**
 * Created by Administrator on 2015/12/29.
 */
public class ImageShowActivity extends BaseActivityImpl {

    private String url;
    private ProgressBar loading;
    private GestureImageView gestureImageView;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_page);

        //拿到图片的URL
        url = getIntent().getExtras().getString("url");
        loading = (ProgressBar) findViewById(R.id.loading);
        gestureImageView = (GestureImageView) findViewById(R.id.image);
        new DownLoadImgTask().execute();
    }

    /**
     * 点击返回按钮
     *
     * @param view
     */
    public void back(View view) {
        finish();
    }

    /**
     * 下载图片
     *
     * @param view
     */
    public void downloadImg(View view) {
        gestureImageView.setDrawingCacheEnabled(true);
        if (FileUtil.writeSDcard(url, gestureImageView.getDrawingCache())) {
            Toast.makeText(getApplicationContext(), "保存成功", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "保存失败", Toast.LENGTH_SHORT).show();
        }
        gestureImageView.setDrawingCacheEnabled(false);     //禁用DrawingCahce否则会影响性能
    }

    class DownLoadImgTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            bitmap = HttpUtil.HttpGetImg(url, getApplicationContext());
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            gestureImageView.setImageBitmap(bitmap);
            loading.setVisibility(View.GONE);
            super.onPostExecute(result);
        }
    }
}
