package com.cnki.paotui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.cnki.paotui.adapter.BookAdaper;
import com.cnki.paotui.bean.Book;
import com.cnki.paotui.utils.JDBC;
import com.cnki.paotui.utils.ThreadPoolExecutorUtil;

import java.util.List;
//我的收藏的图书
public class AddBookActivity extends BaseActivity {
    BookAdaper bookAdaper;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragmentlist);
        RecyclerView recyclerView = findViewById(R.id.listview);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setHasFixedSize(true);
        bookAdaper = new BookAdaper(R.layout.item_home_book);
        recyclerView.setAdapter(bookAdaper);
        bookAdaper.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                Intent intent=new Intent(mContext, bookDetailsActivity.class);
                intent.putExtra("url", bookAdaper.getData().get(position).url);
                mContext.startActivity(intent);
            }
        });
    }
    Handler handler=new Handler(Looper.getMainLooper());
    private void getData(){
        ThreadPoolExecutorUtil.doTask(new Runnable() {
            @Override
            public void run() {
                //查询我收藏的图书
                List<Book> books = JDBC.getInstance().queryAllCollentBook();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        bookAdaper.setNewInstance(books);
                    }
                });
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if(App.user!=null) {
            getData();
        }
    }
}
