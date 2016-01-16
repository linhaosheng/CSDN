package com.example.administrator.modle;

import com.example.administrator.bean.News;
import com.example.administrator.bean.NewsDto;
import com.example.administrator.bean.NewsItem;
import com.example.administrator.contant.Constant;
import com.example.administrator.util.AppUtil;
import com.example.administrator.util.CTO_URLUtil;
import com.example.administrator.util.CommonExecption;
import com.example.administrator.util.DataUtil;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;






/**
 * Created by Administrator on 2015/12/24.
 * 处理NewItem的业务类
 */
public class NewItem51CTO {
    /**
     * 处理开发   网络管理  ，IT生活.....
     *
     * @param newsType
     * @param currentPage
     * @return
     * @throws CommonExecption
     */
    public List<NewsItem> getNewsItems(int newsType, int currentPage) throws CommonExecption {
        String urlStr = CTO_URLUtil.generateUrl(newsType, currentPage);

        String htmlStr = DataUtil.doGet(urlStr, "GB2312");
        List<NewsItem> newsItems = new ArrayList<>();
        NewsItem newsItem = null;

        Document doc = Jsoup.parse(htmlStr);
        Elements units = doc.getElementsByClass("r_li");

        for (int i = 0; i < units.size(); i++) {
            newsItem = new NewsItem();
            newsItem.setNewsType(newsType);

            Element unit_ele = units.get(i);

            Element h1_ele = unit_ele.getElementsByTag("h4").get(0);
            Element h1_a_ele = h1_ele.child(0);
            String title = h1_a_ele.text();
            title= AppUtil.encoding(title,"utf-8");
            String href = h1_a_ele.attr("href");
             href=AppUtil.encoding(href,"utf-8");
            newsItem.setLink(href);
            newsItem.setTitle(title);
            //	System.out.println(href);

            Element div_date = unit_ele.getElementsByTag("div").get(2);
            Element p_ele = div_date.getElementsByTag("p").get(0);
            String span_ele = p_ele.getElementsByTag("span").get(0).text();
            //	System.out.println("---------"+div_date.toString());
            span_ele=AppUtil.encoding(span_ele,"utf-8");
            newsItem.setDate(span_ele);

            try {// 可能没有图片
                Element div_pic = unit_ele.getElementsByTag("div").get(1);
                Element p_pic = div_pic.getElementsByTag("a").get(0);
                Element img = p_pic.child(0);
                String imgLink = img.attr("src");
                imgLink=AppUtil.encoding(imgLink,"utf-8");
                newsItem.setPicLink(imgLink);
            } catch (IndexOutOfBoundsException e) {
                System.out.println("数组边界异常");
            }
            Element div_content = unit_ele.getElementsByTag("div").get(1);
            Element p_content = div_content.getElementsByTag("p").get(0);
            String content = p_content.text();
            content=AppUtil.encoding(content,"utf-8");
            //	System.out.println("--------"+content);
            newsItem.setContent(content);
            newsItems.add(newsItem);
        }
        return newsItems;
    }

    /**
     * 开发技术
     *
     * @param urlStr
     * @return
     * @throws CommonExecption
     */
    public NewsDto getNewsDevelopment1(String urlStr) throws CommonExecption {
        int index = 0;
        NewsDto newsDto = new NewsDto();
        List<News> newses = new ArrayList<News>();
        String htmlStr = DataUtil.doGet(urlStr,"GB2312");
        Document doc = Jsoup.parse(htmlStr);

        // ±êÌâ
        Element detailEle = doc.select(".showTitleBOx .showTitle").get(0);
        News news = new News();

        news.setTitle(detailEle.text());
        news.setType(Constant.TITLE);
        newses.add(news);

        Element contentEle2 = doc.select(".showContent").get(0);
        Elements childrenEle = contentEle2.children();

        for (Element element : childrenEle) {

            if (element.getElementsByTag("h3").size() != 0) {

                news = new News();
                news.setContent(element.getElementsByTag("h3").text());
                news.setType(Constant.CONTENT);
                newses.add(news);
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
                // Í¼Æ¬
                if (imgEles.size() > 0) {
                    // System.out.println("img------>"+imgEles.attr("href"));

                    for (Element imgEle : imgEles) {
                        if (imgEle.attr("href").equals(""))
                            continue;
                        news = new News();
                        news.setImageLink(imgEle.attr("href"));
                        news.setType(Constant.IMG);
                        newses.add(news);
                    }
                }

                imgEles.remove();
            }

            if (element.text().equals(""))
                continue;

   /*         if (element.getElementsByTag("span").size() != 0) {
                Elements contentEle3 = element.getElementsByTag("span");
                news = new News();
                String content = contentEle3.text();
                byte[] bytes = { (byte) 0xC2, (byte) 0xA0 };
                try {
                    String UTFSpace = new String(bytes, "utf-8");
                    content = content.replace(UTFSpace, " ");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                news.setContent(content);
                news.setType(Constant.CONTENT);
                newses.add(news);

            }
            */

            if (element.getElementsByTag("p").size() != 0) {
                Elements contentEle3 = element.getElementsByTag("p");
                news = new News();
                String content = contentEle3.text();
                content=AppUtil.encoding(content,"gb2312");
                news.setContent(content);
                news.setType(Constant.CONTENT);
                newses.add(news);
            }

            if (element.getElementsByTag("pre").size() != 0) {
                Elements contentEle4 = element.getElementsByTag("pre");
                news = new News();
                String content1 = contentEle4.text();
                byte[] bytes1 = { (byte) 0xC2, (byte) 0xA0 };
                try {
                    String UTFSpace = new String(bytes1, "utf-8");
                    content1 = content1.replace(UTFSpace, " ");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                news.setContent(content1);
                news.setType(Constant.CONTENT);
                newses.add(news);
            }
        }

        // System.out.println("content------"+content4);

        String nextPageUri = "http://wuyvzhang.blog.51cto.com";
        Element nextPage = doc.select(".prevNext").get(0);
        String uri = nextPage.getElementsByTag("a").attr("href");
        newsDto.setNextPage(nextPageUri + uri);
        // ÄÚÈÝ

        newsDto.setNewses(newses);
        return newsDto;
    }
}
