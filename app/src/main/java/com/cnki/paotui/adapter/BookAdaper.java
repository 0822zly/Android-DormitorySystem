package com.cnki.paotui.adapter;

import android.text.Html;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.cnki.paotui.R;
import com.cnki.paotui.bean.Book;
import com.cnki.paotui.utils.ThreadPoolExecutorUtil;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;

public class BookAdaper extends BaseQuickAdapter<Book, BaseViewHolder> {
    public BookAdaper(int layoutResId) {
        super(layoutResId);
    }

    public BookAdaper(int layoutResId, @Nullable List<Book> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder holder, Book item) {
        holder.setText(R.id.tv_title, item.title);
        holder.setText(R.id.tv_auther, item.auther);
        TextView tv_sds=holder.getView(R.id.tv_info);
        if(!TextUtils.isEmpty(item.content)) {
            tv_sds.setText(Html.fromHtml(item.content));
        }
        ImageView iamge = holder.getView(R.id.item_image);

        if(TextUtils.isEmpty(item.cover)){
            loadurl(item,item.url,iamge,tv_sds);
//            String cover="";
//            if(item.id.length()>3) {
//                 cover = item.id.substring(0, item.id.length() - 3);
//            }else {
//                cover="0";
//            }
//            String url="https://www.bbiquge.net/files/article/image/"+cover+"/"+item.id+"/"+item.id+"s.jpg";
//            System.out.println(url);
//            Glide.with(getContext()).load(url).into(iamge);
        }else {
            Glide.with(getContext()).load(item.cover).into(iamge);
        }
    }
   private void loadurl(Book book,String url,ImageView imageView,TextView tv_content){
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
               Element elementintro = doc.getElementById("intro");
               Elements elementsbrs=  elementintro.getElementsByTag("br");
               if(elementsbrs!=null&&elementsbrs.size()>0){
                   String content=elementintro.html();
                   tv_content.post(new Runnable() {
                       @Override
                       public void run() {
                           tv_content.setText(Html.fromHtml(content));
                           book.content=content;
                       }
                   });
               }
               //图片
               if(elementsimgs!=null&&elementsimgs.size()>0){
                   Element element = elementsimgs.get(0);
                   String src =element.attr("src");
                   imageView.post(new Runnable() {
                       @Override
                       public void run() {
                           Glide.with(getContext()).load(src).into(imageView);
    book.cover=src;
                       }
                   });

               }
           }
       });

   }
}
