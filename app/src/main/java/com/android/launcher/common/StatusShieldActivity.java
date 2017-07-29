package com.android.launcher.common;

import android.support.v7.app.AppCompatActivity;

import java.lang.reflect.Method;

/**
 * Created by wpc on 2017/7/12.
 */

public class StatusShieldActivity extends AppCompatActivity {

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        disableStatusBar();
        super.onWindowFocusChanged(hasFocus);
    }

    protected void disableStatusBar() {
        try{
            Object service =getSystemService("statusbar");
            Class<?> claz = Class.forName("android.app.StatusBarManager");
            Method expand =claz.getMethod("collapse");
            expand.invoke(service);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
