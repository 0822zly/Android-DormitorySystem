package com.cnki.paotui.adapter;

import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.cnki.paotui.R;
import com.cnki.paotui.bean.Book;

import java.util.List;

public class BookAdaper extends BaseQuickAdapter<Book, BaseViewHolder> {
    public BookAdaper(int layoutResId) {
        super(layoutResId);
    }

    public BookAdaper(int layoutResId, @Nullable List<Book> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder holder, Book item) {
        holder.setText(R.id.tv_title, item.title);
        holder.setText(R.id.tv_auther, item.auther);
        holder.setText(R.id.tv_info, item.content);
        ImageView iamge=holder.getView(R.id.item_image);
                Glide.with(getContext()).load(item.cover).into(iamge);
    }
}