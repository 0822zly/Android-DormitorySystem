package com.cnki.paotui.adapter;

import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.cnki.paotui.R;
import com.cnki.paotui.bean.Book;

import java.util.List;

public class BookOrderAdaper extends BaseQuickAdapter<Book, BaseViewHolder> {
    public BookOrderAdaper(int layoutResId) {
        super(layoutResId);
    }

    public BookOrderAdaper(int layoutResId, @Nullable List<Book> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder holder, Book item) {
        Book book=item;
        holder.setText(R.id.tv_title, item.title);
        holder.setText(R.id.tv_auther, item.auther);
        if(TextUtils.isEmpty(item.newchapter)){
            holder.setGone(R.id.tv_newc,true);
        }else {
            holder.setText(R.id.tv_newc, item.newchapter);
        }
        holder.setText(R.id.tv_time, item.time);

    }

}
