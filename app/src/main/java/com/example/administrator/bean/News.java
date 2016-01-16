package com.example.administrator.bean;

/**
 * Created by Administrator on 2015/12/28.
 */
public class News {
    /*
    标题
     */
    private String title;
    /**
     * 摘要
     */
    private String sumary;
    /**
     * 内容
     */
    private String content;
    /**
     * 图片链接
     */
    private String imageLink;
    /**
     * 类型
     */
    private int type;
    /**
     * 上一篇
     */
    private String prevPassage;
    /**
     * 上一篇的url
     */
    private String prevUrl;
    /**
     * 下一篇
     */
    private String nextPassage;
    /**
     * 下一篇的url
     */
    private String nextUrl;
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSumary() {
        return sumary;
    }

    public void setSumary(String sumary) {
        this.sumary = sumary;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getPrevPassage() {
        return prevPassage;
    }
    public void setPrevPassage(String prevPassage) {
        this.prevPassage = prevPassage;
    }

    public String getNextPassage() {
        return nextPassage;
    }

    public void setNextPassage(String nextPassage) {
        this.nextPassage = nextPassage;
    }

    public String getPrevUrl() {
        return prevUrl;
    }

    public void setPrevUrl(String prevUrl) {
        this.prevUrl = prevUrl;
    }

    public String getNextUrl() {
        return nextUrl;
    }

    public void setNextUrl(String nextUrl) {
        this.nextUrl = nextUrl;
    }
}
