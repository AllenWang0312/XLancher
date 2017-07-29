package edu.tjrac.swant.baselibrary.common.adapter;

import android.view.View;

/**
 * Created by wpc on 2017/4/20.
 */

public interface ShowAbleItem {
    public String getTitle();

    public Integer getBackgroundColor();

    public Integer getImageResId();

    public View getItemView();
}
