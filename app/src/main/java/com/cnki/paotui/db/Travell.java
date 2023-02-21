package com.cnki.paotui.db;

import java.io.Serializable;

public class Travell implements Serializable {
    private int id;
    private String  title;
    private author  author;
    private String  content;

    private String  pictureNumber;//图片数量
    private String  commentNumber;//评论数量
    private String  viewNumber;//观看数量
    private String  imageUrl;
    private String  jumpUrl;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public com.cnki.paotui.db.author getAuthor() {
        return author;
    }

    public void setAuthor(com.cnki.paotui.db.author author) {
        this.author = author;
    }



    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPictureNumber() {
        return pictureNumber;
    }

    public void setPictureNumber(String pictureNumber) {
        this.pictureNumber = pictureNumber;
    }

    public String getCommentNumber() {
        return commentNumber;
    }

    public void setCommentNumber(String commentNumber) {
        this.commentNumber = commentNumber;
    }

    public String getViewNumber() {
        return viewNumber;
    }

    public void setViewNumber(String viewNumber) {
        this.viewNumber = viewNumber;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getJumpUrl() {
        return jumpUrl;
    }

    public void setJumpUrl(String jumpUrl) {
        this.jumpUrl = jumpUrl;
    }
}
