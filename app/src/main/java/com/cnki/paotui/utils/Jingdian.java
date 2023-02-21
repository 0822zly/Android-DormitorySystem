package com.cnki.paotui.utils;

import java.io.Serializable;

public class Jingdian implements Serializable {
    private String address;
    private String opentime;
    private String imageurl1;
    private String imageurl2;
    private String content;
    private String title;
    private String imageurl;
    private String score;
    private String url;

    public String getTitle() {
        return title;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOpentime() {
        return opentime;
    }

    public void setOpentime(String opentime) {
        this.opentime = opentime;
    }

    public String getImageurl1() {
        return imageurl1;
    }

    public void setImageurl1(String imageurl1) {
        this.imageurl1 = imageurl1;
    }

    public String getImageurl2() {
        return imageurl2;
    }

    public void setImageurl2(String imageurl2) {
        this.imageurl2 = imageurl2;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Jingdian(String title, String imageurl, String score, String url) {
        this.title = title;
        this.imageurl = imageurl;
        this.score = score;
        this.url = url;
    }
    public Jingdian() {
        this.title = title;
        this.imageurl = imageurl;
        this.score = score;
        this.url = url;
    }
}
