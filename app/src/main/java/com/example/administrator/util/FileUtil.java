package com.example.administrator.util;

import android.graphics.Bitmap;
import android.os.Environment;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * Created by Administrator on 2015/12/29.
 */
public class FileUtil {

    public static String filePath = Environment.getExternalStorageDirectory() + "/ITDownLoad";

    /**
     * 通过文件的url来获取文件的名
     *
     * @param url
     * @return
     */
    public static String getFileName(String url) {
        // 去除url中的符号作为文件名返回
        url = url.replaceAll("(?i)[^a-zA-Z0-9\u4E00-\u9FA5]", "");
        System.out.println("filename = " + url);
        return url + ".png";
    }

    public static void writeSDCard(String fileName, InputStream inputStream) {
        try {
            File file = new File(fileName);
            if (!file.exists()) {
                file.mkdirs();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(filePath + "/" + fileName);
            byte[] buff = new byte[1024];
            int length;
            while ((length = inputStream.read(buff)) != -1) {
                fileOutputStream.write(buff, 0, length);
            }
            fileOutputStream.flush();
            fileOutputStream.close();
            inputStream.close();
            System.out.println("writeToSD success");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("writeToSD fail");
        }
    }

    /**
     * 将图片写入到SDcard中
     * @param fileName
     * @param bitmap
     * @return
     */
    public static boolean writeSDcard(String fileName, Bitmap bitmap) {
        try {
            File file = new File(fileName);
            if (!file.exists()) {
                file.mkdirs();
            }
            File imgFile = new File(filePath + "/" + getFileName(fileName));
            if(imgFile.exists()){
                return true;
            }
            InputStream is=bitmapToBytes(bitmap);
            FileOutputStream fileOutputStream=new FileOutputStream(imgFile);
            byte[] bytes=new byte[1024];
            int length=0;
            while ((length=is.read(bytes))!=-1){
                fileOutputStream.write(bytes,0,length);
            }
            fileOutputStream.flush();
            fileOutputStream.close();
            is.close();
            System.out.println("writeToSD success");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * 将bitmap转换为inputstream
     * @param bitmap
     * @return
     */
    public static InputStream bitmapToBytes(Bitmap bitmap){
        ByteArrayOutputStream bos=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,bos);
        InputStream is=new ByteArrayInputStream(bos.toByteArray());
        return is;
    }
}
