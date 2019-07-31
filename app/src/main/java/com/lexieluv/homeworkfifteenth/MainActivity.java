package com.lexieluv.homeworkfifteenth;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

//项目还有很多问题，但是不知道怎么处理，请老师不要骂我。之前有个老师把我骂惨了，55555555.
public class MainActivity extends AppCompatActivity {
    private Button left,collect,right;
    private ListView lv_content;
    Bean b;

    String url = "http://www.imooc.com/api/teacher?type=3&cid=";
    String urll = null;
    int num = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Asynctaskk asynctaskk;
        setTitle("");
        init();//初始化控件
        setClick();//给按钮添加监听事件,操作网络解析，并绑定
    }



    private void setClick() {
        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //由于子线程中不能toast，因此下载外面
                if(num <= 1) {
                    Toast.makeText(MainActivity.this, "前面没有了，这是第一篇！", Toast.LENGTH_SHORT).show();
                }else{
                    //新建子线程进行网络下载操作
                    new Thread(){
                        public void run(){
                            //仅改变文字信息
                            num--;
                            String numLeft = String.valueOf(num);
                            urll = url + numLeft;
                            Log.d("=======urlLeft=======", urll);
                            Asynctaskk asynctaskk = new Asynctaskk(MainActivity.this,lv_content);
                            asynctaskk.execute(urll);
                            //下面这段我忘记为什么会有了，因为点击下一篇都没有这个，好奇怪，老师看到的话可以告诉我一下吗
                            try {
                                String jsonString = readStream(new URL(urll).openStream());
                                Gson gson = new Gson();
                                b = gson.fromJson(jsonString,Bean.class);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }
                    }.start();
                }
            }
        });

        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //仅改变文字信息
                    num++;
                    String numRight = String.valueOf(num);
                    urll = url + numRight;
                    Log.d("=======urlRight=======",urll);
                    Asynctaskk asynctaskk = new Asynctaskk(MainActivity.this,lv_content);
                    asynctaskk.execute(urll);
            }
        });

        collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //判断是否已经被收藏过，只能遍历查找数据库来判断
                List<Bean> query = new BeanDao(MainActivity.this).queryAll();
                if(query.contains(b)){
                    Toast.makeText(MainActivity.this,"您已经收藏过了哦~",Toast.LENGTH_SHORT).show();
                }else {
                    //点击就可以收藏到数据库中
                    String author = b.getAuthor();
                    String title = b.getTitle();
                    String content = b.getContent();
                    new BeanDao(MainActivity.this).addBean(b);
                    Log.d("=========1======", b.toString());
                    Toast.makeText(MainActivity.this, "成功收藏啦~", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void init() {
        left = findViewById(R.id.btn_left);
        collect = findViewById(R.id.btn_collect);
        right = findViewById(R.id.btn_right);

        lv_content = findViewById(R.id.lv_content);
    }

    //“我的收藏”菜单设置
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.collection:
                //这里点击我的收藏就会闪退，暂时没找到原因，请老师帮我看看
                startActivity(new Intent(MainActivity.this,MyCollection.class));
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    //进行网络操作
    class Asynctaskk extends AsyncTask<String,Void,List<Bean>>{

        private Context context;
        ListView lv_content;

        public Asynctaskk(Context context, ListView lv_content){
            super();
            this.context = context;
            this.lv_content = lv_content;
        }

        private List<Bean> getJsonData(String u){
            List<Bean> list = new ArrayList<>();
            try {
                String jsonString = readStream(new URL(u).openStream());
//                Log.i("======jsonString=====",jsonString);
                JSONObject jsonObject;
//                Bean bean;

                jsonObject = new JSONObject(jsonString);
                Log.d("=======jsonObject=====",jsonObject.toString());
                JSONObject object = jsonObject.getJSONObject("data");
                b = new Bean();
                b.setTitle(object.getString("title"));
                b.setAuthor(object.getString("author"));
                b.setContent(object.getString("content"));
                list.add(b);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return list;
        }



        @Override
        protected List<Bean> doInBackground(String... strings) {
            return getJsonData(strings[0]);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(List<Bean> beans) {
            super.onPostExecute(beans);
            MainAdapter adapter = new MainAdapter(context,beans);
            lv_content.setAdapter(adapter);
        }
    }

    //这个本来在asynctaskk类里面，结果我单独拿出来就可以用了。我也不知道为什么，如果可以的话希望老师看到了告诉我一下。
    private String readStream(InputStream inputStream) {
        InputStreamReader isr;
        String result = "";
        try {
            String line = "";
            isr = new InputStreamReader(inputStream,"utf-8");
            BufferedReader br = new BufferedReader(isr);
            while ((line = br.readLine()) != null){
                result += line;
//                Log.i("=======line=======",line);
                Log.i("=======result======",result);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
