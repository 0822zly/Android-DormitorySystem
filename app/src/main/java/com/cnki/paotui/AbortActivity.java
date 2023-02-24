package com.cnki.paotui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


/**
 * 关于软件
 */

public class AbortActivity extends BaseActivity  {

    private Button btn_check;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abort_app);
        initView();

    }


    protected void initView() {
        btn_check = findViewById(R.id.btn_check);
        getSupportActionBar().setTitle("关于软件");
        btn_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "已是最新版本", Toast.LENGTH_SHORT).show();
            }
        });
    }









}
