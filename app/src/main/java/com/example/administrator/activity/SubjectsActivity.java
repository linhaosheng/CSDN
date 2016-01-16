package com.example.administrator.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;


import com.example.administrator.adapter.SubjectAdapter;
import com.example.administrator.bean.NewsItem;
import com.example.administrator.contant.Constant;
import com.example.administrator.csdn.R;
import com.example.administrator.modle.NewItemITeye;
import com.example.administrator.util.AppUtil;
import com.example.administrator.util.CommonExecption;
import com.example.administrator.util.ITeyeDataUtil;

import java.util.ArrayList;
import java.util.List;

import me.maxwin.view.IXListViewLoadMore;
import me.maxwin.view.IXListViewRefreshListener;
import me.maxwin.view.XListView;

/**
 * Created by linhao on 2016/1/14.
 */
public class SubjectsActivity extends BaseActivityImpl implements IXListViewRefreshListener,IXListViewLoadMore {

    String url = null;
    private List<NewsItem> newsItems = new ArrayList<>();
    private NewItemITeye itemITeye = null;
    private XListView mXlistView;
    private SubjectAdapter subjectAdapter = null;
    private boolean isFirstIn=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subject);
        Bundle bundle = getIntent().getExtras();
        url = bundle.getString("url");
        itemITeye = new NewItemITeye();
        subjectAdapter = new SubjectAdapter(this);
        mXlistView=(XListView)findViewById(R.id.id_xlistView);
        mXlistView.setPullRefreshEnable(this);
        mXlistView.setPullLoadEnable(this);
        mXlistView.setRefreshTime(AppUtil.getRefreashTime(this, Constant.NEWS_TYPE_SUBJECTS));
        mXlistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NewsItem newsItem = newsItems.get(position-1);
                String url=newsItem.getLink();
                Intent intent=new Intent(SubjectsActivity.this,NewsContentActivity.class);
                intent.putExtra("url",url);
                intent.putExtra("select",5);
                startActivity(intent);
            }
        });
        if (isFirstIn) {
            /**
             * 进来时直接刷新
             */
            mXlistView.startRefresh();
            isFirstIn = false;
        } else {
            mXlistView.NotRefreshAtBegin();
        }
        mXlistView.setAdapter(subjectAdapter);
    }

    @Override
    public void onLoadMore() {
        new LoadData().execute();
    }

    @Override
    public void onRefresh() {
        new LoadData().execute();
    }

    class LoadData extends AsyncTask<String, Void, Integer> {

        @Override
        protected Integer doInBackground(String... params) {
            try {

                String html = ITeyeDataUtil.doGet(url);
                newsItems = itemITeye.getSubjectItem(html);
                subjectAdapter.setList(newsItems);
            } catch (CommonExecption e) {
                e.printStackTrace();
            }
            return -1;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            mXlistView.stopRefresh();
        }
    }
}
