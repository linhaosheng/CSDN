package com.example.administrator.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.administrator.bean.NewsItem;
import com.example.administrator.contant.Constant;


import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2015/11/13.
 */
public class NewsItemDao {

    private DBHelp_CSDN csdnHelp;
    private DBHelp_CTO ctoHelp;
    private DBHelp_Blog_house blog_houseHelp;
    private DBHelp_ITeye iteyeHelp;

    public NewsItemDao(Context context) {
        csdnHelp = new DBHelp_CSDN(context);
        ctoHelp = new DBHelp_CTO(context);
        blog_houseHelp = new DBHelp_Blog_house(context);
        iteyeHelp = new DBHelp_ITeye(context);
    }

    /**
     * 添加消息到数据库
     *
     * @param newsItem
     */
    public void add(NewsItem newsItem, int select) {
        Log.i("add new newsType", String.valueOf(newsItem.getNewsType()));
        String sql = null;
        SQLiteDatabase db =null;
        switch (select) {
            case 1:
                db = csdnHelp.getWritableDatabase();
                sql = "insert into csdn_tb_newsItem(title,link,date,imgLink,content,newsType)values(?,?,?,?,?,?);";
                break;
            case 2:
                db = ctoHelp.getWritableDatabase();
                sql = "insert into cto_tb_newsItem(title,link,date,imgLink,content,newsType)values(?,?,?,?,?,?);";
                break;
            case 3:
                db = blog_houseHelp.getWritableDatabase();
                sql = "insert into blog_house_tb_newsItem(title,link,date,imgLink,content,newsType)values(?,?,?,?,?,?);";
                break;
            case 4:
                db = iteyeHelp.getWritableDatabase();
                sql = "insert into iteye_tb_newsItem(title,link,date,imgLink,content,newsType)values(?,?,?,?,?,?);";
                break;
        }
        db.execSQL(sql, new Object[]{newsItem.getTitle(), newsItem.getLink(), newsItem.getDate(), newsItem.getPicLink(), newsItem.getContent(), newsItem.getNewsType()});
        db.close();
    }

    /**
     * 根据消息类型删除消息
     *
     * @param newsType
     */
    public void deleteAll(int newsType, int select) {
        SQLiteDatabase db = null;
        String sql = null;
        switch (select) {
            case 1:
              db = csdnHelp.getWritableDatabase();
                sql = "delete from csdn_tb_newsItem where newsType=?";
                break;
            case 2:
                db = ctoHelp.getWritableDatabase();
                sql = "delete from cto_tb_newsItem where newsType=?";
                break;
            case 3:
                db = blog_houseHelp.getWritableDatabase();
                sql = "delete from blog_house_tb_newsItem where newsType=?";
                break;
            case 4:
                db = iteyeHelp.getWritableDatabase();
                sql = "delete from iteye_tb_newsItem where newsType=?";
                break;

        }
        db.execSQL(sql, new Object[]{newsType});
        db.close();
    }

    public void add(List<NewsItem> newsItems, int select) {
        for (NewsItem newsItem : newsItems) {
            add(newsItem, select);
        }
    }

    /**
     * 根据newsType和currentPage从数据库中取数据
     *
     * @param newsType
     * @param currentPage
     * @return
     */
    public List<NewsItem> list(int newsType, int currentPage, int select) {
        List<NewsItem> newsItems = new ArrayList<NewsItem>();
        SQLiteDatabase db =null;
        int offset = 0;
        String sql = null;
        int num = 0;
        try {
            switch (select) {
                case 1:
                    db = csdnHelp.getReadableDatabase();
                    offset = 10 * (currentPage - 1);
                    num = 10;
                    sql = "select title,link,date,imgLink,content,newsType from csdn_tb_newsItem where newsType=? limit ?,?";
                    break;
                case 2:
                    db = csdnHelp.getReadableDatabase();
                    offset = 15 * (currentPage - 1);
                    num = 15;
                    sql = "select title,link,date,imgLink,content,newsType from cto_tb_newsItem where newsType=? limit ?,?";
                    break;
                case 3:
                    db = csdnHelp.getReadableDatabase();
                    if (newsType == Constant.NEWS_TYPE_NEWS) {
                        offset = 25 * (currentPage - 1);
                        num = 25;
                    } else {
                        offset = 20 * (currentPage - 1);
                        num = 20;
                    }
                    sql = "select title,link,date,imgLink,content,newsType from blog_house_tb_newsItem where newsType=? limit ?,?";
                    break;
                case 4:
                    db = csdnHelp.getReadableDatabase();
                    switch (newsType) {
                        case Constant.NEWS_TYPE_NEW:
                            offset = 25 * (currentPage - 1);
                            num = 25;
                            break;
                        case Constant.NEWS_TYPE_MAGAZINES:
                            offset = 30 * (currentPage - 1);
                            num = 30;
                            break;
                        case Constant.NEWS_TYPE_BLOGS:
                            offset = 30 * (currentPage - 1);
                            num = 25;
                            break;
                        case Constant.NEWS_TYPE_SUBJECTS:
                            offset = 20 * (currentPage - 1);
                            num = 20;
                            break;
                    }
                    sql = "select title,link,date,imgLink,content,newsType from iteye_tb_newsItem where newsType=? limit ?,?";
                    break;
            }
            Cursor cursor = db.rawQuery(sql, new String[]{newsType + "", offset + "", "" + (offset + num)});
            NewsItem newsItem = null;

            while (cursor.moveToNext()) {
                newsItem = new NewsItem();
                String title = cursor.getString(cursor.getColumnIndex("title"));
                String link = cursor.getString(cursor.getColumnIndex("link"));
                String date = cursor.getString(cursor.getColumnIndex("date"));
                String imgLink = cursor.getString(cursor.getColumnIndex("imgLink"));
                String content = cursor.getString(cursor.getColumnIndex("content"));
                Integer newstype = cursor.getInt(cursor.getColumnIndex("newsType"));

                newsItem.setTitle(title);
                newsItem.setPicLink(imgLink);
                newsItem.setContent(content);
                newsItem.setLink(link);
                newsItem.setNewsType(newstype);
                newsItem.setDate(date);

                newsItems.add(newsItem);
            }
            cursor.close();
            db.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return newsItems;
    }
}
