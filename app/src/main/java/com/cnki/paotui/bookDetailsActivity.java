package com.cnki.paotui;

import android.content.Intent;
import android.graphics.Paint;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.cnki.paotui.adapter.BookChapterAdaper;
import com.cnki.paotui.adapter.BookOrderAdaper;
import com.cnki.paotui.bean.Book;
import com.cnki.paotui.databinding.ActivityBookBinding;
import com.cnki.paotui.utils.JDBC;
import com.cnki.paotui.utils.ThreadPoolExecutorUtil;
import com.cnki.paotui.utils.ViewUtils;
import com.google.android.flexbox.FlexboxLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;

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
public class bookDetailsActivity extends BaseActivity{

    String url;
    private com.cnki.paotui.databinding.ActivityBookBinding viewBinding;
    private BookChapterAdaper bookChapterAdaper;
    private boolean isCollent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        url=getIntent().getStringExtra("url");
        viewBinding = ActivityBookBinding.inflate(getLayoutInflater());

          setContentView(viewBinding.getRoot());
          getSupportActionBar().hide();
        viewBinding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        RecyclerView recyclerView=findViewById(R.id.listview);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        bookChapterAdaper = new BookChapterAdaper(R.layout.item_chapter);
        bookChapterAdaper.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                Intent intent=new Intent(mContext,ReadActivity.class);
                intent.putExtra("url",url+bookChapterAdaper.getData().get(position).url);
                intent.putExtra("title",bookChapterAdaper.getData().get(position).title);
                mContext.startActivity(intent);
            }
        });
        viewBinding.tvAuther.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);

        recyclerView.setAdapter(bookChapterAdaper);
        viewBinding.tvAuther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(mContext,AutherActivity.class);
                intent.putExtra("title",book.auther);
                mContext.startActivity(intent);
            }
        });
        viewBinding.smartrefreshlayout.setEnableLoadMore(true);
        viewBinding.smartrefreshlayout.setEnableRefresh(false);
        viewBinding.smartrefreshlayout.setOnLoadMoreListener(new OnLoadMoreListener() {

            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if(havemore){
                    if(index>=total){
                        Toast.makeText(mContext, "没有更过了", Toast.LENGTH_SHORT).show();
                        viewBinding.smartrefreshlayout.finishLoadMore();
                    }else {
                        index++;
                        getChapter();
                    }
                }else {
                    Toast.makeText(mContext, "没有更过了", Toast.LENGTH_SHORT).show();
                    viewBinding.smartrefreshlayout.finishLoadMore();
                }
            }
        });
        viewBinding.collentt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ThreadPoolExecutorUtil.doTask(new Runnable() {
                    @Override
                    public void run() {
                        if(isCollent){
                            JDBC.getInstance().deleteBook(book.id);
                        }else {
                            JDBC.getInstance().insertBook(book);
                        }
                        refreshCollent();
                    }
                });

            }
        });
        getData();
        getChapter();

    }
    private void refreshCollent(){
        ThreadPoolExecutorUtil.doTask(new Runnable() {
            @Override
            public void run() {
                isCollent = JDBC.getInstance().queryBookISCollent(book.id);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if(isCollent){
                            viewBinding.collentt.setBackground(mContext.getResources().getDrawable(R.mipmap.icon_new_collent));
                        }else {
                            viewBinding.collentt.setBackground(mContext.getResources().getDrawable(R.mipmap.icon_new_uncollent));
                        }
                    }
                });
            }
        });


    }

    int index=1;
   Book book=new Book();
   List<Book> listTuijian=new ArrayList<>();
    private void getData(){
        ThreadPoolExecutorUtil.doTask(new Runnable() {

            @Override
            public void run() {
                Document doc = null;
                try {
                    doc = Jsoup.connect(url).get();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Element shu_cont = doc.getElementById("bookdetail");
                Elements elementsimgs=  shu_cont.getElementsByTag("img");
                //图片
                if(elementsimgs!=null&&elementsimgs.size()>0){
                    Element element = elementsimgs.get(0);
                    String src =element.attr("src");
                    book.cover=src;
                    book.id=url.split("/")[url.split("/").length-1];

                }
                Element elementInfo = doc.getElementById("info");
                Elements elementsh1s=  elementInfo.getElementsByTag("h1");
                if(elementsh1s!=null&&elementsh1s.size()>0){
                    Element element = elementsh1s.get(0);
                    String title =element.text();
                    book.title=title;//标题
                    Elements elementsa=  element.getElementsByTag("a");
                    if(elementsa!=null&&elementsa.size()>0){
                        Element element1 = elementsa.get(0);
                        String auther=element1.html();
                        book.auther=auther;//作者
                        book.url=url;
                    }
                }
                Element elementintro = doc.getElementById("intro");
                Elements elementsbrs=  elementintro.getElementsByTag("br");
                if(elementsbrs!=null&&elementsbrs.size()>0){
                    String content=elementintro.html();
                    book.content=content;
                }
                //获取推荐数据
                Elements tjlist = doc.getElementsByClass("tjlist");
                if(tjlist!=null&&tjlist.size()>0){
                    Element element = tjlist.get(0);
                    Elements as = element.getElementsByTag("a");
                    if(as!=null&&as.size()>0){
                        for (Element elementA:as
                             ) {
                            Book book1=new Book();
                            book1.url=elementA.attr("href");
                            book1.title=elementA.text();
                            listTuijian.add(book1);
                        }
                    }
                }
                JDBC.getInstance().insertHistoryBook(book);
                refreshCollent();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Glide.with(mContext).load(book.cover).into(viewBinding.itemImage);
                        viewBinding.tvTitle.setText(TextUtils.isEmpty(book.title)?"":book.title);
                        viewBinding.tvAuther.setText(TextUtils.isEmpty(book.auther)?"":book.auther);
                        viewBinding.tvInfo.setText(Html.fromHtml(TextUtils.isEmpty(book.content)?"":book.content));
                       viewBinding.sdsdsd.setText(TextUtils.isEmpty(book.title)?"":book.title);
                        for (Book book1 : listTuijian) {
                            TextView tv=new TextView(mContext);
                            tv.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent intent=new Intent(mContext,bookDetailsActivity.class);
                                    intent.putExtra("url",book1.url);
                                    mContext.startActivity(intent);
                                }
                            });
                            viewBinding.itemLineMark.addView(tv);
                            tv.setTextColor(mContext.getResources().getColor(R.color.tinkcolor));
                            setbackRadiusDrawable(tv,4f,mContext.getResources().getColor(R.color.bg_04));
                            tv.setPadding(10,5,10,5);
                            tv.setMaxLines(1);
                            tv.setText(book1.title);
                            FlexboxLayout.LayoutParams layoutParams=new  FlexboxLayout.LayoutParams(FlexboxLayout.LayoutParams.WRAP_CONTENT, FlexboxLayout.LayoutParams.WRAP_CONTENT);
                            layoutParams.setMargins(20,10,0,20);
                            tv.setLayoutParams(layoutParams);

//                            viewBinding.itemLineMark.addView(TextView(context).apply {
//
//                                setPadding(10,5,10,5)
//                                // isSingleLine
//                                maxLines=1
//                                text=it
//                                layoutParams=  FlexboxLayout.LayoutParams(FlexboxLayout.LayoutParams.WRAP_CONTENT, FlexboxLayout.LayoutParams.WRAP_CONTENT).apply {
//                                    setMargins(10,0,0,10)
//                                }
//
//                            })
                        }

                    }
                });
            }
        });

    }
    boolean havemore=true;
    int total=0 ;
    private void  setbackRadiusDrawable( View view,float radius,  int color) {
        GradientDrawable drawable =new GradientDrawable() ;
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setColor(color);
        float re=ViewUtils.dp2px(mContext, radius);
        drawable.setCornerRadius(re);
        // drawable.setStroke(ViewUtils.dp2px(context, ViewUtils.dp2px(context, 0F, Color.TRANSPARENT)
        view.setBackground(drawable);
    }
  private String getchapterUrl(){

      return "index_"+index+".html";
    }
    private void getChapter(){
        ThreadPoolExecutorUtil.doTask(new Runnable() {

            @Override
            public void run() {
                List<Chapter> list=new ArrayList<>();

                Document doc = null;
                try {
                    doc = Jsoup.connect(url+getchapterUrl()).get();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Elements ttname = doc.getElementsByClass("zjlist");
                Elements footer = doc.getElementsByClass("panel-footer text-center");
                if(footer!=null&&footer.size()>0){
                    Element element = footer.get(0);
                    if(element.text().equals("没有更多章节")){
                        havemore=false;
                    }
                }
                Elements control = doc.getElementsByClass("form-control");
                if(control!=null&&control.size()>0){
                    Element element = control.get(0);
                    Elements elementoption=  element.getElementsByTag("option");
                    total=elementoption.size();
                    havemore=true;
                }
                if(ttname!=null&&ttname.size()>0){
                    Element element = ttname.get(0);
                    Elements elementsdds=  element.getElementsByTag("dd");
                    for(int i=0;i<elementsdds.size();i++){
                        Element dd= elementsdds.get(i);
                        Elements elementsA=  dd.getElementsByTag("a");
                        if(elementsA!=null&&elementsA.size()>0){
                            Element elementA = elementsA.get(0);
                            Chapter chapter=new Chapter();
                            chapter.url=elementA.attr("href");
                            chapter.title=elementA.html();
                            list.add(chapter);
                        }


                    }

                }

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        bookChapterAdaper.addData(list);
                        viewBinding.smartrefreshlayout.finishLoadMore();
                    }
                });
            }
        });
    }
    Handler handler=new Handler(Looper.getMainLooper());
    public static class Chapter{
        public String title;
        public String url;
        public String in;
    }
}
