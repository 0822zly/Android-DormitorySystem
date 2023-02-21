package com.cnki.paotui.adapter;


import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.cnki.paotui.AddTralltion;
import com.cnki.paotui.JingdianActivity;
import com.cnki.paotui.R;
import com.cnki.paotui.WebActivity1;
import com.cnki.paotui.db.MyTravell;
import com.cnki.paotui.utils.Jingdian;

import java.util.List;

public class JingdianAdapter extends BaseQuickAdapter<Jingdian, BaseViewHolder> {
    public JingdianAdapter(int layoutResId) {
        super(layoutResId);
    }

    public JingdianAdapter(int layoutResId, @Nullable List<Jingdian> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder baseViewHolder, Jingdian photo) {
        baseViewHolder.setText(R.id.item_jingdian_title,photo.getTitle());
        baseViewHolder.setText(R.id.item_jingdian_score,photo.getScore()+"分");

        ImageView imageView=baseViewHolder.getView(R.id.item_jingdian_icon);
        baseViewHolder.getView(R.id.rootview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), JingdianActivity.class);
                intent.putExtra("jingdian",photo);
                getContext().startActivity(intent);
            }
        });
        Glide.with(getContext()).load(photo.getImageurl()).into(imageView);    }

}
