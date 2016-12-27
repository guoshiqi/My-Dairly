package com.name.cn.mydiary.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.content.SharedPreferencesCompat;

import com.name.cn.mydiary.framework.DiaryApplication;

import java.util.Map;

/**
 * app sharePreferenceUtil
 * Created by guoshiqi on 2016/12/27.
 */

public class SPUtils {

    public static String FILLNAME = "config";

    /**
     * 存入某个key对应的value值
     *
     * @param key key
     * @param value value
     */
    public static void put(String key, Object value) {
        Context context = DiaryApplication.getInstance();
        SharedPreferences sp = context.getSharedPreferences(FILLNAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        if (value instanceof String) {
            edit.putString(key, (String) value);
        } else if (value instanceof Integer) {
            edit.putInt(key, (Integer) value);
        } else if (value instanceof Boolean) {
            edit.putBoolean(key, (Boolean) value);
        } else if (value instanceof Float) {
            edit.putFloat(key, (Float) value);
        } else if (value instanceof Long) {
            edit.putLong(key, (Long) value);
        }
        SharedPreferencesCompat.EditorCompat.getInstance().apply(edit);
    }

    /**
     * 得到某个key对应的值
     *
     * @param key key
     * @param defValue default
     * @return (T)Object
     */
    public static Object get(String key, Object defValue) {
        Context context = DiaryApplication.getInstance();
        SharedPreferences sp = context.getSharedPreferences(FILLNAME, Context.MODE_PRIVATE);
        if (defValue instanceof String) {
            return sp.getString(key, (String) defValue);
        } else if (defValue instanceof Integer) {
            return sp.getInt(key, (Integer) defValue);
        } else if (defValue instanceof Boolean) {
            return sp.getBoolean(key, (Boolean) defValue);
        } else if (defValue instanceof Float) {
            return sp.getFloat(key, (Float) defValue);
        } else if (defValue instanceof Long) {
            return sp.getLong(key, (Long) defValue);
        }
        return defValue;
    }

    /**
     * 返回所有数据
     *
     * @return map
     */
    public static Map<String, ?> getAll() {
        Context context = DiaryApplication.getInstance();
        SharedPreferences sp = context.getSharedPreferences(FILLNAME, Context.MODE_PRIVATE);
        return sp.getAll();
    }

    /**
     * 移除某个key值已经对应的值
     *
     * @param key key
     */
    public static void remove(String key) {
        Context context = DiaryApplication.getInstance();
        SharedPreferences sp = context.getSharedPreferences(FILLNAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.remove(key);
        SharedPreferencesCompat.EditorCompat.getInstance().apply(edit);
    }

    /**
     * 清除所有内容
     *
     */
    public static void clear() {
        Context context = DiaryApplication.getInstance();
        SharedPreferences sp = context.getSharedPreferences(FILLNAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.clear();
        SharedPreferencesCompat.EditorCompat.getInstance().apply(edit);
    }


}
