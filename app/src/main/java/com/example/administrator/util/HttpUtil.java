package com.example.administrator.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * Created by Administrator on 2015/12/29.
 */
public class HttpUtil {

    public static Bitmap HttpGetImg(String url,Context context){
        HttpGet httpGet=new HttpGet(url);
        BasicHttpParams httpParams=new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParams, 3000);
        HttpConnectionParams.setSoTimeout(httpParams, 3000);
        Bitmap bitmap=null;
        try{
            HttpClient client=new DefaultHttpClient(httpParams);
            HttpResponse response=client.execute(httpGet);
            if(response.getStatusLine().getStatusCode()==200) {
                InputStream is = response.getEntity().getContent();
                byte[] bytes=new byte[1024];
                ByteArrayOutputStream bos=new ByteArrayOutputStream();
                int length=0;
                while ((length=is.read(bytes))!=-1){
                    System.out.println("readImage-----");
                    bos.write(bytes,0,length);
                }
                byte[] bytesArray=bos.toByteArray();
                bitmap= BitmapFactory.decodeByteArray(bytesArray,0,bytesArray.length);
                is.close();
                bos.close();
            }else {
                Toast.makeText(context,"图片下载出错",Toast.LENGTH_LONG).show();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return bitmap;
    }
}
