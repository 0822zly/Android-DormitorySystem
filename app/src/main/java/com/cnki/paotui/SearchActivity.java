package com.cnki.paotui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.cnki.paotui.adapter.BookOrderAdaper;
import com.cnki.paotui.bean.Book;
import com.cnki.paotui.utils.JDBC;
import com.cnki.paotui.utils.ThreadPoolExecutorUtil;
import com.j256.ormlite.dao.EagerForeignCollection;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 搜索图书界面
 */
public class SearchActivity extends BaseActivity {


    private androidx.appcompat.widget.SearchView searchView;
    private SmartRefreshLayout refreshLayout;
    private BookOrderAdaper bookOrderAdaper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        getSupportActionBar().hide();
        searchView = findViewById(R.id.searchvew);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                bookOrderAdaper.setNewInstance(new ArrayList<>());
                getData();
                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        bookOrderAdaper = new BookOrderAdaper(R.layout.item_book_order);
        bookOrderAdaper.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                Intent intent=new Intent(mContext,bookDetailsActivity.class);
                intent.putExtra("url",bookOrderAdaper.getData().get(position).url);
                mContext.startActivity(intent);
            }
        });
        RecyclerView recyclerView = findViewById(R.id.listview);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(bookOrderAdaper);
        refreshLayout = (SmartRefreshLayout) findViewById(R.id.smartrefreshlayout);
        refreshLayout.setEnableLoadMore(true);
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                getData();
            }
        });
    }

    /**
     * 搜索图书
     */
    private void getData(){
        ThreadPoolExecutorUtil.doTask(new Runnable() {
            @Override
            public void run() {
                List<Book> books = JDBC.getInstance().queryAllMyBook(searchView.getQuery().toString(),bookOrderAdaper.getData().size(), 10);
                if(books!=null&&books.size()>0) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            bookOrderAdaper.addData(books);
                            refreshLayout.finishLoadMore();
                        }
                    });
                }
                JDBC.getInstance().insertSearch(searchView.getQuery().toString());
            }
        });
    }
    Handler handler=new Handler(Looper.getMainLooper());
}