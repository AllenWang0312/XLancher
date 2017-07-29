package com.android.launcher.module.usb_bus;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.android.launcher.R;
import com.android.launcher.module.main.MainActivity;

/**
 * Created by wpc on 2017/7/19.
 */

public class UsbBus extends Service {

    public static final String TAG = "UsbBus";

    private UsbBinder mBinder = new UsbBinder();



    @Override
    public void onCreate() {
        super.onCreate();

        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                notificationIntent, 0);

        NotificationCompat.Builder mbuilder = new NotificationCompat.Builder(this);
        mbuilder.setContentTitle("Usb服务").setContentText("检测到仪器连接")
                .setWhen(System.currentTimeMillis()).setPriority(Notification.PRIORITY_DEFAULT)
                .setOngoing(false).setDefaults(Notification.DEFAULT_VIBRATE)
                .setSmallIcon(R.drawable.ic_device_hub_grey_400_24dp)//设置通知小ICON
                .setContentIntent(pendingIntent);

        Notification notification = mbuilder.build();
        startForeground(1, notification);
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }


    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    public class UsbBinder extends Binder {

        public void logState() {
            Log.i("usb binder ", "");
        }

    }
}
