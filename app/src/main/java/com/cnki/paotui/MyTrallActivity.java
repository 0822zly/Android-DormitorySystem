package com.cnki.paotui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.cnki.paotui.adapter.LvYouAdapter;
import com.cnki.paotui.adapter.MyTrallAdapter;
import com.cnki.paotui.databinding.ActivityMytrallBinding;
import com.cnki.paotui.db.MyTravell;
import com.cnki.paotui.utils.JDBC;
import com.gyf.immersionbar.ImmersionBar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android Studio.
 * User: cjr
 * Date: 2023/1/15
 * Time: 13:46
 * 此类的作用为：
 */
public class MyTrallActivity extends BaseActivity{
    MyTrallAdapter kvAdapter=new MyTrallAdapter(R.layout.item_mytrall);
    List<MyTravell> titles = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMytrallBinding  viewBinding = ActivityMytrallBinding.inflate(getLayoutInflater());
         setContentView(viewBinding.getRoot());
         viewBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        viewBinding.recyclerView.setAdapter(kvAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }
    private void getData(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                titles.clear();
                titles= JDBC.getInstance().queryAllMyTrall();
                handler.post(new Runnable() {
                    @Override
                    public void run() {

                        kvAdapter.setNewInstance(titles);
                    }
                });
            }
        }).start();
    }
    Handler handler=new Handler(Looper.getMainLooper()){};
}
