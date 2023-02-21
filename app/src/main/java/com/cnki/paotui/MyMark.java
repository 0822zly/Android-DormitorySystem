package com.cnki.paotui;

import java.io.Serializable;

public class MyMark implements Serializable {
    private String Lat;
    private String Lng;
    private String title;
    private String content;
    private String cityCode;

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getLat() {
        return Lat;
    }

    public MyMark(String lat, String lng, String title, String content) {
        Lat = lat;
        Lng = lng;
        this.title = title;
        this.content = content;
    }
    public MyMark() {

    }
    public void setLat(String lat) {
        Lat = lat;
    }

    public String getLng() {
        return Lng;
    }

    public void setLng(String lng) {
        Lng = lng;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
