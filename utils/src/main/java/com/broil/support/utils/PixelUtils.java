package com.broil.support.utils;

import android.content.Context;
import android.content.res.Resources;
import android.view.WindowManager;

/**
 * 像素转换工具
 *
 * @author fmz@zniot.com
 */
public class PixelUtils {

    private PixelUtils() {
    }

    /**
     * 获取屏幕宽度
     *
     * @return
     */
    public static int getWindowWidth(Context mContext) {
        WindowManager wm = (WindowManager) mContext.getSystemService(
                Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();

        return width;
    }

    /**
     * 获取屏幕高度
     *
     * @return
     */
    public static int getWindowHeight(Context mContext) {
        WindowManager wm = (WindowManager) mContext.getSystemService(
                Context.WINDOW_SERVICE);
        int height = wm.getDefaultDisplay().getHeight();

        return height;
    }


    /**
     * dp转 px.
     *
     * @param value   the value
     * @param context the context
     * @return the int
     */
    public static int dp2px(float value, Context context) {
        final float scale = context.getResources().getDisplayMetrics().densityDpi;
        return (int) (value * (scale / 160) + 0.5f);
    }

    /**
     * px转dp.
     *
     * @param value   the value
     * @param context the context
     * @return the int
     */
    public static int px2dp(float value, Context context) {
        final float scale = context.getResources().getDisplayMetrics().densityDpi;
        return (int) ((value * 160) / scale + 0.5f);
    }


    /**
     * sp转px.
     *
     * @param value   the value
     * @param context the context
     * @return the int
     */
    public static int sp2px(float value, Context context) {
        Resources r;
        if (context == null) {
            r = Resources.getSystem();
        } else {
            r = context.getResources();
        }
        float spvalue = value * r.getDisplayMetrics().scaledDensity;
        return (int) (spvalue + 0.5f);
    }


    /**
     * px转sp.
     *
     * @param value   the value
     * @param context the context
     * @return the int
     */
    public static int px2sp(float value, Context context) {
        final float scale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (value / scale + 0.5f);
    }
}