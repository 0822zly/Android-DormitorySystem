package com.cnki.paotui;

import static com.huantansheng.easyphotos.utils.media.MediaScannerConnectionUtils.refresh;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Html;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.cnki.paotui.bean.Book;
import com.cnki.paotui.databinding.ActivityReadBinding;
import com.cnki.paotui.ui.pop.SettingPop;
import com.cnki.paotui.utils.SPUtil;
import com.cnki.paotui.utils.ThreadPoolExecutorUtil;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import razerdp.basepopup.BasePopupWindow;

public class ReadActivity extends AppCompatActivity {

    private com.cnki.paotui.databinding.ActivityReadBinding viewBinding;
    Map<String,String> map=new HashMap<>();
    Map<String,String> maptitle=new HashMap<>();
    String url;
    String preurl;
    String nexturl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewBinding = ActivityReadBinding.inflate(getLayoutInflater());
        setContentView(viewBinding.getRoot());
        url=getIntent().getStringExtra("url");
        getSupportActionBar().setTitle(getIntent().getStringExtra("title"));
        viewBinding.next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!TextUtils.isEmpty(nexturl)&&nexturl.contains(".html")) {
                    url = nexturl;
                    getData();
                }else {
                    Toast.makeText(ReadActivity.this, "没有下一页了", Toast.LENGTH_SHORT).show();
                }
            }
        });
        viewBinding.pre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!TextUtils.isEmpty(preurl)&&preurl.contains(".html")){
                url=preurl;
                    getData();
                }else {
                    Toast.makeText(ReadActivity.this, "没有上一页了", Toast.LENGTH_SHORT).show();
                }
            }
        });
        getData();
        sefresh();
    }
    private void sefresh(){
        if(SPUtil.getInstance().getBoolean("isnight")){
            viewBinding.scroll.setBackgroundColor(Color.parseColor("#181818"));
            viewBinding.tvContent.setTextColor(Color.parseColor("#777777"));
        }else {
            viewBinding.scroll.setBackgroundColor(Color.parseColor(SPUtil.getInstance().getString("textcolor")));
            viewBinding.tvContent.setTextSize(TypedValue.COMPLEX_UNIT_SP, SPUtil.getInstance().getInt("textsize"));
            viewBinding.tvContent.setTextColor(Color.parseColor("#4A4438"));
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sss, menu);
        return true;
    }
    SettingPop settingPop;
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_settings) {
            if(settingPop==null){
                settingPop=new SettingPop(this);
                View jian=settingPop.findViewById(R.id.bt_jian);
                View jia=settingPop.findViewById(R.id.bt_jia);
                View bg1=settingPop.findViewById(R.id.bg1);
                View bg2=settingPop.findViewById(R.id.bg2);
                View bg3=settingPop.findViewById(R.id.bg3);
                View bg4=settingPop.findViewById(R.id.bg4);
                View tv_light=settingPop.findViewById(R.id.tv_light);
                View tv_night=settingPop.findViewById(R.id.tv_night);
                TextView tvsize=settingPop.findViewById(R.id.tv_pop_size);
                CheckBox ishuyan=settingPop.findViewById(R.id.ishuyan);
                ishuyan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        if(b){
                            viewBinding.scroll.setBackgroundColor(Color.parseColor("#E8D3A9"));
                        }else {
                            sefresh();
                        }
                    }
                });
                bg1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        SPUtil.getInstance().setValue("textcolor", "#C8E4CB");
                        sefresh();
                    }
                });
                bg2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        SPUtil.getInstance().setValue("textcolor", "#E8D3A9");
                        sefresh();
                    }
                });
                bg3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        SPUtil.getInstance().setValue("textcolor", "#EBEEF7");
                        sefresh();
                    }
                });
                bg4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        SPUtil.getInstance().setValue("textcolor", "#DFC8A8");
                        sefresh();
                    }
                });
                tv_light.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        SPUtil.getInstance().setValue("isnight", false);
                        sefresh();
                    }
                });
                tv_night.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        SPUtil.getInstance().setValue("isnight", true);
                        sefresh();
                    }
                });
                jian.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                      int size=  SPUtil.getInstance().getInt("textsize");
                      if(size==10){
                          Toast.makeText(ReadActivity.this, "范围为10-20", Toast.LENGTH_SHORT).show();
                      }else {
                          tvsize.setText((size - 1)+"");
                          SPUtil.getInstance().setValue("textsize", size - 1);
                          sefresh();
                      }
                    }
                });
                jia.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int size=  SPUtil.getInstance().getInt("textsize");
                        if(size==20){
                            Toast.makeText(ReadActivity.this, "范围为10-20", Toast.LENGTH_SHORT).show();
                        }else {
                            tvsize.setText((size + 1)+"");
                            SPUtil.getInstance().setValue("textsize", size + 1);
                            sefresh();
                        }
                    }
                });

            }
            settingPop.showPopupWindow();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }
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
                Element elementInfo = doc.getElementById("content");
               final String content=elementInfo.html();
                map.put(url,content);
                Element preview = doc.getElementById("link-preview");
                Element next = doc.getElementById("link-next");
                preurl=preview.attr("href");
                nexturl=next.attr("href");
                System.out.println("url:"+url);
                System.out.println("preurl:"+preurl);
                System.out.println("nexturl:"+nexturl);
                Elements h1s=  doc.getElementsByTag("h1");
                if(h1s!=null&&h1s.size()>0){
                    Element h1=h1s.get(0);
                    String title=h1.text();
                    maptitle.put(url,title);
                    handler.post(new Runnable() {

                        @Override
                        public void run() {
                            // viewBinding.tvContent.setText(content);
                            getSupportActionBar().setTitle(title);
                        }
                    });
                }
                handler.post(new Runnable() {

                    @Override
                    public void run() {
                       // viewBinding.tvContent.setText(content);
                        viewBinding.scroll.scrollTo(0,0);
                      viewBinding.tvContent.setText(Html.fromHtml(content));
                    }
                });
            }
        });

    }
Handler handler=new Handler(Looper.getMainLooper());
}