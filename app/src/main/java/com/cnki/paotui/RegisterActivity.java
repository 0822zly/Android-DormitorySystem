package com.cnki.paotui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.cnki.paotui.db.User;
import com.cnki.paotui.db.UserDao;
import com.cnki.paotui.utils.JDBC;
import com.gyf.immersionbar.ImmersionBar;

/**
 * Created by Android Studio.
 * User: cjr
 * Date: 2023/1/15
 * Time: 13:46
 * 此类的作用为：
 */
public class RegisterActivity extends BaseActivity{
    private EditText username;
    private EditText password;
    private EditText password1;
    private Button login;
    private ProgressBar loading;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regiter);
        getSupportActionBar().hide();
        ImmersionBar.with(this).transparentBar().init();
        username=findViewById(R.id.username);
        password=findViewById(R.id.password);
        password1=findViewById(R.id.password1);
        login=findViewById(R.id.login);
        loading=findViewById(R.id.loading);
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //loading.setVisibility(View.VISIBLE);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user=username.getText().toString();
                String pass=password.getText().toString();
                String pass1=password1.getText().toString();
                if(TextUtils.isEmpty(user)||TextUtils.isEmpty(pass)){
                    Toast.makeText(mContext, "用户名和密码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(user.length()<6||password.length()<6){
                    Toast.makeText(mContext, "用户名和密码必须大于6位", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(pass1)){
                    Toast.makeText(mContext, "密保问题不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
               new Thread(new Runnable() {
                   @Override
                   public void run() {
                       int i = JDBC.getInstance().insertUser(user, pass,pass1);
                       if(i>0) {
                           handler.post(new Runnable() {
                               @Override
                               public void run() {

                                   Toast.makeText(mContext, "注册成功", Toast.LENGTH_SHORT).show();
                                   finish();
                               }
                           });

                       }else {
                           handler.post(new Runnable() {
                               @Override
                               public void run() {
                                   Toast.makeText(mContext, "注册失败", Toast.LENGTH_SHORT).show();

                               }
                           });
                       }
                   }
               }){}.start();
            }
        });
    }
    Handler handler=new Handler(Looper.getMainLooper()){};
}
