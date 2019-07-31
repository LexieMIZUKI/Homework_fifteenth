package com.lexieluv.homeworkfifteenth;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class DetailAdapter extends BaseAdapter{
    List<Bean> list;
    Context context;
    LayoutInflater inflater;

    public DetailAdapter(Context context,List<Bean> list){
        super();
        this.context = context;
        this.list = list;
        this.inflater = LayoutInflater.from(context);
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
        ViewHolder vh = null;
        if(convertView == null){
            vh = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_detail,null);
            vh.title = convertView.findViewById(R.id.tv_main_title);
            vh.author = convertView.findViewById(R.id.tv_main_author);
            vh.content = convertView.findViewById(R.id.tv_main_content);
            convertView.setTag(vh);
        }else {
            vh = (ViewHolder) convertView.getTag();
        }
        vh.title.setText(list.get(position).getTitle());
        vh.author.setText(list.get(position).getAuthor());
        vh.content.setText(list.get(position).getContent());
        return convertView;
    }

    class ViewHolder{
        public TextView title,author,content;
    }
}
