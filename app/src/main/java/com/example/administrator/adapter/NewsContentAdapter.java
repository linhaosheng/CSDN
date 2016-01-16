package com.example.administrator.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.bean.News;
import com.example.administrator.contant.Constant;
import com.example.administrator.csdn.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/12/28.
 */
public class NewsContentAdapter extends BaseAdapter {


    private LayoutInflater inflater;
    private List<News> data = new ArrayList<>();
    private int select;    //进入的是那个博客
    private ImageLoader imageLoader = ImageLoader.getInstance();
    private DisplayImageOptions options;

    public NewsContentAdapter(Context context,int select) {
        this.select=select;
        inflater = LayoutInflater.from(context);
        imageLoader.init(ImageLoaderConfiguration.createDefault(context));
        selectPicture(select);
        }

        public void selectPicture(int select){
            switch (select){
                case 1:
                    options = new DisplayImageOptions.Builder().showStubImage(R.drawable.images)
                            .showImageForEmptyUri(R.drawable.images).showImageOnFail(R.drawable.images).cacheInMemory()
                            .cacheOnDisc().imageScaleType(ImageScaleType.EXACTLY).bitmapConfig(Bitmap.Config.RGB_565)
                            .displayer(new FadeInBitmapDisplayer(300)).build();
                    break;
                case 2:
                    options = new DisplayImageOptions.Builder().showStubImage(R.drawable.cto)
                            .showImageForEmptyUri(R.drawable.cto).showImageOnFail(R.drawable.cto).cacheInMemory()
                            .cacheOnDisc().imageScaleType(ImageScaleType.EXACTLY).bitmapConfig(Bitmap.Config.RGB_565)
                            .displayer(new FadeInBitmapDisplayer(300)).build();
                    break;
                case 3:
                    options = new DisplayImageOptions.Builder().showStubImage(R.drawable.blog_house)
                            .showImageForEmptyUri(R.drawable.blog_house).showImageOnFail(R.drawable.blog_house).cacheInMemory()
                            .cacheOnDisc().imageScaleType(ImageScaleType.EXACTLY).bitmapConfig(Bitmap.Config.RGB_565)
                            .displayer(new FadeInBitmapDisplayer(300)).build();
                    break;
                case 4:
                    options = new DisplayImageOptions.Builder().showStubImage(R.drawable.iteye)
                            .showImageForEmptyUri(R.drawable.iteye).showImageOnFail(R.drawable.iteye).cacheInMemory()
                            .cacheOnDisc().imageScaleType(ImageScaleType.EXACTLY).bitmapConfig(Bitmap.Config.RGB_565)
                            .displayer(new FadeInBitmapDisplayer(300)).build();
                    break;
    }
    }

    public void addList(List<News> datas) {
        data.addAll(datas);
    }

    @Override
    public int getCount() {
        return data == null ? 0 : data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        switch (data.get(position).getType()) {
            case Constant.TITLE:
                return 0;
            case Constant.SUMMARY:
                return 1;
            case Constant.CONTENT:
                return 2;
            case Constant.IMG:
                return 3;
            case Constant.BOLD_TITLE:
                return 4;
            case Constant.PREV:
                return 5;
            case Constant.NEXT:
                return 6;
        }
        return -1;
    }

    @Override
    public int getViewTypeCount() {
        return 7;
    }

    @Override
    public boolean isEnabled(int position) {
        switch (data.get(position).getType()) {
            case Constant.IMG:
                return true;
            case Constant.PREV:
                return true;
            case Constant.NEXT:
                return true;
            default:
                return false;
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        News news=data.get(position);   //获取当前数据项

        ViewHolder holder=null;
        if(convertView==null){
            holder=new ViewHolder();

            switch (news.getType()){
               case Constant.TITLE:
                   convertView=inflater.inflate(R.layout.news_content_title_item,null);
                   holder.textView=(TextView)convertView.findViewById(R.id.text);
                break;
                case Constant.SUMMARY:
                    convertView=inflater.inflate(R.layout.news_content_summary_item,null);
                    holder.textView=(TextView)convertView.findViewById(R.id.text);
                    break;
                case Constant.CONTENT:
                    convertView=inflater.inflate(R.layout.news_content_item,null);
                    holder.textView=(TextView)convertView.findViewById(R.id.text);
                    break;
                case Constant.IMG:
                    convertView=inflater.inflate(R.layout.news_content_img_item,null);
                    holder.imageView=(ImageView)convertView.findViewById(R.id.imageView);
                    break;
                case Constant.BOLD_TITLE:
                    convertView=inflater.inflate(R.layout.news_content_bold_title_item,null);
                    holder.textView=(TextView)convertView.findViewById(R.id.text);
                    break;
                case Constant.PREV:
                    convertView=inflater.inflate(R.layout.news_content_prev_item,null);
                    holder.textView=(TextView)convertView.findViewById(R.id.prev);
                    break;
                case Constant.NEXT:
                    convertView=inflater.inflate(R.layout.news_content_next_item,null);
                    holder.textView=(TextView)convertView.findViewById(R.id.next);
                    break;
            }
            convertView.setTag(holder);
        }else {
            holder=(ViewHolder)convertView.getTag();
        }
        if (null != news)
        {
            switch (news.getType())
            {
                case Constant.IMG:
                    imageLoader.displayImage(news.getImageLink(), holder.imageView, options);
                    break;
                case Constant.TITLE:
                    holder.textView.setText(news.getTitle());
                    break;
                case Constant.SUMMARY:
                    holder.textView.setText(news.getSumary());
                    break;
                case Constant.CONTENT:
                    if (select==1){
                        holder.textView.setText("\u3000\u3000"+ Html.fromHtml(news.getContent()));
                    }else {
                        holder.textView.setText("\u3000\u3000"+ news.getContent());
                    }
                    break;
                case Constant.BOLD_TITLE:
                    holder.textView.setText("\u3000\u3000"+Html.fromHtml(news.getContent()));
                case Constant.PREV:
                    String prevTitle=news.getPrevPassage();
                    if(prevTitle==null) {
                        holder.textView.setVisibility(View.GONE);
                    }else {
                        holder.textView.setText("上一篇"+prevTitle);
                    }
                case Constant.NEXT:
                    String nextTitle=news.getPrevPassage();
                    if(nextTitle==null) {
                        holder.textView.setVisibility(View.GONE);
                    }else {
                        holder.textView.setText("上一篇"+nextTitle);
                    }
            }
        }
        return convertView;
    }
    private final class ViewHolder{
        TextView textView;
        ImageView imageView;
    }
}
