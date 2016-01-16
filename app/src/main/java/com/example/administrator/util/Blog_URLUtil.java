package com.example.administrator.util;

import com.example.administrator.contant.Constant;

/**
 * Created by Administrator on 2015/12/26.
 */
public class Blog_URLUtil {

    public static final String HOME_URL="http://www.cnblogs.com/#p";                       //首页
    public static final String PICK_URL="http://www.cnblogs.com/pick/#p";                  //精华
    public static final String CANDIDATE_URL="http://www.cnblogs.com/candidate/#p";        //候选
    public static final String NEWS_URL="http://www.cnblogs.com/news/#p";                  //新闻

    /**
     * 根据文章类型，和当前页码生成url
     * @param newsType
     * @param currentPage
     * @return
     */
    public static String generateUrl(int newsType, int currentPage)
    {
        currentPage = currentPage > 0 ? currentPage : 1;
        String urlStr = "";

        switch (newsType)
        {
            case Constant.NEWS_TYPE_HOME:
                urlStr = HOME_URL;
                break;
            case Constant.NEWS_TYPE_PICK:
                urlStr = PICK_URL;
                break;
            case Constant.NEWS_TYPE_CANDIDATE:
                urlStr =  CANDIDATE_URL;
                break;
            case Constant.NEWS_TYPE_NEWS:
                urlStr = NEWS_URL;
                break;
            default:
                break;
        }

        urlStr += ""+currentPage;
        return urlStr;

    }

}
