package com.name.cn.mydiary.data.source.local;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.name.cn.mydiary.data.Config;
import com.name.cn.mydiary.data.User;
import com.name.cn.mydiary.data.source.UserDataSource;
import com.name.cn.mydiary.data.source.local.dao.ConfigDao;
import com.name.cn.mydiary.data.source.local.dao.UserDao;
import com.name.cn.mydiary.util.database.GreenDaoManager;
import com.name.cn.mydiary.util.schedulers.BaseSchedulerProvider;

import org.greenrobot.greendao.annotation.NotNull;

import rx.Observable;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * database user
 * Created by guoshiqi on 2016/12/26.
 */

public class UserLocalDataSource implements UserDataSource {

    @Nullable
    private static UserLocalDataSource INSTANCE;

    @NotNull
    private UserDao userDao;

    @NotNull
    private ConfigDao configDao;


    public static UserLocalDataSource getInstance(
            @NonNull Context context,
            @NonNull BaseSchedulerProvider schedulerProvider) {
        if (INSTANCE == null) {
            INSTANCE = new UserLocalDataSource(context, schedulerProvider);
        }
        return INSTANCE;
    }

    // Prevent direct instantiation.
    private UserLocalDataSource(@NonNull Context context,
                                @NonNull BaseSchedulerProvider schedulerProvider) {
        checkNotNull(context, "context cannot be null");
        checkNotNull(schedulerProvider, "scheduleProvider cannot be null");
        userDao = GreenDaoManager.getInstance().getmDaoSession().getUserDao();
        configDao = GreenDaoManager.getInstance().getmDaoSession().getConfigDao();
    }


    @Override
    public Observable<User> getUser(Long Id) {
        return Observable.just(userDao.load(Id));
    }

    @Override
    public Observable<Config> getConfig(Long Id) {
        return Observable.just(configDao.load(Id));
    }

    @Override
    public void saveUser(User user) {
        userDao.save(user);
    }

    @Override
    public void saveConfig(Config config) {
        configDao.save(config);
    }

    @Override
    public void deleteAllUser() {
        userDao.deleteAll();
    }

    @Override
    public void deleteAllConfig() {
        configDao.deleteAll();
    }

    @Override
    public void deleteUser(Long Id) {
        userDao.deleteByKey(Id);
    }

    @Override
    public void deleteConfig(Long Id) {
        configDao.deleteByKey(Id);
    }
}
