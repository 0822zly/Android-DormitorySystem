package com.cnki.paotui.db;

import java.io.Serializable;

public class MyTravell implements Serializable {
    private int id;
    private String userid;

    private String starttime;
    private String fromlocation;
    private String tolocation;
    private String hotal;

    private String fromlat;
    private String fromlon;
    private String tolat;
    private String tolon;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getFromlocation() {
        return fromlocation;
    }

    public void setFromlocation(String fromlocation) {
        this.fromlocation = fromlocation;
    }

    public String getTolocation() {
        return tolocation;
    }

    public void setTolocation(String tolocation) {
        this.tolocation = tolocation;
    }

    public String getHotal() {
        return hotal;
    }

    public void setHotal(String hotal) {
        this.hotal = hotal;
    }

    public String getFromlat() {
        return fromlat;
    }

    public void setFromlat(String fromlat) {
        this.fromlat = fromlat;
    }

    public String getFromlon() {
        return fromlon;
    }

    public void setFromlon(String fromlon) {
        this.fromlon = fromlon;
    }

    public String getTolat() {
        return tolat;
    }

    public void setTolat(String tolat) {
        this.tolat = tolat;
    }

    public String getTolon() {
        return tolon;
    }

    public void setTolon(String tolon) {
        this.tolon = tolon;
    }
}
