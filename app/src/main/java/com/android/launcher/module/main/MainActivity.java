package com.android.launcher.module.main;//package com.android.launcher.module.main;//package com.android.launcher.module;//package com.hzcaipu.www.xlancher;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.widget.Button;

import com.android.launcher.BuildConfig;
import com.android.launcher.R;
import com.android.launcher.common.BaseActivity;
import com.android.launcher.common.adapter.ViewPagerAdapter;
import com.android.launcher.module.apps.AppListActivity;
import com.android.launcher.module.main.fragments.TestFragment;
import com.android.launcher.module.main.fragments.file.FileFragment;
import com.android.launcher.module.serial.ConsoleActivity;
import com.android.launcher.module.spi.Spi_Activity;
import com.android.launcher.module.usb_bus.UsbBus;
import com.android.launcher.view.VerticalViewPager;

import butterknife.BindView;
import butterknife.ButterKnife;
import q.rorbin.verticaltablayout.VerticalTabLayout;

/**
 * Created by wpc on 2017/7/12.
 */

public class MainActivity extends BaseActivity {

    @BindView(R.id.vtl_main) VerticalTabLayout mVtlMain;
    @BindView(R.id.vvp_main) VerticalViewPager mVvpMain;
    @BindView(R.id.bt_apps) Button bt_apps;
    @BindView(R.id.bt_serial)Button bt_serial;
    @BindView(R.id.bt_spi)Button bt_spi;
    private ViewPagerAdapter mViewPagerAdapter;
    NotificationManager mNotificationManager;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(BuildConfig.DEBUG){
            showBackNotification();
        }


        //启动usb service
        Intent startIntent = new Intent(this, UsbBus.class);
        startService(startIntent);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        mViewPagerAdapter.addFragment(new TestFragment(), "测量");
        mViewPagerAdapter.addFragment(new TestFragment(), "数据");
        mViewPagerAdapter.addFragment(new FileFragment(), "文件");
        mViewPagerAdapter.addFragment(new TestFragment(), "设置");
        mVvpMain.setAdapter(mViewPagerAdapter);
        mVtlMain.setupWithViewPager(mVvpMain);

        bt_apps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AppListActivity.class));
            }
        });
        bt_serial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ConsoleActivity.class));
            }
        });
        bt_spi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Spi_Activity.class));
            }
        });
    }


    private void showBackNotification() {
        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, getIntent(), 0);

        mBuilder.setContentTitle("App Launcher")//设置通知栏标题
                .setContentText("点击返回") //<span style="font-family: Arial;">/设置通知栏显示内容</span>
//    .setContentIntent(getDefalutIntent(Notification.FLAG_AUTO_CANCEL)) //设置通知栏点击意图
//  .setNumber(number) //设置通知集合的数量
//                .setTicker("测试通知来啦") //通知首次出现在通知栏，带上升动画效果的
                .setWhen(System.currentTimeMillis())//通知产生的时间，会在通知信息里显示，一般是系统获取到的时间
                .setPriority(Notification.PRIORITY_DEFAULT) //设置该通知优先级
//  .setAutoCancel(true)//设置这个标志当用户单击面板就可以让通知将自动取消
                .setOngoing(false)//ture，设置他为一个正在进行的通知。他们通常是用来表示一个后台任务,用户积极参与(如播放音乐)或以某种方式正在等待,因此占用设备(如一个文件下载,同步操作,主动网络连接)
                .setDefaults(Notification.DEFAULT_VIBRATE)//向通知添加声音、闪灯和振动效果的最简单、最一致的方式是使用当前的用户默认设置，使用defaults属性，可以组合
                //Notification.DEFAULT_ALL  Notification.DEFAULT_SOUND 添加声音 // requires VIBRATE permission
                .setSmallIcon(R.drawable.ic_exit_to_app_grey_400_24dp)//设置通知小ICON
                .setContentIntent(pendingIntent);

        Notification mNotification = mBuilder.build();
//        notification.flags = Notification.FLAG_AUTO_CANCEL;
        mNotification.icon = R.mipmap.ic_launcher;
        //在通知栏上点击此通知后自动清除此通知
        mNotification.flags = Notification.FLAG_ONGOING_EVENT;//FLAG_ONGOING_EVENT 在顶部常驻，可以调用下面的清除方法去除  FLAG_AUTO_CANCEL  点击和清理可以去调
        //设置显示通知时的默认的发声、震动、Light效果
        mNotification.defaults = Notification.DEFAULT_VIBRATE;
        //设置发出消息的内容
        mNotification.tickerText = "通知来了";
        //设置发出通知的时间
        mNotification.when=System.currentTimeMillis();
//		mNotification.flags = Notification.FLAG_AUTO_CANCEL; //在通知栏上点击此通知后自动清除此通知
//		mNotification.setLatestEventInfo(this, "常驻测试", "使用cancel()方法才可以把我去掉哦", null); //设置详细的信息  ,这个方法现在已经不用了
        mNotificationManager.notify(100, mNotification);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Intent stopIntent = new Intent(this, UsbBus.class);
        stopService(stopIntent);
    }
}
