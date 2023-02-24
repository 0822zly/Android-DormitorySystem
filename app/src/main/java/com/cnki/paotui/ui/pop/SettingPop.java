package com.cnki.paotui.ui.pop;

import static android.view.Gravity.CENTER;

import android.content.Context;

import com.cnki.paotui.R;

import razerdp.basepopup.BasePopupWindow;

public class SettingPop extends BasePopupWindow {
    public SettingPop(Context context) {
        super(context);
        setContentView(R.layout.pop_set);
        setPopupGravity(CENTER);
    }
}
