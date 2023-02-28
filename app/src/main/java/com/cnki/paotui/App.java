package com.cnki.paotui;

import android.app.Activity;
import android.app.Application;


import com.cnki.paotui.db.User;


/**
 * Created by Android Studio.
 * User: cjr
 * Date: 2023/1/15
 * Time: 19:42
 * 此类的作用为：
 */
public class App extends Application {
    public static App mIntance;
    @Override
    public void onCreate() {
        super.onCreate();
        mIntance=this;


    }
    public static User user;
}
