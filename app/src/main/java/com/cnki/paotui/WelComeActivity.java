package com.cnki.paotui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.cnki.paotui.db.User;
import com.cnki.paotui.ui.login.LoginActivity;
import com.cnki.paotui.utils.JDBC;
import com.cnki.paotui.utils.SPUtil;
import com.cnki.paotui.utils.ThreadPoolExecutorUtil;
import com.gyf.immersionbar.ImmersionBar;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import java.io.IOException;

public class WelComeActivity  extends BaseActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        getSupportActionBar().hide();
        ImmersionBar.with(this).transparentBar().init();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if(SPUtil.getInstance().getBoolean(Ikeys.ISNOTFRISTCOMEIN)){
                    Intent intent=new Intent(mContext, MainActivity.class);
                    mContext.startActivity(intent);
                }else {
                    Intent intent=new Intent(mContext,MainActivity.class);
                    mContext.startActivity(intent);
                }
                SPUtil.getInstance().setValue(Ikeys.ISNOTFRISTCOMEIN,true);
                finish();
            }
        },2000);
        if (SPUtil.getInstance().getBoolean(Ikeys.ISAUTOLOGIN)) {
            if (!TextUtils.isEmpty(SPUtil.getInstance().getString(Ikeys.USERNAME)) && !TextUtils.isEmpty(SPUtil.getInstance().getString(Ikeys.PASSWORD))) {


                ThreadPoolExecutorUtil.doTask(new Runnable() {
                    @Override
                    public void run() {
                        User user = JDBC.getInstance().queryuser(SPUtil.getInstance().getString(Ikeys.USERNAME),SPUtil.getInstance().getString(Ikeys.PASSWORD));
                        App.user=user;

                    }
                });
            }
        }
        //https://www.bbiquge.net/fenlei/1_1/
        ThreadPoolExecutorUtil.doTask(new Runnable() {
            @Override
            public void run() {
                Document doc = null;
                try {
                    doc = Jsoup.connect("https://www.bbiquge.net/fenlei/1_1/").get();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Elements shu_cont = doc.getElementsByClass("shu_img");
                for (int i = 0; i <shu_cont.size() ; i++) {
                    Element element = shu_cont.get(i);
                    //  Elements elementsA=  element.getElementsByAttribute("a");
                    Elements elementsA=  element.getElementsByTag("a");
                    if(elementsA.size()>0){
                        Element elementAA=  elementsA.get(0);
                      String href=  elementAA.attr("href");
                      String title=  elementAA.attr("title");
                        System.out.println("链接为："+href);
                        System.out.println("标题为："+title);
                        Elements elementsurl=  elementAA.getElementsByTag("img");
                        if(elementsurl.size()>0){
                            System.out.println("image为："+elementsurl.get(0).attr("src"));

                        }
                    }
                }

            }
        });

    }
    Handler handler=new Handler(Looper.getMainLooper());

    public static void main(String[] args) {

    }
}
