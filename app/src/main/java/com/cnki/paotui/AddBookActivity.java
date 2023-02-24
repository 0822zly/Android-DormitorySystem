package com.cnki.paotui;

import static com.cnki.paotui.Ikeys.TYPE;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.cnki.paotui.adapter.BookAdaper;
import com.cnki.paotui.adapter.SelectPhotoAdapter;
import com.cnki.paotui.bean.Book;
import com.cnki.paotui.db.Order;
import com.cnki.paotui.db.OrderDao;
import com.cnki.paotui.utils.JDBC;
import com.cnki.paotui.utils.SPUtil;
import com.cnki.paotui.utils.ThreadPoolExecutorUtil;
import com.huantansheng.easyphotos.EasyPhotos;
import com.huantansheng.easyphotos.callback.SelectCallback;
import com.huantansheng.easyphotos.models.album.entity.Photo;
import com.kongzue.dialogx.dialogs.InputDialog;
import com.kongzue.dialogx.interfaces.OnInputDialogButtonClickListener;
import com.kongzue.dialogx.util.InputInfo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
