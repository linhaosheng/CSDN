package com.example.administrator.util;

import android.content.Context;
import android.text.TextUtils;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2015/11/13.
 */
public class AppUtil {

    /**
     * 根据时间类型获取上次更新的时间
     * @param context
     * @param newsType
     * @return
     */
    public static String getRefreashTime(Context context,int newsType){
        String timeStr=PreferenceUtil.readString(context,"NEWS_"+newsType);
        if(TextUtils.isEmpty(timeStr)){
            return "忘记了";
        }
        return timeStr;
    }

    /**
     * 根据时间类型设置上次更新的时间
     * @param context
     * @param newsType
     */
    public static void setRefreashTime(Context context,int newsType){
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        PreferenceUtil.write(context,"NEWS_"+newsType,df.format(new Date()));
    }

    /**
     * 转换空格
     * @param text
     * @return
     */
    public static String encoding(String text,String code){
        byte [] bytes ={(byte) 0xC2,(byte) 0xA0};
        try {
            String UTFSpace=new String(bytes, code);
            text=text.replace(UTFSpace, " ");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return text;
    }
}
