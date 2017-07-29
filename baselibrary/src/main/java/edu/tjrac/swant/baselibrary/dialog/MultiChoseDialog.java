package edu.tjrac.swant.baselibrary.dialog;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;

/**
 * Created by wpc on 2017/6/19.
 */
@SuppressLint({"NewApi", "ValidFragment"})
public  class MultiChoseDialog extends DialogFragment {
    Context context;
    String title;
    String[] items;

    public boolean[] getSelected() {
        return selected;
    }

    public void setSelected(boolean[] selected) {
        this.selected = selected;
    }

    boolean[] selected;
    DialogInterface.OnClickListener positive;

    public MultiChoseDialog(Context context, String title, String[] items, @Nullable boolean[] selected) {
        this.context = context;
        this.title = title;
        this.items = items;
        if (selected == null) {
            this.selected = new boolean[items.length];
        } else {
            this.selected = selected;
        }
    }

    public DialogInterface.OnClickListener getPositive() {
        return positive;
    }

    public void setPositive(DialogInterface.OnClickListener positive) {
        this.positive = positive;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMultiChoiceItems(items, selected, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                selected[which]=isChecked;
            }
        });
        builder.setPositiveButton("确定", positive);
        return builder.create();
    }
}