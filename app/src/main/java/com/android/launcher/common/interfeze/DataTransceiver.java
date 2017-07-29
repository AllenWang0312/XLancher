package com.android.launcher.common.interfeze;

import android.content.Context;
import android.graphics.Bitmap;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbInterface;
import android.support.annotation.Nullable;

import java.util.ArrayList;

/**
 * Created by wpc on 2017/7/18.
 */

public abstract class DataTransceiver {
    byte[] Receiver_bytes;  //接收信息字节
    int num_onceArrive = 4000;
    //图片数据
    public short length = 640;
    public short height = 480;
    public byte content = 1;
    public Bitmap bitmap;
    final short times = (short) ((2 * length * height / content) / num_onceArrive);

    byte[] part1 = new byte[(length * height / content) * 2];

    //        private int[] GRAY = new int[(length * height / content)];//源数据 10位
    short[] gray = new short[(length * height / content)];//RGB支持的8位
    public int[] arr = new int[(length - 1) * (height - 1)];//计算过后的图片数组


    public abstract void setConnection(UsbDeviceConnection con, android.hardware.usb.UsbManager manager, UsbDevice Device,
                                       UsbInterface Interface,
                                       UsbEndpoint Out, UsbEndpoint In);

    public abstract void sendBatteryRequest(Context context);

    public abstract byte[] sendTakeConfigRequest();

    public abstract void sendNotifyUserRequest(boolean b);

    public abstract boolean saveData(int test_mod, @Nullable String tips, @Nullable Boolean saveImg, @Nullable ArrayList<Result> mDates);

    public abstract boolean sendTakePhotoRequest(byte b, int anInt);

    public abstract int LoadDate(int test_mod, int i);

    public abstract ArrayList<Result> checkData();
}
