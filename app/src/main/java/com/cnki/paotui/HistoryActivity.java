package com.cnki.paotui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.SimpleAdapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.cnki.paotui.adapter.BookOrderAdaper;
import com.cnki.paotui.adapter.HistoryAdaper;
import com.cnki.paotui.bean.Book;
import com.cnki.paotui.utils.JDBC;
import com.cnki.paotui.utils.ThreadPoolExecutorUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
//搜索历史界面
public class HistoryActivity extends BaseActivity {

    private RecyclerView recyclerview;
    private HistoryAdaper bookOrderAdaper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auther);
        recyclerview = findViewById(R.id.recyclerview);
           bookOrderAdaper = new HistoryAdaper(R.layout.item_history);

        recyclerview.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerview.setAdapter(bookOrderAdaper);
        getData();
    }
    private void getData(){
        ThreadPoolExecutorUtil.doTask(new Runnable() {
            @Override
            public void run() {
                //获取所有的搜索历史
                Set<String> books = JDBC.getInstance().getAllSearch();
                if(books!=null&&books.size()>0) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            bookOrderAdaper.addData(new ArrayList<>(books));
                        }
                    });
                }
            }
        });
    }

    Handler handler=new Handler(Looper.getMainLooper());

}