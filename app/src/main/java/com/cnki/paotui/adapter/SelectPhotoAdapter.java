package com.cnki.paotui.adapter;


import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.cnki.paotui.App;
import com.cnki.paotui.R;
import com.huantansheng.easyphotos.models.album.entity.Photo;

import java.util.List;

public class SelectPhotoAdapter extends BaseQuickAdapter<Photo, BaseViewHolder> {
    public SelectPhotoAdapter(int layoutResId) {
        super(layoutResId);
    }

    public SelectPhotoAdapter(int layoutResId, @Nullable List<Photo> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder baseViewHolder, Photo photo) {
        ImageView imageView=baseViewHolder.getView(R.id.item_image);
       // Glide.with(App.mIntance).
       //
        if(TextUtils.isEmpty(photo.name)){
            imageView.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_baseline_add_24_big));
            imageView.setBackgroundResource(R.drawable.shape_16_zi_xuxian);
        }else {
            Glide.with(getContext()).load(photo.uri).into(imageView);
            imageView.setBackgroundResource(R.color.transparent);

        }

    }

}
