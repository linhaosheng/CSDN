package com.example.administrator.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.administrator.adapter.NewsContentAdapter;
import com.example.administrator.bean.News;
import com.example.administrator.bean.NewsItem;
import com.example.administrator.csdn.R;
import com.example.administrator.modle.NewItem51CTO;
import com.example.administrator.modle.NewItemBlogHouse;
import com.example.administrator.modle.NewItemITeye;
import com.example.administrator.modle.NewsItemBiz;
import com.example.administrator.util.CommonExecption;
import com.example.administrator.util.ITeyeDataUtil;

import java.util.List;

import me.maxwin.view.IXListViewLoadMore;
import me.maxwin.view.XListView;

/**
 * Created by Administrator on 2015/11/13.
 */
public class NewsContentActivity extends BaseActivityImpl implements IXListViewLoadMore {

    private XListView listView;
    /**
     * 该页面的URL
     */
    private String url;
    //下一篇文章的url
    private String nextUrl;
    //选的是哪个博客;
    private int select;
    //当前的是那一页
    private int currentPage;
    private NewsItemBiz newsItemBiz;
    private NewItem51CTO newItem51CTO;
    private NewItemBlogHouse newItemBlogHouse;
    private NewItemITeye newItemITeye;

    private List<News> data;
    private ProgressBar progressBar;
    private NewsContentAdapter adapter;
    private ImageView nextPage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_content);
        Bundle bundle = getIntent().getExtras();
        url = bundle.getString("url");
        nextUrl=bundle.getString("nextUrl");
        select=bundle.getInt("select");
        currentPage=bundle.getInt("currentPage");
        initBiz();
        adapter = new NewsContentAdapter(this,select);
        listView = (XListView) findViewById(R.id.id_listview);
        progressBar = (ProgressBar) findViewById(R.id.id_newsContentPro);
        nextPage=(ImageView)findViewById(R.id.next);
        nextPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(NewsContentActivity.this,NewsContentActivity.class);
                intent.putExtra("url",nextUrl);
                startActivity(intent);
            }
        });
        listView.setAdapter(adapter);
        listView.disablePullRefreash();
        listView.setPullLoadEnable(this);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                News news = data.get(position - 1);
               switch (view.getId())
               {
                   case R.id.imageView:
                       String imageLink = news.getImageLink();
                       Intent intent = new Intent(NewsContentActivity.this, ImageShowActivity.class);
                       intent.putExtra("url", imageLink);
                       startActivity(intent);
                       break;
                   case R.id.prev:
                       Intent prevIntent=new Intent(NewsContentActivity.this,NewsContentActivity.class);
                       prevIntent.putExtra("url",news.getPrevUrl());
                       startActivity(prevIntent);
                       break;
                   case R.id.next:
                       Intent nextIntent=new Intent(NewsContentActivity.this,NewsContentActivity.class);
                       nextIntent.putExtra("url",news.getNextUrl());
                       startActivity(nextIntent);
                       break;
               }
            }
        });
        new LoadDataTask().execute();
    }

    /**
     * 初始化Biz
     */
    public void initBiz(){
        newsItemBiz = new NewsItemBiz();
        newItem51CTO=new NewItem51CTO();
        newItemBlogHouse=new NewItemBlogHouse();
        newItemITeye=new NewItemITeye();
    }
    @Override
    public void onLoadMore() {

    }

    class LoadDataTask extends AsyncTask<Void,Void,Void>{

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
        }


        @Override
        protected Void doInBackground(Void... params) {
            try {
                switch (select){
                    case 1:
                        data = newsItemBiz.getNews(url).getNewses();
                        break;
                    case 2:
                        data = newItem51CTO.getNewsDevelopment1(url).getNewses();
                        break;
                    case 3:
                        data = newItemBlogHouse.getNewsDevelopment1(url,currentPage,select).getNewses();
                        break;
                    case 4:
                        data = newItemITeye.getNewsDevelopment1(url).getNewses();
                        break;
                    case 5:
                        String html= ITeyeDataUtil.doGet(url);
                        data = newItemITeye.getSubjectDTO(html).getNewses();
                        for (News news :data){
                            System.out.println("content------>"+news.getContent());
                        }
                        break;

                }

            }catch (CommonExecption e){
                Looper.prepare();
                Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if(data==null)
                return;
            adapter.addList(data);
            adapter.notifyDataSetChanged();
            progressBar.setVisibility(View.GONE);
        }
    }

    /**
     * 点击返回按钮
     * @param view
     */
    public void back(View view){
        finish();
    }
}
