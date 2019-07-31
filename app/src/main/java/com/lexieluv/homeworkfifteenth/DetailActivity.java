package com.lexieluv.homeworkfifteenth;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

//收藏listview点击详情页
public class DetailActivity extends AppCompatActivity{
    ListView detail;
    List<Bean> list = new ArrayList<>();

    Intent intent = getIntent();
    String title = intent.getStringExtra("title");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_list);
        detail = findViewById(R.id.lv_detail);
        list = new BeanDao(DetailActivity.this).queryAll();
        detail.setAdapter(new DetailAdapter(this,list));
    }
}
