package com.cnki.paotui.adapter;


import android.content.Intent;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.cnki.paotui.App;
import com.cnki.paotui.R;
import com.cnki.paotui.WebActivity1;
import com.cnki.paotui.db.Travell;

import java.util.List;

public class LvYouAdapter extends BaseQuickAdapter<Travell, BaseViewHolder> {
    public LvYouAdapter(int layoutResId) {
        super(layoutResId);
    }

    public LvYouAdapter(int layoutResId, @Nullable List<Travell> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder baseViewHolder, Travell photo) {
        ImageView imageView = baseViewHolder.getView(R.id.item_image);
        baseViewHolder.setText(R.id.item_title,photo.getTitle());
        baseViewHolder.setText(R.id.item_content,photo.getContent());
        baseViewHolder.setText(R.id.tv_image_num,photo.getPictureNumber());
        baseViewHolder.setText(R.id.tv_comment_num,photo.getCommentNumber());
        baseViewHolder.setText(R.id.tv_zan_num,photo.getViewNumber());
        Glide.with(imageView.getContext()).load(photo.getImageUrl()).into(imageView);
        baseViewHolder.getView(R.id.item_layout).setOnClickListener(v -> {
            if(App.user!=null) {
                Intent intent = new Intent(getContext(), WebActivity1.class);
                intent.putExtra("url", photo.getJumpUrl());
                intent.putExtra("title", photo.getTitle());
                intent.putExtra("id", photo.getId());
                getContext().startActivity(intent);
            }else {
                Toast.makeText(getContext(), "请先登入", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
