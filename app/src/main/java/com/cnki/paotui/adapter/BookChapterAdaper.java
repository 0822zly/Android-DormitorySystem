package com.cnki.paotui.adapter;

import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.cnki.paotui.R;
import com.cnki.paotui.bean.Book;
import com.cnki.paotui.bookDetailsActivity;

import java.util.List;

public class BookChapterAdaper extends BaseQuickAdapter<bookDetailsActivity.Chapter, BaseViewHolder> {
    public BookChapterAdaper(int layoutResId) {
        super(layoutResId);
    }

    public BookChapterAdaper(int layoutResId, @Nullable List<bookDetailsActivity.Chapter> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder holder, bookDetailsActivity.Chapter item) {
        holder.setText(R.id.tv_chapter_title, item.title);

    }
}
