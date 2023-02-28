package com.cnki.paotui.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.cnki.paotui.App;
import com.cnki.paotui.R;
import com.cnki.paotui.adapter.BookAdaper;
import com.cnki.paotui.bean.Book;
import com.cnki.paotui.bookDetailsActivity;
import com.cnki.paotui.utils.JDBC;
import com.cnki.paotui.utils.ThreadPoolExecutorUtil;

import java.util.List;

public class DashboardFragment extends Fragment {


    private View mRootView;
    private BookAdaper bookAdaper;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_dashboard, container, false);

        RecyclerView recyclerView = mRootView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        bookAdaper = new BookAdaper(R.layout.item_home_book);
        recyclerView.setAdapter(bookAdaper);
        bookAdaper.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                Intent intent=new Intent(getContext(), bookDetailsActivity.class);
                intent.putExtra("url", bookAdaper.getData().get(position).url);
                getActivity().startActivity(intent);
            }
        });
        return mRootView;
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }
}