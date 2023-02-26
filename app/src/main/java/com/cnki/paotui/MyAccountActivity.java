package com.cnki.paotui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.cnki.paotui.databinding.ActivityMyAccountBinding;

public class MyAccountActivity extends BaseActivity {

    private com.cnki.paotui.databinding.ActivityMyAccountBinding viewBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewBinding = ActivityMyAccountBinding.inflate(getLayoutInflater());
          setContentView(viewBinding.getRoot());
        viewBinding.tvName.setText("账号："+App.user.username);
        viewBinding.tvPassword.setText("密码："+App.user.password);
        viewBinding.tvPassword1.setText("您的小学名字是："+App.user.password1);
    }
}