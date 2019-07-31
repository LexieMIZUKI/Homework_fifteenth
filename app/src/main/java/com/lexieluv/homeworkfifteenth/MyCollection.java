package com.lexieluv.homeworkfifteenth;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

//收藏页
public class MyCollection extends AppCompatActivity{
    private ListView lv_collection;
    List<Bean> list = new ArrayList<>();
    List<String> sl = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.collection_listview);
        setTitle("我的收藏");
        init();//初始化控件
        setData();//数据库中绑定数据
        setOnClick();//单个listview点击跳转详情页
    }

    private void setOnClick() {
        lv_collection.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = null;
                intent = new Intent(MyCollection.this,DetailActivity.class);
                intent.putExtra("title",sl.get(position));
                startActivity(intent);
                finish();
            }
        });
    }

    private void setData() {
        list = new BeanDao(MyCollection.this).queryAll();
        for(Bean bb : list){
            sl.add(bb.getTitle());
        }
        lv_collection.setAdapter(new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item,sl));
    }

    private void init() {
        lv_collection = findViewById(R.id.lv_collection);
    }
}
