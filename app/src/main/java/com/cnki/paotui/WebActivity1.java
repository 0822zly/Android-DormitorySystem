package com.cnki.paotui;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.cnki.paotui.db.Travell;
import com.cnki.paotui.utils.JDBC;
import com.cnki.paotui.utils.ThreadPoolExecutorUtil;

public class WebActivity1 extends BaseActivity{

    private TextView fanyi;
    private TextView tv_title_1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        getSupportActionBar().hide();
      //  mTravell= (Travell) getIntent().getSerializableExtra(Travell.class.getSimpleName());
        findViewById(R.id.back).setOnClickListener(v -> finish());
        WebView mWebView = findViewById(R.id.webview);
       WebSettings s= mWebView.getSettings();
        tv_title_1=findViewById(R.id.tv_title_1);
        if(!TextUtils.isEmpty(getIntent().getStringExtra("title")))
        tv_title_1.setText(getIntent().getStringExtra("title"));
        //

        //
        s.setBuiltInZoomControls(true);
        s.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);

//        s.layoutAlgorithm = WebSettings.LayoutAlgorithm.NARROW_COLUMNS
//        s.useWideViewPort = true
//        s.loadWithOverviewMode = true
//        s.setTextZoom(200);
//        s.domStorageEnabled = true
//        s.savePassword = true
//        s.saveFormData = true
        s.setUseWideViewPort(true);
        s.setLoadWithOverviewMode(true);
        s.setDomStorageEnabled(true);
        s.setJavaScriptEnabled(true);
        s.setGeolocationEnabled(true);
        s.setDisplayZoomControls(false);;
        s.setBlockNetworkImage(false);//解决图片不显示

      //  s.domStorageEnabled = true
        //这个是解决https里面有http的视频播放不出
        //这个是解决https里面有http的视频播放不出
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            s.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        mWebView.requestFocus();
        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String request) {
                view.loadUrl(request);
                return super.shouldOverrideUrlLoading(view, request);
            }
        });


        mWebView.loadUrl(getIntent().getStringExtra("url"));
        fanyi = findViewById(R.id.fanyi);
        fanyi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ThreadPoolExecutorUtil.doTask(new Runnable() {

                    @Override
                    public void run() {
                        if(isCollent){
                            JDBC.getInstance().deleteTrall(getIntent().getIntExtra("id",0));
                        }else {
                            JDBC.getInstance().addTrall(getIntent().getIntExtra("id",0));
                        }
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                RefreshView();
                            }
                        });

                    }
                });
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        RefreshView();
    }
    private boolean isCollent;

    Handler handler = new Handler(Looper.getMainLooper());
    private void RefreshView() {
        if(getIntent().getIntExtra("id",0)==0){
            fanyi.setVisibility(View.GONE);
        }else {
            ThreadPoolExecutorUtil.doTask(new Runnable() {

                @Override
                public void run() {
                    isCollent = JDBC.getInstance().queryTrallISCollent(getIntent().getIntExtra("id", 0));
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            fanyi.setText(isCollent ? "取消收藏" : "收藏");

                        }
                    });

                }
            });
        }
    }
}
