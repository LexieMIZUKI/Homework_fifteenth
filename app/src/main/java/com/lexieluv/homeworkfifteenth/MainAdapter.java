package com.lexieluv.homeworkfifteenth;


import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.List;

public class MainAdapter extends BaseAdapter {
    Context context;
    private List<Bean> list;
    private LayoutInflater layoutInflater;
    Uri picUri = Uri.parse("http://img.mukewang.com//546418750001a81906000338-590-330.jpg");

    public MainAdapter(Context context,List<Bean> list){
        super();
        this.context = context;
        this.list = list;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.item_main,null);
            viewHolder.title = convertView.findViewById(R.id.tv_main_title);
            viewHolder.author = convertView.findViewById(R.id.tv_main_author);
            viewHolder.content = convertView.findViewById(R.id.tv_main_content);
            viewHolder.pic = convertView.findViewById(R.id.iv_main);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.title.setText(list.get(position).getTitle());
        viewHolder.author.setText(list.get(position).getAuthor());
        viewHolder.content.setText(list.get(position).getContent());
        //利用毕加索加载网络图片
                Picasso.with(context)
                .load(picUri)
//                .resize(20,20)//可以用来调节大小，一开始以为这里没设置导致闪退，结果是context忘了初始化
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.mipmap.ic_launcher_round)
                .into(viewHolder.pic);
        return convertView;
    }

    class ViewHolder{
        public TextView title,author,content;
        public ImageView pic;
    }

}
