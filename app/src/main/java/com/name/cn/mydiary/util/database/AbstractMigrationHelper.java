package com.name.cn.mydiary.util.database;

import android.database.sqlite.SQLiteDatabase;

/**
 * abstract
 * Created by Administrator on 2016-12-25.
 */

public abstract class AbstractMigrationHelper {

    public abstract void onUpgrade(SQLiteDatabase db);
}
