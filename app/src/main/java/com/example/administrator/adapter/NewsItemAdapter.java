package com.example.administrator.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.administrator.bean.NewsItem;
import com.example.administrator.csdn.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import java.util.List;

/**
 * Created by Administrator on 2015/11/13.
 */
public class NewsItemAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<NewsItem> mData;
    //选择的是哪个博客
    private int select = 0;
    //选择的是博客下的哪个选项
    private int selectType = 0;
    private ImageLoader imageLoader = ImageLoader.getInstance();
    private DisplayImageOptions options;

    public NewsItemAdapter(Context context, List<NewsItem> data, int select) {
        this.mData = data;
        this.select = select;
        mInflater = LayoutInflater.from(context);
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(context));
        switch (select) {
            case 1:
                options = new DisplayImageOptions.Builder().showImageOnFail(R.drawable.images).showImageForEmptyUri(R.drawable.images).showImageOnFail(R.drawable.images).cacheInMemory().
                        cacheOnDisc().displayer(new RoundedBitmapDisplayer(20)).displayer(new FadeInBitmapDisplayer(300)).build();
                break;
            case 2:
                options = new DisplayImageOptions.Builder().showImageOnFail(R.drawable.cto).showImageForEmptyUri(R.drawable.cto).showImageOnFail(R.drawable.cto).cacheInMemory().
                        cacheOnDisc().displayer(new RoundedBitmapDisplayer(10)).displayer(new FadeInBitmapDisplayer(150)).build();
                break;
            case 3:
                options = new DisplayImageOptions.Builder().showImageOnFail(R.drawable.blog_house).showImageForEmptyUri(R.drawable.blog_house).showImageOnFail(R.drawable.blog_house).cacheInMemory().
                        cacheOnDisc().displayer(new RoundedBitmapDisplayer(10)).displayer(new FadeInBitmapDisplayer(150)).build();
                break;
            case 4:
                options = new DisplayImageOptions.Builder().showImageOnFail(R.drawable.iteye).showImageForEmptyUri(R.drawable.iteye).showImageOnFail(R.drawable.iteye).cacheInMemory().
                        cacheOnDisc().displayer(new RoundedBitmapDisplayer(10)).displayer(new FadeInBitmapDisplayer(150)).build();
                break;
        }

    }

    public void setSelectType(int selectType) {
        this.selectType = selectType;
    }

    public void addAll(List<NewsItem> mData) {
        this.mData.addAll(mData);
    }

    public void setDatas(List<NewsItem> mData) {
        this.mData = mData;
        this.mData.addAll(mData);
    }

    @Override
    public int getCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            switch (select) {
                case 1:
                    convertView = mInflater.inflate(R.layout.news_item_yidong, null);
                    break;
                case 2:
                    convertView = mInflater.inflate(R.layout.news_item_51cto, null);
                case 3:
                    convertView = mInflater.inflate(R.layout.news_item_bloghouse, null);
                    break;
                case 4:
                    // 14 15 :    资讯 精华 16 17:    博客专栏
                    if (selectType == 14 || selectType == 15) {
                        convertView = mInflater.inflate(R.layout.news_item_iteye_2, null);
                    } else if (selectType == 16 || selectType == 17) {
                        convertView = mInflater.inflate(R.layout.news_item_iteye_1, null);
                    }
                    break;
            }
            viewHolder = new ViewHolder();

            viewHolder.mContent = (TextView) convertView.findViewById(R.id.id_content);
            viewHolder.mDate = (TextView) convertView.findViewById(R.id.id_date);
            viewHolder.mTitle = (TextView) convertView.findViewById(R.id.id_title);
            viewHolder.mImg = (ImageView) convertView.findViewById(R.id.id_newsImg);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        NewsItem newsItem = mData.get(position);
        viewHolder.mContent.setText(newsItem.getContent());
        viewHolder.mDate.setText(newsItem.getDate());
        viewHolder.mTitle.setText(newsItem.getTitle());

        if (newsItem.getPicLink() != null) {
            viewHolder.mImg.setVisibility(View.VISIBLE);
            imageLoader.displayImage(newsItem.getPicLink(), viewHolder.mImg, options);
        } else {
            viewHolder.mImg.setVisibility(View.GONE);
        }
        return convertView;
    }

    private final class ViewHolder {
        TextView mTitle;
        TextView mContent;
        TextView mDate;
        ImageView mImg;
    }
}
