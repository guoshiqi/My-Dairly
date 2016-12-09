package com.name.cn.mydairly.framework;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.name.cn.mydairly.data.source.local.BookDetail.DaoMaster;
import com.name.cn.mydairly.data.source.local.BookDetail.DaoSession;


/**
 * app init
 * Created by guoshiqi on 2016/12/9.
 */

public class DiaryApplication extends Application {
    private DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    private void init() {
        createDb();
    }

    private void createDb() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "diary-db", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    public DaoSession getDbSession() {
        if (daoSession == null)
            createDb();
        return daoSession;
    }
}
