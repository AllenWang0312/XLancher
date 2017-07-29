package com.android.launcher.common;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.hardware.usb.UsbConstants;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbInterface;
import android.hardware.usb.UsbManager;
import android.util.Log;

import com.android.launcher.util.T;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Timer;



/**
 * Created by wpc on 2016/9/19.
 */
public class USBManager {

    //-1设备不支持usbhost，
    // 0usbmanager可用
    // 1derive可用
    // 2interface可用
    // 3endpoint可用/可以发送拍照请求
    private static Integer state = -1;

    public boolean printable = false;

    //    static final String ACTION_USB_PERMISSION = "com.android.example.USB_PERMISSION";// 权限
    private static USBManager instance;
    private final String tag = "USBManager";

    public Bitmap bitmap;

    //usb相关
    Activity mContext;
    private static UsbManager mUsbManager;
    UsbDevice mUsbDevice;
    private UsbInterface mInterface;
    private UsbDeviceConnection mUsbDeviceConnection;

    private UsbEndpoint epBulkOut, epControl, epIntEndpointOut, epIntEndpointIn, epBulkIn;
    Timer timer;
    public boolean isBussy = false;

    public static USBManager getInstance() {
        return instance;
    }

    private USBManager(Activity context) {
        mContext = context;
    }

    public static USBManager getInstance(Activity context) {
        if (instance == null) {
            instance = new USBManager(context);
        }
        if (mUsbManager == null) {
            mUsbManager = (UsbManager) context.getSystemService(Context.USB_SERVICE);
        }
        if (mUsbManager == null) {
            T.showInTop(context, "usb不可用");
        } else {
            state = 0;
        }
        return instance;
    }

    public Integer getState() {
        return state;
    }

    boolean foundPoint = false;

    public boolean init() {
        if (findDevice(1155, 22336)) {
            if (findInterface(10, 0, 0)) {
                if (findPoint()) {
                    printable = true;
                    return true;
                } else {
                    T.showInTop(mContext, "没有发现端点");
                }
            } else {
                T.showInTop(mContext, "没有发现接口");
            }
        } else {
            T.showInTop(mContext, "没有发现设备");
        }

        return false;
    }

    public boolean hasPoint() {
        return foundPoint;
    }

    public boolean findDevice(int Vendorid, int productid) {
        if (mUsbManager != null) {
            final HashMap<String, UsbDevice> mdrivers = mUsbManager.getDeviceList();
            Iterator<UsbDevice> deviceIterator = mdrivers.values().iterator();
            while (deviceIterator.hasNext()) {
                UsbDevice device = deviceIterator.next();
                if (device.getVendorId() == Vendorid && device.getProductId() == productid) {
                    this.mUsbDevice = device;
                    state = 1;
                    return true;
                }
            }
        }
        return false;
    }

    public boolean findInterface(int interfaceClass, int interfaceSubclass, int interfaceProtocol) {
        if (mUsbDevice != null) {
            int cont = mUsbDevice.getInterfaceCount();
            for (int i = 0; i < cont; i++) {
                UsbInterface Intf = mUsbDevice.getInterface(i);
                if (Intf.getInterfaceClass() == interfaceClass && Intf.getInterfaceSubclass() == interfaceSubclass && Intf.getInterfaceProtocol() == interfaceProtocol) {
                    mInterface = Intf;
                    Log.i("findInterface", "true");
                    state = 2;
                    return true;
                }
            }
        }
        return false;
    }

    public boolean findPoint() {
        if (mInterface != null) {
            for (int j = 0; j < mInterface.getEndpointCount(); j++) {
                UsbEndpoint ep = mInterface.getEndpoint(j);
                if (ep.getType() == UsbConstants.USB_ENDPOINT_XFER_BULK) {
//                    USB_ENDPOINT_XFER_BULK
//                            批量端点类型
                    if (ep.getDirection() == UsbConstants.USB_DIR_OUT) {
                        Log.i(tag, "Find the BulkEndpointOut,index:" + j + ",使用断点号:" + ep.getEndpointNumber());
                        epBulkOut = ep;
                        Log.i("epBulkOut", epBulkOut.toString());
                    } else {
                        epBulkIn = ep;
                        Log.i(tag, "Find the BulkEndpointIn,index:" + j + ",使用断点号:" + ep.getEndpointNumber());
                    }

                    if (epBulkOut != null && epBulkIn != null) {
                        state = 3;
                        Log.i("findPoint", "true");
//                        if (TransceiverInstance == null) {
//                            TransceiverInstance = new USBDataTransceiver();
//                        }
                        return true;
                    } else {
                        state = 0;
                    }
                }
                if (ep.getType() == UsbConstants.USB_ENDPOINT_XFER_CONTROL) {
//                    USB_ENDPOINT_XFER_CONTROL
//                    控制端点类型（端点零）
                    epControl = ep;
                    Log.i(tag, "find the ControlEndPoint:" + "index:" + j
                            + "," + epControl.getEndpointNumber());
                }
                if (ep.getType() == UsbConstants.USB_ENDPOINT_XFER_INT) {
                    if (ep.getDirection() == UsbConstants.USB_DIR_OUT) {
                        epIntEndpointOut = ep;
                        Log.i(tag, "find the InterruptEndpointOut:"
                                + "index:" + j + ","
                                + epIntEndpointOut.getEndpointNumber());
                    }
                    if (ep.getDirection() == UsbConstants.USB_DIR_IN) {
//                        USB_ENDPOINT_XFER_INT
//                                中断端点类型
                        epIntEndpointIn = ep;
                        Log.i(tag, "find the InterruptEndpointIn:"
                                + "index:" + j + ","
                                + epIntEndpointIn.getEndpointNumber());
                    }
                }
            }
        }
        return false;
    }

//    public void registerReceiver(Context context) {
//        myUSBReceiver = new usbBroadcastReceiver();
//        IntentFilter intentFilter = new IntentFilter();
////        intentFilter.setPriority(800);
//        intentFilter.addAction("android.hardware.usb.action.USB_STATE");
//        intentFilter.addAction("android.hardware.usb.action.USB_DEVICE_ATTACHED");
//        intentFilter.addAction("android.hardware.usb.action.USB_DEVICE_DETACHED");
//        context.registerReceiver(myUSBReceiver, intentFilter);
//    }

}
