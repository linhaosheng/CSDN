package com.example.administrator.util;

import com.example.administrator.contant.Constant;


/**
 * Created by Administrator on 2015/11/13.
 */
public class URLUtil {

    public static final String NEWS_LIST_URL = "http://www.csdn.net/headlines.html";
    public static final String NEWS_LIST_URL_YIDONG = "http://mobile.csdn.net/mobile";
    public static final String NEWS_LIST_URL_YANFA = "http://sd.csdn.net/sd";
    public static final String NEWS_LIST_URL_YUNJISUAN = "http://cloud.csdn.net/cloud";
    public static final String NEWS_LIST_URL_ZAZHI = "http://programmer.csdn.net/programmer";
    public static final String NEWS_LIST_URL_YEJIE = "http://news.csdn.net/news";

    public static String generateUrl(int newType,int currentPage){
        currentPage=currentPage>0 ? currentPage :1;
        String urlstr="";
        switch (newType){
            case Constant.NEW_TYPE_YEJIE:
                urlstr=NEWS_LIST_URL_YEJIE;
                break;
            case Constant.NEW_TYPE_YANFA:
                urlstr=NEWS_LIST_URL_YANFA;
                break;

            case Constant.NEW_TYPE_YUNJISUAN:
                urlstr=NEWS_LIST_URL_YUNJISUAN;
                break;
            case Constant.NEW_TYPE_YIDONG:
                urlstr=NEWS_LIST_URL_YIDONG;
                break;
            case Constant.NEW_TYPE_CHENGXUYUAN:
                urlstr=NEWS_LIST_URL_ZAZHI;
                break;
            default:
                urlstr=NEWS_LIST_URL;
        }
        urlstr +="/"+currentPage;
        return urlstr;
    }
}
