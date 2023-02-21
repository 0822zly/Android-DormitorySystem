package com.cnki.paotui;

import android.app.Activity;
import android.app.Application;

import com.amap.api.location.AMapLocationClient;
import com.amap.api.maps.MapsInitializer;
import com.amap.api.services.core.ServiceSettings;
import com.cnki.paotui.db.User;
import com.cnki.paotui.utils.SPUtil;
import com.kongzue.dialogx.DialogX;

import java.util.ArrayList;
import java.util.List;

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
        AMapLocationClient.updatePrivacyAgree(this, true);
        AMapLocationClient.updatePrivacyShow(this, true, true);
        MapsInitializer.updatePrivacyShow(this,true,true);
        MapsInitializer.updatePrivacyAgree(this,true);
        ServiceSettings.updatePrivacyShow(this,true,true);
        ServiceSettings.updatePrivacyAgree(this,true);

    }
    public static User user;
}
