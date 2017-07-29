package edu.tjrac.swant.baselibrary.base.interfaze;

import android.content.Context;

import java.util.HashMap;

/**
 * Created by wpc on 2017/4/17.
 */

public interface MySqlData {
    //save to mysql
    HashMap<String, Object> toHashMapForMySql(Context context);
}
