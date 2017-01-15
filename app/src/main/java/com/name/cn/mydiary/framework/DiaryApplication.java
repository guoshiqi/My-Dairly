package com.name.cn.mydiary.framework;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import com.name.cn.mydiary.Injection;
import com.name.cn.mydiary.R;
import com.name.cn.mydiary.data.User;
import com.name.cn.mydiary.util.database.GreenDaoManager;
import com.name.cn.mydiary.util.schedulers.SchedulerProvider;
import com.squareup.leakcanary.LeakCanary;

import java.util.List;


import io.reactivex.Observable;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;


/**
 * app init
 * Created by guoshiqi on 2016/12/9.
 */

public class DiaryApplication extends Application {
    private static DiaryApplication instance;

    public static DiaryApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
        // Normal app init code...
        String processName = getProcessName(this);
        if (processName != null) {
            if (processName.equals("com.name.cn.mydiary.mock")) {
                //初始化com.name.cn.mydairly以包名为进程名，项目默认的进程
                initApp();
            }
        }
    }

    private void initApp() {
        instance = this;
        GreenDaoManager.getInstance();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/qingyuan.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }

    private String getProcessName(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningApps = am.getRunningAppProcesses();
        if (runningApps == null) {
            return null;
        }
        for (ActivityManager.RunningAppProcessInfo proInfo : runningApps) {
            if (proInfo.pid == android.os.Process.myPid()) {
                if (proInfo.processName != null) {
                    return proInfo.processName;
                }
            }
        }
        return null;
    }

    public Observable<User> getUser(Long userId) {
        return Injection.provideUserRepository(this).getUser(userId);
    }

    public void showToast(String msg) {
        showToast(msg + "", Toast.LENGTH_SHORT);
    }

    public void showToast(String msg, final int length) {
        Observable
                .empty()
                .observeOn(SchedulerProvider.getInstance().ui())
                .doOnComplete(() -> {
                    Toast toast = Toast.makeText(instance, msg + "", length);
                    toast.show();
                })
                .subscribe();
    }

}
