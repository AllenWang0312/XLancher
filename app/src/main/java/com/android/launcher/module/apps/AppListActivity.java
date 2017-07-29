package com.android.launcher.module.apps;//package com.android.launcher.module.apps;//package com.android.launcher.module;//package com.hzcaipu.www.xlancher;//package com.hzcaipu.www.xlancher;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.android.launcher.R;
import com.android.launcher.util.PackageUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AppListActivity extends AppCompatActivity {

    @BindView(R.id.rv_main) RecyclerView mRvMain;
    @BindView(R.id.toolbar) Toolbar mToolbar;
    private PackageManager mPackageManager;
    private List<ResolveInfo> mResolveInfos;
    private LauncherViewAdapter mLauncherViewAdapter;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_liast);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
       getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        mPackageManager = this.getPackageManager();
        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        mResolveInfos = mPackageManager.queryIntentActivities(mainIntent, 0);

        mLauncherViewAdapter = new LauncherViewAdapter(R.layout.app_icon, mResolveInfos, mPackageManager);
        mLauncherViewAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int i) {
                PackageUtil.startAPPFromPackageName(AppListActivity.this,mResolveInfos.get(i).activityInfo.packageName);
            }
        });
        mLauncherViewAdapter.setOnRecyclerViewItemLongClickListener(new BaseQuickAdapter.OnRecyclerViewItemLongClickListener() {
            @Override
            public boolean onItemLongClick(View view, int i) {
                getAppDetailSettingIntent(AppListActivity.this, mResolveInfos.get(i).activityInfo.packageName);
                return true;
            }
        });
        mRvMain.setLayoutManager(new GridLayoutManager(this, 5));

        mRvMain.setAdapter(mLauncherViewAdapter);

    }

    //   以下代码可以跳转到应用详情，可以通过应用详情跳转到权限界面(6.0系统测试可用)
    private void getAppDetailSettingIntent(Context context, String packageName) {
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            localIntent.setData(Uri.fromParts("package", packageName, null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            localIntent.setAction(Intent.ACTION_VIEW);
            localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
            localIntent.putExtra("com.android.settings.ApplicationPkgName", packageName);
        }
        startActivity(localIntent);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id==android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
