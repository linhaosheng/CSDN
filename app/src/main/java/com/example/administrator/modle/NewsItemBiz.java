package com.example.administrator.modle;

import com.example.administrator.bean.News;
import com.example.administrator.bean.NewsDto;
import com.example.administrator.bean.NewsItem;
import com.example.administrator.contant.Constant;
import com.example.administrator.util.AppUtil;
import com.example.administrator.util.CommonExecption;
import com.example.administrator.util.DataUtil;
import com.example.administrator.util.URLUtil;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/11/13.
 * 处理NewItem的业务类
 */
public class NewsItemBiz {

    public List<NewsItem> getNewsItems(int newTypes, int currentPage) throws CommonExecption {
        String urlStr = URLUtil.generateUrl(newTypes, currentPage);
        String htmlStr = DataUtil.doGet(urlStr, "UTF-8");

        List<NewsItem> newsItems = new ArrayList<NewsItem>();
        NewsItem newsItem = null;
        Document doc = Jsoup.parse(htmlStr);
        Elements units = doc.getElementsByClass("unit");
        for (int i = 0; i < units.size(); i++) {
            newsItem = new NewsItem();
            newsItem.setNewsType(newTypes);

            Element unit_ele = units.get(i);
            Element h1_ele = unit_ele.getElementsByTag("h1").get(0);
            Element h1_a_ele = h1_ele.child(0);
            String title = h1_a_ele.text();
            title = AppUtil.encoding(title,"utf-8");
            String hred = h1_a_ele.attr("href");
            hred = AppUtil.encoding(hred,"utf-8");
            newsItem.setLink(hred);
            newsItem.setTitle(title);

            Element h4_ele = unit_ele.getElementsByTag("h4").get(0);
            Element ago_ele = h4_ele.getElementsByClass("ago").get(0);
            String date = ago_ele.text();
            date = AppUtil.encoding(date,"utf-8");
            newsItem.setDate(date);

            Element d1_ele = unit_ele.getElementsByTag("dl").get(0);
            Element dt_ele = d1_ele.child(0);

            try {
                Element img_ele = dt_ele.child(0);
                String imgLink = img_ele.child(0).attr("src");
                //    System.out.println("link--------"+imgLink);
                imgLink = AppUtil.encoding(imgLink,"utf-8");
                newsItem.setPicLink(imgLink);
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
            }
            Element content_ele = d1_ele.child(1);
            String content = content_ele.text();
            content = AppUtil.encoding(content,"utf-8");
            newsItem.setContent(content);
            newsItems.add(newsItem);
        }
        return newsItems;
    }

    public NewsDto getNews(String urlStr) throws CommonExecption {
        NewsDto newsDto = new NewsDto();
        List<News> newses = new ArrayList<>();
        String htmlStr = DataUtil.doGet(urlStr, "UTF-8");
        Document doc = Jsoup.parse(htmlStr);

        // 获得文章中的第一个detail
    //    System.out.println(htmlStr);
        Element detailEle = doc.select(".left .detail").get(0);
        // 标题
        Element titleEle = detailEle.select("h1.title").get(0);
        News news = new News();
        String title = titleEle.text();
        title = AppUtil.encoding(title,"utf-8");
        news.setTitle(title);
        news.setType(Constant.TITLE);
        newses.add(news);
        // 摘要
        Element summaryEle = detailEle.select("div.summary").get(0);
        news = new News();
        String summary = summaryEle.text();
        summary = AppUtil.encoding(summary,"utf-8");
        news.setSumary(summary);
        news.setType(Constant.SUMMARY);
        newses.add(news);
        // 内容
        Element contentEle = detailEle.select("div.con.news_content").get(0);
        Elements childrenEle = contentEle.children();

        for (Element child : childrenEle) {
            Elements imgEles = child.getElementsByTag("img");
            // 图片
            if (imgEles.size() > 0) {
                for (Element imgEle : imgEles) {
                    if (imgEle.attr("src").equals(""))
                        continue;
                    news = new News();
                    String imgLink = imgEle.attr("src");
                    imgLink = AppUtil.encoding(imgLink,"utf-8");
                    news.setImageLink(imgLink);
                    news.setType(Constant.IMG);
                    newses.add(news);
                }
            }
            // 移除图片
            imgEles.remove();

            if (child.text().equals(""))
                continue;

            news = new News();
            news.setType(Constant.CONTENT);

            try {
                if (child.children().size() == 1) {
                    Element cc = child.child(0);
                    if (cc.tagName().equals("b")) {
                        news.setType(Constant.BOLD_TITLE);
                    }
                }

            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
            }
            String content = child.outerHtml();
            content = AppUtil.encoding(content,"utf-8");
            news.setContent(content);
            newses.add(news);
        }
        newsDto.setNewses(newses);
        return newsDto;
    }

    public NewsDto getNews1(String urlStr) throws CommonExecption {
        NewsDto newsDto = new NewsDto();
        List<News> newses = new ArrayList<>();
        String htmlStr = DataUtil.doGet(urlStr, "UTF-8");
        Document doc = Jsoup.parse(htmlStr);

        // 获得文章中的第一个detail
        System.out.println(htmlStr);
        Element detailEle = doc.select(".wrapper").get(0);
        // 标题
        Element title1 = detailEle.select("h1").get(0);
        News news = new News();
        String title = title1.text();
        title = AppUtil.encoding(title,"utf-8");
        news.setTitle(title);
        news.setType(Constant.TITLE);
        newses.add(news);

        Element detailSum = doc.select(".share").get(0);
        //    Element summary=detailSum.select("wb:share-button").get(0);
        //    String sum[]=summary.attr("title").split("-");
        news = new News();
        news.setSumary(detailSum.text());
        news.setType(Constant.SUMMARY);
        newses.add(news);
        // 内容
        Element contentEle = detailEle.select("div.text").get(0);
        Elements childrenEle = contentEle.children();

        for (Element child : childrenEle) {
            Elements imgEles = child.getElementsByTag("img");
            // 图片
            if (imgEles.size() > 0) {
                for (Element imgEle : imgEles) {
                    if (imgEle.attr("src").equals(""))
                        continue;
                    news = new News();
                    String imgLink = imgEle.attr("src");
                    imgLink = AppUtil.encoding(imgLink,"utf-8");
                    news.setImageLink(imgLink);
                    news.setType(Constant.IMG);
                    newses.add(news);
                }
            }
            // 移除图片
            imgEles.remove();

            if (child.text().equals(""))
                continue;

            news = new News();
            news.setType(Constant.CONTENT);

            try {
                if (child.children().size() == 1) {
                    Element cc = child.child(0);
                    if (cc.tagName().equals("b")) {
                        news.setType(Constant.BOLD_TITLE);
                    }
                }

            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
            }
            String content = child.getElementsByTag("p").text();
            content = AppUtil.encoding(content,"utf-8");
            news.setContent(content);
            newses.add(news);
        }
        newsDto.setNewses(newses);
        return newsDto;
    }


}
