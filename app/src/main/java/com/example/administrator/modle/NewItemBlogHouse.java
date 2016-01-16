package com.example.administrator.modle;

import com.example.administrator.bean.News;
import com.example.administrator.bean.NewsDto;
import com.example.administrator.bean.NewsItem;
import com.example.administrator.contant.Constant;
import com.example.administrator.util.AppUtil;
import com.example.administrator.util.BlogHouseDataUtil;
import com.example.administrator.util.Blog_URLUtil;
import com.example.administrator.util.CommonExecption;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class NewItemBlogHouse {

    public List<NewsItem> getNewsItems(int newsType, int currentPage) throws CommonExecption {
        String urlStr = Blog_URLUtil.generateUrl(newsType, currentPage);

        String htmlStr = BlogHouseDataUtil.doGet(urlStr, currentPage, newsType);
        System.out.println("htmlStr------" + htmlStr);
        List<NewsItem> newsItems = new ArrayList<NewsItem>();
        NewsItem newsItem = null;
        Document doc = Jsoup.parse(htmlStr);
        Elements units = doc.getElementsByClass("post_item_body");
        //	System.out.println("--------"+units.toString());
        for (int i = 0; i < units.size(); i++) {
            newsItem = new NewsItem();
            newsItem.setNewsType(newsType);

            Element unit_ele = units.get(i);

            Element h1_ele = unit_ele.getElementsByTag("h3").get(0);
            Element h1_a_ele = h1_ele.child(0);
            String title = h1_a_ele.text();
            title = AppUtil.encoding(title, "utf-8");
            String href = h1_a_ele.attr("href");
            href = AppUtil.encoding(href, "utf-8");
            newsItem.setLink(href);
            newsItem.setTitle(title);
            //  	System.out.println("href---------->"+href);
            // 	System.out.println("title---------->"+title);

            Element div_date = unit_ele.getElementsByTag("div").get(1);
            String date = div_date.text();
            //	String span_ele=p_ele.getElementsByTag("span").get(0).text();
            //	System.out.println("---------"+text);
            date = AppUtil.encoding(date, "utf-8");
            newsItem.setDate(date);

            try {// 可能没有图片
                Element div_pic = unit_ele.getElementsByTag("div").get(0);
                Element p_pic = div_pic.getElementsByTag("a").get(1);
                Element img = p_pic.child(0);
                String imgLink = img.attr("src");
                //	System.out.println(imgLink);
                imgLink = AppUtil.encoding(imgLink, "utf-8");
                newsItem.setPicLink(imgLink);
            } catch (IndexOutOfBoundsException e) {
                System.out.println("没有图片");
            }

            Element div_content = unit_ele.getElementsByTag("div").get(0);
            Element p_content = div_content.getElementsByTag("p").get(0);
            String content = p_content.text();
            //	System.out.println("--------"+content);
            content = AppUtil.encoding(content, "utf-8");
            newsItem.setContent(content);
            newsItems.add(newsItem);
        }
        return newsItems;

    }


    public NewsDto getNewsDevelopment1(String urlStr, int cutrrent, int newType) throws CommonExecption {

        NewsDto newsDto = new NewsDto();
        List<News> newses = new ArrayList<News>();
        String htmlStr = BlogHouseDataUtil.doGet(urlStr, cutrrent, newType);
        Document doc = Jsoup.parse(htmlStr);

        // 标题
        Element detailEle = doc.select(".post").get(0);

        String title = doc.getElementById("cb_post_title_url").text();
        title=AppUtil.encoding(title,"utf-8");
        News news = new News();

        news.setTitle(title);
        news.setType(Constant.TITLE);
        newses.add(news);

        //Element contentEle2 = detailEle.select(".postBody").get(0);

        Element contentEle31 = detailEle.getElementById("cnblogs_post_body");
        Elements childrenEle = contentEle31.children();

        for (Element element : childrenEle) {

            if (element.getElementsByTag("h3").size() != 0) {

                news = new News();
                news.setContent(element.getElementsByTag("h3").text());
                news.setType(Constant.CONTENT);
                newses.add(news);
            }

            if (element.getElementsByTag("ul").size() != 0) {
            //    Elements elements = element.getElementsByTag("ul");
            //    for (Element element1 : elements) {
                       news = new News();
                       StringBuffer sb =new StringBuffer();
                    if (element.getElementsByTag("li").text() != null) {
                        String content=element.getElementsByTag("li").text();
                        news.setContent(sb.append(content).append("\n").toString());
                        news.setType(Constant.CONTENT);
                        newses.add(news);
                    } else {
                        news.setContent(element.getElementsByTag("a").text());
                        news.setType(Constant.CONTENT);
                        newses.add(news);
                    }
               // }
            }

            if (element.getElementsByTag("h1").size() != 0) {

                news = new News();
                news.setContent(element.getElementsByTag("h1").text());
                news.setType(Constant.CONTENT);
                newses.add(news);
            }

            if (element.getElementsByTag("h2").size() != 0) {

                news = new News();
                news.setContent(element.getElementsByTag("h2").text());
                news.setType(Constant.CONTENT);
                newses.add(news);
            }

            if (element.getElementsByTag("h4").size() != 0) {

                news = new News();
                news.setContent(element.getElementsByTag("h4").text());
                news.setType(Constant.CONTENT);
                newses.add(news);
            }


            if (element.getElementById("blogCustomTitleStyle") != null) {
                Element contentEle3 = element.getElementById("blogCustomTitleStyle");
                news = new News();
                String content = contentEle3.text();
                content=AppUtil.encoding(content,"gb2312");
                news.setContent(content);
                news.setType(Constant.CONTENT);
                newses.add(news);

            } else if (element.getElementsByTag("p").size() != 0) {
                news = new News();
                String content=element.getElementsByTag("p").text();
                content=AppUtil.encoding(content,"gb2312");
                news.setContent(content);
                news.setType(Constant.CONTENT);
                newses.add(news);
            }


            if (element.getElementById("blogCustomTitleIco") != null) {
                Element contentEle3 = element.getElementById("blogCustomTitleIco");
                news = new News();
                String content = contentEle3.text();
                content=AppUtil.encoding(content,"gb2312");
                news.setContent(content);
                news.setType(Constant.CONTENT);
                newses.add(news);

            }


            if (element.getElementsByTag("img").size() != 0) {

                Elements imgEles = element.getElementsByTag("img");
                // Í¼Æ¬
                if (imgEles.size() > 0) {
                    // System.out.println("img------>"+imgEles.attr("href"));

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

            if (element.getElementsByTag("a").size() != 0) {

                Elements imgEles = element.getElementsByTag("a");
                // 图片
                if (imgEles.size() > 0) {
                    for (Element imgEle : imgEles) {
                        if (imgEle.attr("href").equals(""))
                            continue;
                        news = new News();
                        String image = imgEle.attr("href");
                        if (image.endsWith(".jpg") || image.endsWith(".png") || image.endsWith(".gif")) {
                            news.setImageLink(image);
                            news.setType(Constant.IMG);
                            newses.add(news);
                        }
                    }
                }

                imgEles.remove();
            }

            if (element.text().equals(""))
                continue;

       /*     if (element.getElementsByTag("span").size() != 0) {
                String content = element.getElementsByTag("p").text();
                content=AppUtil.encoding(content,"gb2312");
                news = new News();
                news.setContent(content);
                news.setType(Constant.CONTENT);
                newses.add(news);

            }
       */

            if (element.getElementsByTag("pre").size() != 0) {
                Elements contentEle4 = element.getElementsByTag("pre");
                news = new News();
                String content = contentEle4.text();
                content=AppUtil.encoding(content,"utf-8");
                news.setContent(content);
                news.setType(Constant.CONTENT);
                newses.add(news);
            }
        }

        Element element =doc.getElementById("post_next_prev");
        String prevPageUrl=element.getElementsByTag("a").attr("href");
        String prevTitle=element.getElementsByTag("a").text();
        news =new News();
        news.setPrevPassage(prevTitle);
        news.setPrevUrl(prevPageUrl);
        news.setType(Constant.PREV);
        newsDto.setNewses(newses);
        return newsDto;
    }
}
