package com.cnki.paotui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.cnki.paotui.databinding.ActivityJingdianBinding;
import com.cnki.paotui.utils.Jingdian;

public class JingdianActivity extends BaseActivity {
    Jingdian jingdian;
    private com.cnki.paotui.databinding.ActivityJingdianBinding viewBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewBinding = ActivityJingdianBinding.inflate(getLayoutInflater());
          setContentView(viewBinding.getRoot());
        jingdian= (Jingdian) getIntent().getSerializableExtra("jingdian");
        Glide.with(this).load(jingdian.getImageurl()).into(viewBinding.jingdianactivityHeader);
        Glide.with(this).load(jingdian.getImageurl1()).into(viewBinding.desIamge1);
        Glide.with(this).load(jingdian.getImageurl2()).into(viewBinding.desIamge2);
        viewBinding.tvTitle.setText(jingdian.getTitle());
        viewBinding.tvAddress.setText(jingdian.getAddress());
        viewBinding.tvScore.setText(jingdian.getScore()+"分");
        viewBinding.tvTime.setText(jingdian.getOpentime());
        viewBinding.tvDes.setText(jingdian.getContent());
    }
}