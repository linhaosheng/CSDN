package com.example.administrator.activity;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;


import com.example.administrator.adapter.NewsItemAdapter;
import com.example.administrator.bean.NewsItem;
import com.example.administrator.contant.Constant;
import com.example.administrator.csdn.R;
import com.example.administrator.dao.NewsItemDao;
import com.example.administrator.modle.NewItem51CTO;
import com.example.administrator.modle.NewItemBlogHouse;
import com.example.administrator.modle.NewItemITeye;
import com.example.administrator.modle.NewsItemBiz;
import com.example.administrator.util.AppUtil;
import com.example.administrator.util.NetUtil;

import java.util.ArrayList;
import java.util.List;

import me.maxwin.view.IXListViewLoadMore;
import me.maxwin.view.IXListViewRefreshListener;


/**
 * Created by Administrator on 2015/11/12.
 */
public class MainFragment extends Fragment implements IXListViewRefreshListener, IXListViewLoadMore {

    private static final int LOAD_MORE = 0x110;
    private static final int LOAD_REFREASH = 0x111;

    private static final int TIP_ERROR_NO_NETWORK = 0x112;
    private static final int TIP_ERROR_SERVER = 0x113;

    //是否是第一次进入
    private boolean isFirstIn = true;
    //是否连接网络
    private boolean isConnNet = false;

    //选择是选择哪个博客  1：CSDN  2：51CTO   3：博客园    4：ITEYE博客
    private int select;
    //选择的是博客的哪个
    private int selectType;
    /**
     * 当前数据是否从网络获取
     */
    private boolean isLoadingDataFromNetwork=true;
    //取CSDN数据
    private int newTypes = Constant.NEW_TYPE_YEJIE;
    //取51CTO数据
    private int newTypes_cto = Constant.NEWS_TYPE_NETWORK;
    //取博客园数据
    private int newTypes_blog_house = Constant.NEWS_TYPE_HOME;
    //取ITeye数据
    private int newTypes_iteye = Constant.NEWS_TYPE_NEW;
    private int currentPage = 1;
    //CSDN
    private NewsItemBiz newsItemBiz;
    //51CTO
    private NewItem51CTO newItem51CTO;
    //博客园
    private NewItemBlogHouse blogHouse;
    //ITeye
    private NewItemITeye iTeye;
    private me.maxwin.view.XListView mXlistView;
    private NewsItemAdapter mAdapter;
    private List<NewsItem> mDatas = new ArrayList<NewsItem>();
    /**
     * 与数据库交互
     */
    private NewsItemDao newsItemDao;

    public MainFragment() {
    }

    public MainFragment(int newsType, int select) {
        this.newTypes = newsType;
        this.newTypes_cto = newsType + 5;
        this.newTypes_blog_house = newsType + 9;
        this.newTypes_iteye = newsType + 13;
        this.select = select;
        newsItemBiz = new NewsItemBiz();
        newItem51CTO = new NewItem51CTO();
        blogHouse = new NewItemBlogHouse();
        iTeye = new NewItemITeye();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_item_fragment_main, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        newsItemDao = new NewsItemDao(getContext());
        mAdapter = new NewsItemAdapter(getActivity(), mDatas, select);
        mAdapter.setSelectType(newTypes_iteye);
        mXlistView = (me.maxwin.view.XListView) getView().findViewById(R.id.id_xlistView);
        mXlistView.setAdapter(mAdapter);
        mXlistView.setPullRefreshEnable(this);
        mXlistView.setPullLoadEnable(this);
        switch (select) {
            case 1:
                mXlistView.setRefreshTime(AppUtil.getRefreashTime(getActivity(), newTypes));
                break;
            case 2:
                mXlistView.setRefreshTime(AppUtil.getRefreashTime(getActivity(), newTypes_cto));
                break;
            case 3:
                mXlistView.setRefreshTime(AppUtil.getRefreashTime(getActivity(), newTypes_blog_house));
                break;
            case 4:
                mXlistView.setRefreshTime(AppUtil.getRefreashTime(getActivity(), newTypes_iteye));
                break;
        }
        // mXlistView.startRefresh();
        mXlistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NewsItem newsItem = mDatas.get(position-1);
                NewsItem nextItem=mDatas.get(position);
                Intent intent=null;
                if (newTypes_iteye==17){
                    intent = new Intent(getActivity(), SubjectsActivity.class);
                    intent.putExtra("url", newsItem.getLink());
                }else {
                    intent = new Intent(getActivity(), NewsContentActivity.class);
                    intent.putExtra("url", newsItem.getLink());
                    intent.putExtra("nextUrl", nextItem.getLink());
                    intent.putExtra("select", select);
                    intent.putExtra("cuttenrPage", currentPage);
                }
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
    }

    @Override
    public void onLoadMore() {
        new LoadDatasTask().execute(LOAD_MORE);
    }

    @Override
    public void onRefresh() {
        new LoadDatasTask().execute(LOAD_REFREASH);
    }

    /**
     * 记载数据的异步任
     *
     * @author
     */
    class LoadDatasTask extends AsyncTask<Integer, Void, Integer> {
        @Override
        protected Integer doInBackground(Integer... params) {
            switch (params[0]) {
                case LOAD_MORE:
                    loadMoreData();
                    break;
                case LOAD_REFREASH:
                    refreashData();
                    break;
            }
            return -1;
        }

        @Override
        protected void onPostExecute(Integer result) {
            switch (result) {
                case TIP_ERROR_NO_NETWORK:
                    Toast.makeText(getActivity(), "没网络连接", Toast.LENGTH_SHORT).show();
                    mAdapter.notifyDataSetChanged();
                    mXlistView.stopRefresh();
                    break;
                case TIP_ERROR_SERVER:
                    Toast.makeText(getActivity(), "服务器错误", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
            switch (select) {
                case 1:
                    mXlistView.setRefreshTime(AppUtil.getRefreashTime(getActivity(), newTypes));
                    break;
                case 2:
                    mXlistView.setRefreshTime(AppUtil.getRefreashTime(getActivity(), newTypes_cto));
                    break;
                case 3:
                    mXlistView.setRefreshTime(AppUtil.getRefreashTime(getActivity(), newTypes_blog_house));
                    break;
                case 4:
                    mXlistView.setRefreshTime(AppUtil.getRefreashTime(getActivity(), newTypes_iteye));
                    break;
            }
            mXlistView.stopRefresh();
            mXlistView.stopLoadMore();
        }
    }

    /**
     * 刷新获取数据
     *
     * @return
     */
    public Integer refreashData() {
        if (NetUtil.checkNet(getActivity())) {
            isConnNet = true;
            List<NewsItem> newsItems = null;
            try {
                switch (select) {
                    case 1:
                        newsItems = newsItemBiz.getNewsItems(newTypes, currentPage);
                        List<NewsItem> newsItem1 = new ArrayList<>();
                        for (int i = 0; i <= newsItems.size() / 2; i++) {
                            newsItem1.add(newsItems.get(i));
                        }

                        mAdapter.setDatas(newsItem1);
                        mDatas=newsItem1;
                        isLoadingDataFromNetwork = true;
                        // 设置刷新时间
                        AppUtil.setRefreashTime(getActivity(), newTypes);
                        // 清除数据库数据
                        newsItemDao.deleteAll(newTypes,select);
                        // 存入数据库
                        newsItemDao.add(newsItems,select);
                        break;
                    case 2:
                        newsItems = newItem51CTO.getNewsItems(newTypes_cto, currentPage);
                        List<NewsItem> newsItem2 = new ArrayList<>();
                        for (int i = 0; i <= newsItems.size() / 2; i++) {
                            newsItem2.add(newsItems.get(i));
                        }
                        mAdapter.setDatas(newsItem2);
                        mDatas=newsItem2;
                        isLoadingDataFromNetwork = true;
                        // 设置刷新时间
                        AppUtil.setRefreashTime(getActivity(), newTypes_cto);
                        // 清除数据库数据
                        newsItemDao.deleteAll(newTypes_cto,select);
                        // 存入数据库
                        newsItemDao.add(newsItem2,select);
                        break;
                    case 3:
                        newsItems = blogHouse.getNewsItems(newTypes_blog_house, currentPage);
                        List<NewsItem> newsItem3 = new ArrayList<>();
                        for (int i = 0; i <= newsItems.size() / 2; i++) {
                            newsItem3.add(newsItems.get(i));
                        }
                        mAdapter.setDatas(newsItem3);
                        mDatas=newsItem3;
                        isLoadingDataFromNetwork = true;
                        // 设置刷新时间
                        AppUtil.setRefreashTime(getActivity(), newTypes_blog_house);
                        // 清除数据库数据
                        newsItemDao.deleteAll(newTypes_blog_house,select);
                        // 存入数据库
                        newsItemDao.add(newsItem3,select);
                        break;
                    case 4:
                        newsItems = iTeye.getNewsItems(newTypes_iteye, currentPage, newTypes_iteye);
                        List<NewsItem> newsItem4 = new ArrayList<>();
                        for (int i = 0; i <= newsItems.size() / 2; i++) {
                            newsItem4.add(newsItems.get(i));
                        }
                        mAdapter.setDatas(newsItem4);
                        mDatas=newsItem4;
                        isLoadingDataFromNetwork = true;
                        // 设置刷新时间
                        AppUtil.setRefreashTime(getActivity(), newTypes_iteye);
                        // 清除数据库数据
                        newsItemDao.deleteAll(newTypes_iteye,select);
                        // 存入数据库
                        newsItemDao.add(newsItem4,select);
                        break;
                }
              //  mDatas = newsItems;
            } catch (Exception e) {
                e.printStackTrace();
                isLoadingDataFromNetwork = false;
                return TIP_ERROR_SERVER;
            }
        } else {
            isConnNet = false;
            isLoadingDataFromNetwork = false;
            List<NewsItem> newsItems = null;
            switch (select) {
                case 1:
                    //从数据库取数据
                    newsItems = newsItemDao.list(newTypes, currentPage,select);
                    break;
                case 2:
                    newsItems = newsItemDao.list(newTypes_cto, currentPage,select);
                    break;
                case 3:
                    newsItems = newsItemDao.list(newTypes_blog_house, currentPage,select);
                    break;
                case 4:
                    newsItems = newsItemDao.list(newTypes_iteye, currentPage,select);
                    break;
            }
            mDatas = newsItems;
            return TIP_ERROR_NO_NETWORK;
        }
        return -1;
    }

    /**
     * 根据网络决定是从哪里取数据
     */
    public void loadMoreData() {
        System.out.println("isLoadingDataFromNetwork------" + isLoadingDataFromNetwork);
        if (isLoadingDataFromNetwork) {
            currentPage =currentPage+ 1;
            List<NewsItem> newsItems = null;
            try {
                switch (select) {
                    case 1:
                        newsItems = newsItemBiz.getNewsItems(newTypes, currentPage);
                        break;
                    case 2:
                        newsItems = newItem51CTO.getNewsItems(newTypes_cto, currentPage);
                        break;
                    case 3:
                        newsItems = blogHouse.getNewsItems(newTypes_blog_house, currentPage);
                        break;
                    case 4:
                        newsItems = iTeye.getNewsItems(newTypes_iteye, currentPage,newTypes_iteye);
                        break;
                }
                newsItemDao.add(newsItems,select);
                mAdapter.addAll(newsItems);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            // 从数据库加载的
            currentPage += 1;
            List<NewsItem> newsItems = null;
            switch (select) {
                case 1:
                    newsItems = newsItemDao.list(newTypes, currentPage,select);
                    break;
                case 2:
                    newsItems = newsItemDao.list(newTypes_cto, currentPage,select);
                    break;
                case 3:
                    newsItems = newsItemDao.list(newTypes_blog_house, currentPage,select);
                    break;
                case 4:
                    newsItems = newsItemDao.list(newTypes_iteye, currentPage,select);
                    break;
            }
            if(newsItems!=null) {
                mAdapter.addAll(newsItems);
            }
        }
    }
}
