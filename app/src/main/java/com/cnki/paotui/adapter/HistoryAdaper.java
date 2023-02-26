package com.cnki.paotui.adapter;

import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.cnki.paotui.R;
import com.cnki.paotui.bean.Book;

import java.util.List;

public class HistoryAdaper extends BaseQuickAdapter<String, BaseViewHolder> {
    public HistoryAdaper(int layoutResId) {
        super(layoutResId);
    }

    public HistoryAdaper(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder holder, String item) {
        holder.setText(R.id.tv_1, item);

    }
}
