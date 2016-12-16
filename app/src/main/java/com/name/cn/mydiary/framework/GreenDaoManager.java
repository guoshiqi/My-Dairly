package com.name.cn.mydiary.framework;

import com.name.cn.mydiary.data.source.local.dao.DaoMaster;
import com.name.cn.mydiary.data.source.local.dao.DaoSession;

/**
 * greendao use
 * Created by Administrator on 2016-12-11.
 */

public class GreenDaoManager {
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;
    private DaoMaster.DevOpenHelper devOpenHelper;

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
        devOpenHelper = new DaoMaster.DevOpenHelper(DiaryApplication.getInstance(),
                "diary-db");
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

    public DaoMaster.OpenHelper getHelper(){
        return devOpenHelper;
    }
}