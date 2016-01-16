package com.example.administrator.test;

import com.example.administrator.bean.News;
import com.example.administrator.bean.NewsDto;
import com.example.administrator.modle.NewsItemBiz;
import com.example.administrator.util.CommonExecption;

import junit.framework.TestCase;

import java.util.List;

/**
 * Created by Administrator on 2015/12/21.
 */
public class Test extends TestCase {

    @org.junit.Test
    public void test() throws Exception {
        //      NewItemITeye itemITeye=new NewItemITeye();
        //   List<NewsItem>list=  itemITeye.getNewsItems(Constant.NEWS_TYPE_NEW, 1, 14);
        //    System.out.println(list.size());
        //  NewsItemBiz biz = new NewsItemBiz();
        NewsItemBiz biz = new NewsItemBiz();

        try {
            NewsDto newsDto = biz.getNews1("http://www.csdn.net/article/2015-12-28/2826563");

            List<News> newses = newsDto.getNewses();
            for (News news : newses) {
                System.out.println(news);
            }
            System.out.println("-----");
            System.out.println(newsDto.getNextPage());
        } catch (CommonExecption e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
