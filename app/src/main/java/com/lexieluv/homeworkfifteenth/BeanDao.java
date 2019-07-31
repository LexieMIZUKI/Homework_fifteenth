package com.lexieluv.homeworkfifteenth;


import android.content.Context;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BeanDao {
    private Dao<Bean,Integer> beanDao;
    private DatabaseHelper helper;//这里用到了之前建立的继承OrmLiteSqliteOpenHelper的类

    public BeanDao(Context context){
        try {
            helper = DatabaseHelper.getHelper(context);
            beanDao = helper.getDao(Bean.class);
            if(beanDao == null){}
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //增加操作
    public void addBean(Bean bean){
        try {
            beanDao.create(bean);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //查询即遍历操作
    public List<Bean> queryAll(){
        ArrayList<Bean> beans = null;
        try {
            beans = (ArrayList<Bean>) beanDao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return beans;
    }

    //按照标题查找
    public Bean queryByTitle(String title){
        Bean bean = new Bean();
//        List<Bean> list = beanDao.queryForAll().where().eq()//貌似不可以根据标题查找，只能根据id，但是由于收藏页的listview只有标题信息，没有id，所以这里是个坑，请老师帮我看看！！
        return bean;
    }
}
