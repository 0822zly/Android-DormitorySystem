package com.cnki.paotui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import androidx.annotation.Nullable;

import com.cnki.paotui.db.User;
import com.cnki.paotui.ui.login.LoginActivity;
import com.cnki.paotui.utils.JDBC;
import com.cnki.paotui.utils.SPUtil;
import com.cnki.paotui.utils.ThreadPoolExecutorUtil;
import com.gyf.immersionbar.ImmersionBar;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;

public class WelComeActivity  extends BaseActivity{
    private boolean oPen;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        getSupportActionBar().hide();
        ImmersionBar.with(this).transparentBar().init();

        int arr[]={R.mipmap.banner1,R.mipmap.banner2};
        ThreadPoolExecutorUtil.doTask(new Runnable() {



            @Override
            public void run() {
                oPen = JDBC.getInstance().isOPen();
            }
        });
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                   if(oPen) {
                       if (SPUtil.getInstance().getBoolean(Ikeys.ISNOTFRISTCOMEIN)) {
                           Intent intent = new Intent(mContext, LoginActivity.class);
                           mContext.startActivity(intent);
                       } else {
                           Intent intent = new Intent(mContext, LoginActivity.class);
                           mContext.startActivity(intent);
                       }
                       SPUtil.getInstance().setValue(Ikeys.ISNOTFRISTCOMEIN, true);
                       finish();
                   }else {
                       finish();
                   }
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


    }
    Handler handler=new Handler(Looper.getMainLooper());

    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                layout.setPrimaryColorsId(R.color.white, android.R.color.black);//全局设置主题颜色
                return new ClassicsHeader(context);//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                return new ClassicsFooter(context).setDrawableSize(20);
            }
        });
    }
}
