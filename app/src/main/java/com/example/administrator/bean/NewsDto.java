package com.example.administrator.bean;

import java.util.List;

/**
 * Created by Administrator on 2015/12/28.
 */
public class NewsDto {
    private List<News>newses;
    private String nextPage;

    public List<News> getNewses(){
        return newses;
    }

    public String getNextPage() {
        return nextPage;
    }

    public void setNextPage(String nextPage) {
        this.nextPage = nextPage;
    }

    public void setNewses(List<News> newses) {
        this.newses = newses;
    }
}
