package com.hloong.demo;

import android.content.Context;

public class MyUtils {
    /**
     * 转换dip为px
     * 
     * @param context
     * @param dip
     * @return
     */
    public static int dip2px(Context context, int dip) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f * (dip >= 0 ? 1 : -1));
    }

    /**
     * 转换px为dip
     * 
     * @param context
     * @param px
     * @return
     */
    public static int px2dip(Context context, int px) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f * (px >= 0 ? 1 : -1));
    }

}
