package com.example.administrator.util;

import com.example.administrator.contant.Constant;

/**
 * Created by Administrator on 2015/12/24.
 */
public class CTO_URLUtil {

    public static final String FIRST_URL="http://blog.51cto.com/artcommend";
    public static final String NETWORK_URL="http://blog.51cto.com/artcommend/14";   //网络开发
    public static final String DEVELOP_URL="http://blog.51cto.com/artcommend/8";    //开发技术������
    public static final String ADMIN_URL="http://blog.51cto.com/artcommend/9";       //IT管理���
    public static final String LIFE_URL="http://blog.51cto.com/artcommend/12";        //IT生活���


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
                case Constant.NEWS_TYPE_NETWORK:
                    urlStr = NETWORK_URL;
                    break;
                case Constant.NEWS_TYPE_DEVELOPMENT:
                    urlStr = DEVELOP_URL;
                    break;
                case Constant.NEWS_TYPE_IT_ADMIN:
                    urlStr =  ADMIN_URL;
                    break;
                case Constant.NEWS_TYPE_IT_LIFE:
                    urlStr = LIFE_URL;
                    break;
                default:
                    break;
            }

            urlStr += "/" + currentPage;

            return urlStr;

        }



}
