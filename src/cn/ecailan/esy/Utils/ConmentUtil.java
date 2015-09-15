package cn.ecailan.esy.Utils;

import android.content.Context;

/**
 * Created by John on 2015/7/27.
 */
public class ConmentUtil {

    public static float getScreenDensity(Context context) {
        return context.getResources().getDisplayMetrics().density;
    }

    public static int dip2px(Context context, float px) {
        final float scale = getScreenDensity(context);
        return (int) (px * scale + 0.5);
    }

}
