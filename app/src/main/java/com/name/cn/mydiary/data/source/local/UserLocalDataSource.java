package com.name.cn.mydiary.data.source.local;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.name.cn.mydiary.data.BookList;
import com.name.cn.mydiary.data.Config;
import com.name.cn.mydiary.data.User;
import com.name.cn.mydiary.data.source.UserDataSource;
import com.name.cn.mydiary.data.source.local.dao.BookListDao;
import com.name.cn.mydiary.data.source.local.dao.ConfigDao;
import com.name.cn.mydiary.data.source.local.dao.UserDao;
import com.name.cn.mydiary.util.database.GreenDaoManager;
import com.name.cn.mydiary.util.schedulers.BaseSchedulerProvider;

import rx.Observable;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * database user
 * Created by guoshiqi on 2016/12/26.
 */

public class UserLocalDataSource implements UserDataSource {

    @Nullable
    private static UserLocalDataSource INSTANCE;

    @NonNull
    private UserDao userDao;

    @NonNull
    private ConfigDao configDao;

    @NonNull
    private BookListDao bookListDao;


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
        bookListDao = GreenDaoManager.getInstance().getmDaoSession().getBookListDao();
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
    public Observable<BookList> getBookList(Long Id) {
        return Observable.just(bookListDao.load(Id));
    }

    @Override
    public void saveBookList(BookList bookList) {
        bookListDao.save(bookList);
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
    public void deleteAllBookList() {
        bookListDao.deleteAll();
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

    @Override
    public void deleteBookList(Long Id) {
        bookListDao.deleteByKey(Id);
    }
}
