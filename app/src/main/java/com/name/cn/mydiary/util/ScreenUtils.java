package com.name.cn.mydiary.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;

/**
 * screen
 * Created by guoshiqi on 2016/12/29.
 */

public class ScreenUtils {

    public static int[] getScreen(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return new int[]{dm.widthPixels,dm.heightPixels};
    }


}
