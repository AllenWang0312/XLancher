package edu.tjrac.swant.baselibrary.common.util;

import android.content.Context;
import android.os.Vibrator;

public class VibrationUtils {

    private VibrationUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static void vibrate(Context context, long milliseconds) {
        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(milliseconds);
    }

    public static void vibrate(Context context, long[] pattern, int repeat) {
        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(pattern, repeat);
    }

    public static void cancel(Context context) {
        ((Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE)).cancel();
    }
}
