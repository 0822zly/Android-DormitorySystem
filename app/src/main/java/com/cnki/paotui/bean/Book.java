package com.cnki.paotui.bean;

import com.cnki.paotui.InterBook;

public class Book implements InterBook {
   public String title;
   public String id;
   public String url;
   public String cover;
   public String type;
   public String content;
   public String auther;
   public String time;
   public String newchapter;//
    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public String getColver() {
        return cover;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public String getContent() {
        return content;
    }

    @Override
    public String getAuther() {
        return auther;
    }

    @Override
    public String getTime() {
        return time;
    }
}
