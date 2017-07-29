package edu.tjrac.swant.baselibrary.common.interfaze;

import android.app.Activity;

/**
 * Created by wpc on 2017/5/19.
 */

public interface Permissionable {
   void checkPermissions(Activity context, String ... permissions);
}
