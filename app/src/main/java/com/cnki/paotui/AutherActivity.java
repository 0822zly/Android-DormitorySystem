package com.cnki.paotui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.cnki.paotui.adapter.BookAdaper;
import com.cnki.paotui.adapter.BookOrderAdaper;
import com.cnki.paotui.bean.Book;
import com.cnki.paotui.utils.JDBC;
import com.cnki.paotui.utils.ThreadPoolExecutorUtil;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class AutherActivity extends BaseActivity {

    private RecyclerView recyclerview;
    private BookAdaper bookOrderAdaper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auther);
        recyclerview = findViewById(R.id.recyclerview);
        getSupportActionBar().setTitle(getIntent().getStringExtra("title"));
           bookOrderAdaper = new BookAdaper(R.layout.item_home_book);
        bookOrderAdaper.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                Intent intent=new Intent(mContext,bookDetailsActivity.class);
                intent.putExtra("url",bookOrderAdaper.getData().get(position).url);
                mContext.startActivity(intent);
            }
        });
        recyclerview.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerview.setAdapter(bookOrderAdaper);
        getData();
    }
    //https://www.bbiquge.net/modules/article/authorarticle.php?author=%BB%A8%D3%C4%C9%BD%D4%C2
    private void getData(){
        ThreadPoolExecutorUtil.doTask(new Runnable() {
            @Override
            public void run() {
                List<Book> books = JDBC.getInstance().queryAllMyBookByAuther(getIntent().getStringExtra("title"));
                if(books!=null&&books.size()>0) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            bookOrderAdaper.addData(books);
                        }
                    });
                }
            }
        });
    }

    Handler handler=new Handler(Looper.getMainLooper());

}