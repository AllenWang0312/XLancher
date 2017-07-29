package edu.tjrac.swant.baselibrary.util;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

/**
 * Created by wpc on 2017/6/21.
 */

public class ChartUtils {
   public enum direction {
        left, top, right, bottom
    }

    public static void addDoitLine(Canvas c, Point from, Point to, Paint p, float num, int dot_width) {
        p.setStrokeWidth(1);
        c.drawLine(from.x, from.y, to.x, to.y, p);
        p.setStrokeWidth(dot_width);
        for (int i = 1; i < num; i++) {
            c.drawPoint(from.x + (to.x - from.x) * (i / num), from.y + (to.y - from.y) * (i / num), p);
        }
    }

    public static void addText(Canvas c, String str, Point base, int size, int margin, Paint p, direction d) {
        p.setTextSize(size);
        float length = p.measureText(str);
        switch (d) {
            case left:
                c.drawText(str, base.x - length - margin, base.y, p);
                break;
            case top:
                c.drawText(str, base.x - length / 2, base.y- margin, p);
                break;
            case right:
                c.drawText(str, base.x + margin, base.y , p);
                break;
            case bottom:
                c.drawText(str, base.x - length / 2, base.y + margin, p);
                break;
        }

    }

    ;
}
