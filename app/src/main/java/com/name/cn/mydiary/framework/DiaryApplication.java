package com.name.cn.mydiary.framework;

import android.app.Application;



/**
 * app init
 * Created by guoshiqi on 2016/12/9.
 */

public class DiaryApplication extends Application {
    private static DiaryApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    private void init() {
        instance = this;
        GreenDaoManager.getInstance();
    }

    public static DiaryApplication getInstance() {
        return instance;
    }



}
