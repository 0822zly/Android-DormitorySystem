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
    }
    Handler handler=new Handler(Looper.getMainLooper());

}
