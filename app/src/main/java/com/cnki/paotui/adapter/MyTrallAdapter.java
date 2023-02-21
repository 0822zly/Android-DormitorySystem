package com.cnki.paotui.adapter;


import android.content.Intent;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.cnki.paotui.AddTralltion;
import com.cnki.paotui.R;
import com.cnki.paotui.WebActivity1;
import com.cnki.paotui.db.MyTravell;
import com.cnki.paotui.db.Travell;

import java.util.List;

public class MyTrallAdapter extends BaseQuickAdapter<MyTravell, BaseViewHolder> {
    public MyTrallAdapter(int layoutResId) {
        super(layoutResId);
    }

    public MyTrallAdapter(int layoutResId, @Nullable List<MyTravell> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder baseViewHolder, MyTravell photo) {
        baseViewHolder.setText(R.id.tv_from,"出发地:"+photo.getFromlocation());
        baseViewHolder.setText(R.id.tv_to,"目的地:"+photo.getTolocation());
        baseViewHolder.setText(R.id.tv_time,"出发日期:"+photo.getStarttime());

        baseViewHolder.getView(R.id.item_layout).setOnClickListener(v -> {
        Intent intent = new Intent(getContext(), AddTralltion.class);
            intent.putExtra(MyTravell.class.getSimpleName(),photo);
            getContext().startActivity(intent);
        });
    }

}
