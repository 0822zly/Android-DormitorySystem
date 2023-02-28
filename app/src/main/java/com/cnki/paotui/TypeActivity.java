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
import com.cnki.paotui.adapter.BookOrderAdaper;
import com.cnki.paotui.bean.Book;
import com.cnki.paotui.utils.ThreadPoolExecutorUtil;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android Studio.
 * User: cjr
 * Date: 2023/2/22
 * Time: 23:16
 * 此类的作用为：男频女频推荐作者
 */
public class TypeActivity extends BaseActivity{
    Handler handler=new Handler(Looper.getMainLooper());
      private BookAdaper bookOrderAdaper;;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragmentlist);
        getSupportActionBar().setTitle(arrTitle[getIntent().getIntExtra("key",0)]);
        RecyclerView recyclerView=findViewById(R.id.listview);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        bookOrderAdaper = new BookAdaper(R.layout.item_home_book);
        bookOrderAdaper.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                Intent intent=new Intent(mContext,bookDetailsActivity.class);
                intent.putExtra("url",bookOrderAdaper.getData().get(position).url);
                mContext.startActivity(intent);
            }
        });
        recyclerView.setAdapter(bookOrderAdaper);
        getData();
    }
    List<Book> list=new ArrayList();
    String []arr={"https://www.bbiquge.net/fenlei/1_1/",//男性
            "https://www.bbiquge.net/fenlei/3_1/",//女性
            "https://www.bbiquge.net/fenlei/2_1/",//热门
            "https://www.bbiquge.net/fenlei/4_1/"};//最新
    String []arrTitle={"男频",//男性
            "女频",//女性
            "推荐",//热门
            "最新"};//最新

    /**
     * 获取图书列表信息
     */
    private void getData(){
        ThreadPoolExecutorUtil.doTask(new Runnable() {

            @Override
            public void run() {
                Document doc = null;
                try {
                    doc = Jsoup.connect(arr[getIntent().getIntExtra("key",0)]).get();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Elements shu_cont = doc.getElementsByClass("titlelist");
                Elements liList = shu_cont.get(0).getElementsByTag("li");
                for (int i = 0; i <liList.size() ; i++) {
                    Book book=new Book();
                    Element element = liList.get(i);
                    //  Elements elementsA=  element.getElementsByAttribute("a");
                    //获取标题和id
                    Elements elementsimg=  element.getElementsByClass("zp");
                    if(elementsimg.size()>0){
                        Element elementAA=  elementsimg.get(0);
                        Elements elementAAs=  elementAA.getElementsByTag("a");
                        if(elementAAs.size()>0){
                            Element element1 = elementAAs.get(0);
                            book.title=element1.html();
                            String href =element1.attr("href");
                            book.id=href.split("/")[href.split("/").length-1];
                            book.url="https://www.bbiquge.net/book/"+book.id+"/";
                        }
                    }
                    //获取最新章节
                    Elements elementszz=  element.getElementsByClass("zz");
                    if(elementszz.size()>0){
                        Element elementAA=  elementszz.get(0);
                        Elements elementAAs=  elementAA.getElementsByTag("a");
                        if(elementAAs.size()>0){
                            Element element1 = elementAAs.get(0);
                            book.newchapter=element1.html();

                        }
                    }
                    //获取作者
                    Elements elementsauthor=  element.getElementsByClass("author");
                    if(elementsauthor.size()>0){
                        Element elementAA=  elementsauthor.get(0);
                        book.auther=elementAA.html();
                    }
                    //获取时间
                    Elements elementssj = element.getElementsByClass("sj");
                    if(elementssj.size()>0){
                        Element elementAA=  elementssj.get(0);
                        book.time=elementAA.html();
                    }
                    list.add(book);

                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        bookOrderAdaper.setNewInstance(list);

                    }
                });
            }
        });
    }
}
