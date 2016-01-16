package com.example.administrator.util;

import com.example.administrator.contant.Constant;

/**
 * Created by Administrator on 2015/12/26.
 */
public class ITEYE_URLUtil {
    public static final String NEWS_URL="http://www.iteye.com/news?page=";                       //资讯
    public static final String MAGAZINES_URL="http://www.iteye.com/magazines?page=";            //精华
    public static final String BLOG_URL="http://www.iteye.com/blogs?page=";                    //博客
    public static final String SUBJECTS_URL="http://www.iteye.com/blogs/subjects?page=";      //专栏

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
            case Constant.NEWS_TYPE_NEW:
                urlStr = NEWS_URL;
                break;
            case Constant.NEWS_TYPE_MAGAZINES:
                urlStr = MAGAZINES_URL;
                break;
            case Constant.NEWS_TYPE_BLOGS:
                urlStr =BLOG_URL;
                break;
            case Constant.NEWS_TYPE_SUBJECTS:
                urlStr =SUBJECTS_URL;
                break;
            default:
                break;
        }

        urlStr +=currentPage;
        return urlStr;

    }

}
