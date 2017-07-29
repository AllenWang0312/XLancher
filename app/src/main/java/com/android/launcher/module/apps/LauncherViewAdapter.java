package com.android.launcher.module.apps;

import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

import com.android.launcher.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by wpc on 2017/7/17.
 */

public  class LauncherViewAdapter extends BaseQuickAdapter<ResolveInfo> {

   PackageManager mPackageManager;
    public LauncherViewAdapter(int layoutResId, List<ResolveInfo> data, PackageManager mPackageManager) {
        super(layoutResId, data);
        this.mPackageManager=  mPackageManager;
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, ResolveInfo resolveInfo) {
        baseViewHolder.setImageDrawable(R.id.iv_apps, resolveInfo.activityInfo.loadIcon(mPackageManager));
        baseViewHolder.setText(R.id.tv_apps, resolveInfo.loadLabel(mPackageManager));
    }
}