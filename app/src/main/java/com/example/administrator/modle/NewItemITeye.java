package com.example.administrator.modle;

import com.example.administrator.bean.News;
import com.example.administrator.bean.NewsDto;
import com.example.administrator.bean.NewsItem;
import com.example.administrator.contant.Constant;
import com.example.administrator.util.AppUtil;
import com.example.administrator.util.CommonExecption;
import com.example.administrator.util.DataUtil;
import com.example.administrator.util.ITEYE_URLUtil;
import com.example.administrator.util.ITeyeDataUtil;
import com.example.administrator.util.URLUtil;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by Administrator on 2015/12/26.
 */
public class NewItemITeye {

    public List<NewsItem> getNewsItems(int newsType, int currentPage, int useAgentNum)
            throws CommonExecption {
        String urlStr = ITEYE_URLUtil.generateUrl(newsType, currentPage);
        String htmlStr = ITeyeDataUtil.doGet1(urlStr, newsType, currentPage, 12);
        List<NewsItem> newsItems = new ArrayList<NewsItem>();
        NewsItem newsItem = null;

        Document doc = Jsoup.parse(htmlStr);
        Elements units = doc.getElementsByClass("content");

        for (int i = 0; i < units.size(); i++) {
            newsItem = new NewsItem();
            newsItem.setNewsType(newsType);

            Element unit_ele = units.get(i);

            Element h3_ele = unit_ele.getElementsByTag("h3").get(0);
            //解析时间
            Element span_ele = null;
            switch (newsType) {
                case Constant.NEWS_TYPE_NEW:
                    Element a_ele = h3_ele.getElementsByTag("a").get(1);

                    String title = a_ele.text();
                    title = AppUtil.encoding(title, "utf-8");
                    newsItem.setTitle(title);
                    String href = a_ele.attr("href");
                    href = AppUtil.encoding(href, "utf-8");
                    StringBuffer sb = new StringBuffer();
                    sb.append("http://www.iteye.com").append(href);
                    newsItem.setLink(sb.toString());

                    Element div_ele = unit_ele.getElementsByTag("div").get(3);
                    if (div_ele.getElementsByTag("span").size() >= 3) {
                        span_ele = div_ele.getElementsByTag("span").get(2);
                    } else {
                        span_ele = div_ele.getElementsByTag("span").get(1);
                    }
                    //获取图片链接
                    try {// 可能没有图片
                        Element img_ele = h3_ele.child(0);
                        String imgLink = img_ele.attr("src");
                        // System.out.println(imgLink);
                        imgLink = AppUtil.encoding(imgLink, "utf-8");
                        newsItem.setPicLink(imgLink);
                    } catch (IndexOutOfBoundsException e) {
                        System.out.println("没有图片");
                    }
                    break;
                case Constant.NEWS_TYPE_BLOGS:
                    Element a_ele1 = h3_ele.getElementsByTag("a").get(1);
                    //	System.out.println("a_ele----------->"+a_ele.toString());
                    String title1 = a_ele1.text();
                    title1 = AppUtil.encoding(title1, "utf-8");
                    newsItem.setTitle(title1);
                    String href1 = a_ele1.attr("href");
                    href1 = AppUtil.encoding(href1, "utf-8");
                    newsItem.setLink(href1);
                    Element div_ele2 = unit_ele.getElementsByTag("div").get(4);
                    if (div_ele2.getElementsByTag("span").size() >= 3) {
                        span_ele = div_ele2.getElementsByTag("span").get(4);
                    } else {
                        span_ele = div_ele2.getElementsByTag("span").get(1);
                    }
                    //获取图片链接
                    try {// 可能没有图片
                        Element img_ele = unit_ele.getElementsByTag("div").get(2);
                        Element a1_ele = img_ele.getElementsByTag("a").get(0);
                        Element img = a1_ele.getElementsByTag("img").get(0);
                        String imgLink = img.attr("src");
                        imgLink = AppUtil.encoding(imgLink, "utf-8");
                        newsItem.setPicLink(imgLink);
                        //	System.out.println("img--------"+imgLink);
                    } catch (IndexOutOfBoundsException e) {
                        System.out.println("没有图片");
                    }
                    break;
                case Constant.NEWS_TYPE_MAGAZINES:
                    Element a_ele2 = h3_ele.getElementsByTag("a").get(0);
                    String title2 = a_ele2.text();
                    title2 = AppUtil.encoding(title2, "utf-8");
                    newsItem.setTitle(title2);
                    String href2 = a_ele2.attr("href");
                    href2 = AppUtil.encoding(href2, "utf-8");
                    StringBuffer sb_href = new StringBuffer();
                    sb_href.append("http://www.iteye.com").append(href2);
                    //System.out.println("sb_href-------"+sb_href.toString());
                    newsItem.setLink(sb_href.toString());

                    Element div_ele3 = unit_ele.getElementsByTag("div").get(3);
                    if (div_ele3.getElementsByTag("span").size() >= 3) {
                        span_ele = div_ele3.getElementsByTag("span").get(2);
                    } else {
                        span_ele = div_ele3.getElementsByTag("span").get(1);
                    }
                    //获取图片链接
                    try {// 可能没有图片
                        Element img_ele = h3_ele.child(0);
                        String imgLink = img_ele.attr("src");
                        // System.out.println(imgLink);
                        imgLink = AppUtil.encoding(imgLink, "utf-8");
                        newsItem.setPicLink(imgLink);
                    } catch (IndexOutOfBoundsException e) {
                        System.out.println("没有图片");
                    }
                    break;
                case Constant.NEWS_TYPE_SUBJECTS:
                    Element a_ele3 = h3_ele.getElementsByTag("a").get(1);
                    //	System.out.println("a_ele----------->"+a_ele.toString());
                    String title3 = a_ele3.text();
                    title3 = AppUtil.encoding(title3, "utf-8");
                    newsItem.setTitle(title3);
                    String href3 = a_ele3.attr("href");
                    href3 = AppUtil.encoding(href3, "utf-8");
                    Element a_ele_3 = h3_ele.getElementsByTag("a").get(1);
                    //	System.out.println("a_ele----------->"+a_ele.toString());
                    newsItem.setLink(href3);
                    Element div_ele1 = unit_ele.getElementsByTag("div").get(4);
                    if (div_ele1.getElementsByTag("span").size() >= 3) {
                        span_ele = div_ele1.getElementsByTag("span").get(1);
                    } else {
                        span_ele = div_ele1.getElementsByTag("span").get(2);
                    }
                    //获取图片链接
                    try {// 可能没有图片
                        Element img_ele = unit_ele.getElementsByTag("div").get(2);
                        Element a1_ele = img_ele.getElementsByTag("a").get(0);
                        Element img = a1_ele.getElementsByTag("img").get(0);
                        String imgLink = img.attr("src");
                        imgLink = AppUtil.encoding(imgLink, "utf-8");
                        newsItem.setPicLink(imgLink);
                    } catch (IndexOutOfBoundsException e) {
                        System.out.println("没有图片");
                    }
                    break;

            }

            String date = span_ele.text();
            date = AppUtil.encoding(date, "utf-8");
            StringBuffer date_buffer = new StringBuffer();
            date_buffer.append("发布于").append(" ").append(date);
            newsItem.setDate(date_buffer.toString());

            Element h1_ele = unit_ele.getElementsByTag("div").get(1);
            String content = h1_ele.text();
            content = AppUtil.encoding(content, "utf-8");
            // System.out.println("h1_ele---------->"+content);
            // System.out.println("--------"+content);
            newsItem.setContent(content);
            newsItems.add(newsItem);
        }
        return newsItems;
    }

    public NewsDto getNewsDevelopment1(String urlStr) throws CommonExecption {

        NewsDto newsDto = new NewsDto();
        List<News> newses = new ArrayList<News>();
        String htmlStr = ITeyeDataUtil.doGet(urlStr);
        //   System.out.println("title------"+htmlStr);
        Document doc = Jsoup.parse(htmlStr);
        News news = null;
        if (doc.select(".title").size() > 0) {
            Element detailEle = doc.select(".title").get(0);

            String title = detailEle.getElementsByTag("a").get(0).text();
            news = new News();

            news.setTitle(title);
            news.setType(Constant.TITLE);
            newses.add(news);
        }
        if (doc.select(".title_block").size() > 0) {
            Element element = doc.select(".title_block").get(0);
            String title = element.getElementsByTag("a").get(1).text();
            news = new News();

            news.setTitle(title);
            news.setType(Constant.TITLE);
            newses.add(news);
        }
        if (doc.select(".blog_title").size() > 0) {
            Element element = doc.select(".blog_title").get(0);
            String title = element.getElementsByTag("a").get(0).text();
            news = new News();
            news.setContent(title);
            news.setType(Constant.CONTENT);
            newses.add(news);
        }
        if (doc.select(".blog_content").size() > 0) {
            Element element = doc.select(".blog_content").get(0);
                String content = element.text();
                String[] content1 = content.split("。");
               for (int i=0;i<content1.length;i++){
                   news=new News();
                   news.setType(Constant.CONTENT);
                   news.setContent(content1[i]);
                   newses.add(news);
               }
        }
        if (doc.getElementById("news_content") != null) {
            Element element = doc.getElementById("news_content");
            String content1 = element.text();
            String[] content2 = content1.split("。");
            for (int i = 0; i < content2.length; i++) {
                news = new News();
                news.setContent(content2[i] + "。");
                news.setType(Constant.CONTENT);
                newses.add(news);
            }
            if (doc.getElementsByTag("li").size() < 0) {
                news = new News();
                StringBuffer sb = new StringBuffer();
                String content = sb.append(doc.getElementsByTag("li").text()).append("\n").toString();
                news.setContent(content);
                news.setType(Constant.CONTENT);
                newses.add(news);
            }
            if (doc.getElementsByTag("img").size() > 0) {
                Elements imgEles = element.getElementsByTag("img");

                if (imgEles.size() > 0) {
                    for (Element imgEle : imgEles) {
                        if (imgEle.attr("src").equals(""))
                            continue;
                        news = new News();
                        news.setImageLink(imgEle.attr("src"));
                        news.setType(Constant.IMG);
                        newses.add(news);
                    }
                }

                imgEles.remove();

            }
        }
        if (doc.getElementById("interview_main") != null) {
            Element element = doc.getElementById("interview_main");
            if (doc.getElementById("interview_menu") != null) {
                Element element2 = doc.getElementById("interview_menu");
                String content = element2.getElementsByTag("h4").text();
                news = new News();
                news.setType(Constant.CONTENT);
                news.setContent(content);
                newses.add(news);
            }
                if (element.getElementsByTag("li").size() > 0) {
                    Elements elements = element.getElementsByTag("li");
                    for (Element element1 : elements) {
                        news = new News();
                        news.setType(Constant.CONTENT);
                        news.setContent(element1.getElementsByTag("li").text());
                        newses.add(news);
                    }
                }

            if (element.getElementsByTag("h2") != null) {
                Elements element1=element.getElementsByTag("h2");
                for (Element element2 :element1)
                news = new News();
                news.setType(Constant.CONTENT);
                news.setContent(element1.text());
                newses.add(news);
            }
        }
        if (doc.select(".news_content").size() > 0) {
            Elements contentEle31 = doc.select(".news_content");

            for (Element element3 : contentEle31) {

                if (element3.getElementsByTag("div").size() != 0) {

                    if (element3.getElementsByTag("img").size() != 0) {

                        Elements imgEles = element3.getElementsByTag("img");
                        if (imgEles.size() > 0) {

                            for (Element imgEle : imgEles) {
                                if (imgEle.attr("src").equals(""))
                                    continue;
                                news = new News();
                                news.setImageLink(imgEle.attr("src"));
                                news.setType(Constant.IMG);
                                newses.add(news);
                                imgEles.remove();
                            }
                        }

                    }
                    String content = element3.getElementsByTag("div").get(0).text();
                    byte[] bytes = {(byte) 0xC2, (byte) 0xA0};
                    try {
                        String UTFSpace = new String(bytes, "utf-8");
                        content = content.replace(UTFSpace, " ");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    String[] content2 = content.split("。");
                    for (int i = 0; i < content2.length; i++) {
                        news = new News();
                        news.setContent(content2[i]);
                        news.setType(Constant.CONTENT);
                        newses.add(news);
                    }
                }


            }
        }


        newsDto.setNewses(newses);
        return newsDto;
    }

    public List<NewsItem> getSubjectItem(String html){
        List<NewsItem> list=new ArrayList<>();
        NewsItem newsItem;
        Document doc=Jsoup.parse(html);
       if ( doc.getElementsByClass("content").size() >0){
           Elements element= doc.getElementsByClass("content");
           for (Element element1 :element){
               newsItem=new NewsItem();
               String title=element1.getElementsByTag("h3").text();
               newsItem.setTitle(title);
               String url=element1.getElementsByTag("a").get(0).attr("href");
               newsItem.setLink(url);
               String content=element1.getElementsByTag("div").get(1).text();
               newsItem.setContent(content);
               Element element2=element1.select(".blog_info").get(0);
               String date=element2.getElementsByClass("date").text();
               newsItem.setDate(date);
               list.add(newsItem);
           }
       }
        return list;
    }

    public NewsDto getSubjectDTO(String html) {
        NewsDto newsDto = new NewsDto();
        List<News> newses = new ArrayList<News>();
        News news = null;
        Document doc = Jsoup.parse(html);
        Elements elements = doc.select(".blog_main");
        for (Element element : elements) {
            if (element.getElementsByClass("blog_title").size() > 0) {
                Element element1 = element.getElementsByClass("blog_title").get(0);
                String title = element1.getElementsByTag("a").get(0).text();
                news = new News();
                news.setType(Constant.TITLE);
                news.setTitle(title);
                newses.add(news);
            }
            if (element.getElementsByClass("blog_content").size() > 0) {
                Element element1 = element.getElementsByClass("blog_content").get(0);
                if (element1.getElementsByTag("p").size() > 0) {
                    Elements elements1 = element1.getElementsByTag("p");
                    for (Element element2 : elements1) {
                        if (!element2.text().equals(" ") ||element2.text()!=null) {
                            news = new News();
                            news.setType(Constant.CONTENT);
                            news.setContent(element2.text());
                            newses.add(news);
                        }
                    }
                    if (element1.getElementsByTag("img").size()>0){
                        Elements elements2=element1.getElementsByTag("img");
                        for (Element element2 :elements2){
                            Element element3=element2.getElementsByTag("img").get(0);
                            String image=element3.attr("src");
                            news=new News();
                            news.setType(Constant.IMG);
                            news.setImageLink(image);
                            newses.add(news);
                        }
                    }
                }

            }
        }
        newsDto.setNewses(newses);
        return newsDto;
    }
}
