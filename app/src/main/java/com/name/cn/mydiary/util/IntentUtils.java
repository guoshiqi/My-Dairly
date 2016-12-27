package com.name.cn.mydiary.util;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.name.cn.mydiary.framework.AppConstants;
import com.name.cn.mydiary.function.home.HomeActivity;

/**
 * intent go Another activity
 * Created by guoshiqi on 2016/12/26.
 */

public class IntentUtils {

    public static void SplashToHomeActivity(Fragment frg, Context context, String user) {
        Intent intent = new Intent(context, HomeActivity.class);
        intent.putExtra(AppConstants.PASS_JSON, user);
        frg.startActivity(intent);
    }
}
