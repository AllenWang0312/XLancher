package com.android.launcher.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.util.Log;

/**
 * Created by wpc on 2017/7/17.
 */

public class PackageUtil {
    public static final String TAG = "PackageUtil";
    public static void startAPPFromPackageName(Context context, String packagename) {
        Intent intent = isexit(context, packagename);
        if (intent == null) {
            Log.i(TAG, "APP not found!");
        }
        context.startActivity(intent);
    }
    public static Intent isexit(Context context, String pk_name) {
        PackageManager packageManager = context.getPackageManager();
        Intent it = packageManager.getLaunchIntentForPackage(pk_name);
        return it;
    }
}
