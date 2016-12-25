package com.name.cn.mydiary.util.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.name.cn.mydiary.data.source.local.dao.DaoMaster;
import com.name.cn.mydiary.data.source.local.dao.DaoSession;
import com.name.cn.mydiary.data.source.local.dao.UserDao;
import com.name.cn.mydiary.framework.DiaryApplication;

import org.greenrobot.greendao.database.Database;

/**
 * greendao use
 * Created by Administrator on 2016-12-11.
 */

public class GreenDaoManager {
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;
    private GreenDaoOpenHelper devOpenHelper;

    private GreenDaoManager() {
        init();
    }

    /**
     * 静态内部类，实例化对象使用
     */
    private static class SingleInstanceHolder {
        private static final GreenDaoManager INSTANCE = new GreenDaoManager();
    }

    /**
     * 对外唯一实例的接口
     *
     * @return INSTANCE
     */
    public static GreenDaoManager getInstance() {
        return SingleInstanceHolder.INSTANCE;
    }

    /**
     * 初始化数据
     */
    private void init() {
        devOpenHelper = new GreenDaoOpenHelper(DiaryApplication.getInstance(),
                "diary-db", null);
        mDaoMaster = new DaoMaster(devOpenHelper.getWritableDatabase());
        mDaoSession = mDaoMaster.newSession();
    }

    public DaoMaster getmDaoMaster() {
        return mDaoMaster;
    }

    public DaoSession getmDaoSession() {
        return mDaoSession;
    }

    public DaoSession getNewSession() {
        mDaoSession = mDaoMaster.newSession();
        return mDaoSession;
    }

    public DaoMaster.OpenHelper getHelper() {
        return devOpenHelper;
    }


    public class GreenDaoOpenHelper extends DaoMaster.OpenHelper {
        public GreenDaoOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
            super(context, name, factory);
        }

        @Override
        public void onUpgrade(Database db, int oldVersion, int newVersion) {
            MigrationHelper.getInstance().migrate(db, UserDao.class);
        }
    }
}