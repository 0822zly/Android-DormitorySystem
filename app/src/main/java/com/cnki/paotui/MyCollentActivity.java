package com.cnki.paotui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cnki.paotui.adapter.LvYouAdapter;
import com.cnki.paotui.db.Travell;
import com.cnki.paotui.utils.JDBC;

import java.util.List;

public class MyCollentActivity extends BaseActivity{
    Handler myHandler = new Handler(Looper.getMainLooper()){};
    private LvYouAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_collent);
        RecyclerView recyclerView=(RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        adapter = new LvYouAdapter(R.layout.item_lvyougonglue);
        recyclerView.setAdapter(adapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        new Thread(new Runnable() {

            @Override
            public void run() {
                List<Travell> list = JDBC.getInstance().queryAllCollentTrall();
                myHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        adapter.setNewInstance(list);
                    }
                });
            }
        }).start();
    }
}
