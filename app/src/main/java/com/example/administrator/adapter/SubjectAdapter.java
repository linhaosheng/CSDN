package com.example.administrator.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.bean.NewsItem;
import com.example.administrator.csdn.R;


import java.util.List;

/**
 * Created by linhao on 2016/1/14.
 */
public class SubjectAdapter extends BaseAdapter {

    public List<NewsItem> newsItems = null;
    private LayoutInflater mInflater;

    public SubjectAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    public void setList(List<NewsItem> newsItems) {
        this.newsItems = newsItems;
    }

    @Override
    public int getCount() {
        return newsItems == null ? 0 : newsItems.size();
    }

    @Override
    public Object getItem(int position) {
        return newsItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.subject_item, null);
            viewHolder=new ViewHolder();
            viewHolder.mTitle=(TextView)convertView.findViewById(R.id.id_title);
            viewHolder.mContent=(TextView) convertView.findViewById(R.id.id_content);
            viewHolder.mDate=(TextView)convertView.findViewById(R.id.id_date);
            convertView.setTag(viewHolder);
        }else {
            viewHolder=(ViewHolder) convertView.getTag();
        }
        NewsItem newsItem=newsItems.get(position);
        viewHolder.mContent.setText(newsItem.getContent());
        viewHolder.mDate.setText(newsItem.getDate());
        viewHolder.mTitle.setText(newsItem.getTitle());
        return convertView;
    }

    private final class ViewHolder {
        TextView mTitle;
        TextView mContent;
        TextView mDate;
    }
}
