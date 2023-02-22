package com.cnki.paotui.ui.home;


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

import com.cnki.paotui.R;
import com.cnki.paotui.adapter.BookAdaper;
import com.cnki.paotui.bean.Book;
import com.cnki.paotui.utils.ThreadPoolExecutorUtil;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private View mRootView;
    RecyclerView recyclerView;
    Handler handler=new Handler(Looper.getMainLooper());
    private BookAdaper bookAdaper;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView=mRootView.findViewById(R.id.listview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        bookAdaper = new BookAdaper(R.layout.item_home_book);
        recyclerView.setAdapter(bookAdaper);
        ThreadPoolExecutorUtil.doTask(new Runnable() {
            @Override
            public void run() {
                getData();
            }
        });
        mRootView.findViewById(R.id.nanpin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        mRootView.findViewById(R.id.nvpin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        mRootView.findViewById(R.id.tuijian).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        mRootView.findViewById(R.id.zuixin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        return mRootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }
    List<Book> list=new ArrayList();
    private void getData(){
        Document doc = null;
        try {
            doc = Jsoup.connect("https://www.bbiquge.net").get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements shu_cont = doc.getElementsByClass("item");
        for (int i = 0; i <shu_cont.size() ; i++) {
            Book book=new Book();
            Element element = shu_cont.get(i);
            //  Elements elementsA=  element.getElementsByAttribute("a");
            //获取封面
            Elements elementsimg=  element.getElementsByTag("img");
            if(elementsimg.size()>0){
                Element elementAA=  elementsimg.get(0);
                String href=  elementAA.attr("src");
                book.cover=href;
            }
            //获取 标题 链接 id
            Elements elementsA=  element.getElementsByTag("a");
            if(elementsA.size()>0){
                Element elementAA=  elementsA.get(0);
                String title=  elementAA.attr("title");
                String href=  elementAA.attr("href");
                book.title=title;
                book.url=href;
                book.id=href.split("/")[href.split("/").length-1];
            }
            //获取 作者
            Elements elementsBlue=  element.getElementsByClass("blue");
            if(elementsBlue.size()>0){
                Element elementAA=  elementsBlue.get(0);
                String auther=  elementAA.html();
                book.auther=auther;
            }
            //获取 简介
            Elements elementsInfo=  element.getElementsByClass("info");
            if(elementsInfo.size()>0){
                Element elementAA=  elementsInfo.get(0);
                String info=  elementAA.html();
                book.content=info;
            }
            list.add(book);
            handler.post(new Runnable() {
                @Override
                public void run() {
                    bookAdaper.setNewInstance(list);
                }
            });
        }
    }
}