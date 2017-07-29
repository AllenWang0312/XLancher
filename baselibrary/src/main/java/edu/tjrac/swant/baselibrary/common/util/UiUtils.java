package edu.tjrac.swant.baselibrary.common.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import edu.tjrac.swant.baselibrary.R;

/**
 * Created by wpc on 2016/9/10.
 */
public class UiUtils {

    //切换输入法显示状态
    public static void hiddenSoftInput(Context context) {
        //得到InputMethodManager的实例
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            //关闭软键盘，开启方法相同，这个方法是切换开启与关闭状态的
            imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }


    public static int getToolbarHeight(Context context) {
        final TypedArray styledAttributes = context.getTheme().obtainStyledAttributes(
                new int[]{R.attr.actionBarSize});
        int toolbarHeight = (int) styledAttributes.getDimension(0, 0);
        styledAttributes.recycle();

        return toolbarHeight;
    }

//    public static int getTabsHeight(Context context) {
//        return (int) context.getResources().getDimension(R.dimen.tabsHeight);
//    }

    public static int Dp2Px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    public static int Px2Dp(Context context, float px) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }

    public static void addLeftDrawTextViewToLinearLayout(Context context, LinearLayout ll, String str, @Nullable Integer left_res) {
        TextView t = new TextView(context);
        t.setBackground(context.getResources().getDrawable(R.drawable.black_stroke));
        t.setGravity(Gravity.CENTER);
        t.setLayoutParams(new LinearLayout.LayoutParams(Dp2Px(context, 64), ViewGroup.LayoutParams.MATCH_PARENT));
        t.setText(str);
        if (left_res != null) {
            setDrawableLeft(context, t, left_res, Dp2Px(context, 24));
        }
        ll.addView(t);
    }

    public static void setDrawableLeft(Context context, TextView attention, int drawableId) {
        Drawable drawable = context.getResources().getDrawable(drawableId);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        attention.setCompoundDrawables(drawable, null, null, null);
    }

    public static void setDrawableRight(Context context, TextView attention, int drawableId) {
        Drawable drawable = context.getResources().getDrawable(drawableId);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        attention.setCompoundDrawables(null, null, drawable, null);
    }

    public static void setDrawableLeft(Context context, TextView attention, int drawableId, int width) {
        Drawable drawable = context.getResources().getDrawable(drawableId);
        drawable.setBounds(0, 0, width, width);
        attention.setCompoundDrawables(drawable, null, null, null);
    }

    public static void addTextViewToLinearLayout(Context context, LinearLayout ll, String str, @Nullable Integer color) {
        TextView t = new TextView(context);
        t.setBackground(context.getResources().getDrawable(R.drawable.black_stroke));
        t.setLayoutParams(new LinearLayout.LayoutParams(Dp2Px(context, 64), ViewGroup.LayoutParams.MATCH_PARENT));
        t.setGravity(Gravity.CENTER);
        t.setText(str);

        if (color != null) {
            t.setTextColor(color);
        }
        ll.addView(t);
    }


    public static void addImageViewToLinearLayout(Context context, LinearLayout ll, Integer res) {
        int width = Dp2Px(context, 12);
        ImageView i = new ImageView(context);
        i.setScaleType(ImageView.ScaleType.CENTER_CROP);
        i.setLayoutParams(new LinearLayout.LayoutParams(width, width));
        if (res != null) {
            Glide.with(context).load(res).into(i);
//            i.setImageResource(res);
        }
        ll.addView(i);
    }

    public static void setViewPercentSize(View v, Activity context, @Nullable Float width_percent, float height_percent) {
        View decor = context.getWindow().getDecorView();
        int height = decor.getHeight();
        int width = decor.getWidth();
        v.setLayoutParams(new LinearLayout.LayoutParams(
                width_percent == null ?
                        ViewGroup.LayoutParams.MATCH_PARENT :
                        (int) (width_percent * width),
                (int) (height_percent * height)));
    }

//    public static void setViewPercentWidth(View v, Activity context, float percent) {
//        int height = context.getWindowManager().getDefaultDisplay().getHeight();
//        v.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) percent * height));
//    }


}
