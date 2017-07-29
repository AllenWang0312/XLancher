package edu.tjrac.swant.baselibrary.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by wpc on 2017/6/19.
 */

public class ComDialog {
    public static AlertDialog getMessageDialog(Context context, String title, String message, DialogInterface.OnClickListener postive) {
        return new AlertDialog.Builder(context).setTitle(title).setMessage(message).setPositiveButton("确定", postive).setNegativeButton("取消", null).create();
    }
    public static AlertDialog getMessageDialog(Context context, String title, int message, DialogInterface.OnClickListener postive) {
        return new AlertDialog.Builder(context).setTitle(title).setMessage(context.getString(message)).setPositiveButton("确定", postive).setNegativeButton("取消", null).create();
    }
}
