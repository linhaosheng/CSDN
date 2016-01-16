package com.example.administrator.util;
import com.example.administrator.contant.Constant;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;



/**
 * Created by Administrator on 2015/12/26.
 */
public class BlogHouseDataUtil {

    /**
     * 返回该链接地址的html数据
     *
     * @param urlStr
     * @return
     * @throws
     */
    public static String doGet(String urlStr,int currentPage,int newType) throws CommonExecption
    {
        StringBuffer sb = new StringBuffer();
        try
        {

            HttpClient client=new HttpClient();
            PostMethod post =new PostMethod(urlStr);
            switch(newType){
                case Constant.NEWS_TYPE_HOME:
                    post.addParameter("CategoryType", "SiteHome");
                    post.addParameter("CategoryId",String.valueOf(808));
                    post.addParameter("ItemListActionName", "PostList");
                    break;
                case Constant.NEWS_TYPE_PICK:
                    post.addParameter("CategoryType", "Picked");
                    post.addParameter("CategoryId",String.valueOf(-2));
                    post.addParameter("ItemListActionName", "PostList");
                    break;
                case Constant.NEWS_TYPE_CANDIDATE:
                    post.addParameter("CategoryType", "HomeCandidate");
                    post.addParameter("CategoryId",String.valueOf(108697));
                    post.addParameter("ItemListActionName", "PostList");
                    break;
                case Constant.NEWS_TYPE_NEWS:
                    post.addParameter("CategoryType", "News");
                    post.addParameter("CategoryId",String.valueOf(-1));
                    post.addParameter("ItemListActionName", "NewsList");
                    break;

            }

            post.addParameter("PageIndex",String.valueOf(currentPage));
            post.addParameter("ParentCategoryId",String.valueOf(0));
            int state=client.executeMethod(post);

            if (state == 200)
            {
                InputStream is = post.getResponseBodyAsStream();
                int len = 0;
                byte[] buf = new byte[1024];

                while ((len = is.read(buf)) != -1)
                {
                    sb.append(new String(buf, 0, len, "UTF-8"));
                }
                is.close();
            } else
            {
                throw new CommonExecption("访问网络失败！");
            }

        } catch (Exception e)
        {
            e.printStackTrace();
            throw new CommonExecption("访问网络失败！");
        }

        return sb.toString();
    }
}
