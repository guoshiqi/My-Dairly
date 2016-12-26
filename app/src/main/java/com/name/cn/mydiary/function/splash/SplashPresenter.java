package com.name.cn.mydiary.function.splash;

import android.support.annotation.NonNull;

import com.name.cn.mydiary.data.source.UserDataSource;
import com.name.cn.mydiary.util.schedulers.BaseSchedulerProvider;

import rx.subscriptions.CompositeSubscription;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * 启动页presenter
 * Created by Administrator on 2016-12-25.
 */

public class SplashPresenter implements SplashContract.Presenter{
    @NonNull
    private final UserDataSource mUserRepository;

    @NonNull
    private final SplashContract.View mSplashView;

    @NonNull
    private final BaseSchedulerProvider mSchedulerProvider;


    public SplashPresenter(@NonNull UserDataSource userRepository,
                         @NonNull SplashContract.View splashView,
                         @NonNull BaseSchedulerProvider schedulerProvider) {
        this.mUserRepository = checkNotNull(userRepository, "booksRepository cannot be null");
        this.mSplashView = checkNotNull(splashView, "homeView cannot be null");
        this.mSchedulerProvider = checkNotNull(schedulerProvider, "schedulerProvider cannot be null");

        mSubscriptions = new CompositeSubscription();
        mSplashView.setPresenter(this);
    }

    @Override
    public void subscribe() {
        checkDataBaseVersion();
    }

    @Override
    public void unSubscribe() {
        mSubscriptions.clear();
    }

    @NonNull
    private CompositeSubscription mSubscriptions;


    //检查数据库是否需要升级
    @Override
    public void checkDataBaseVersion() {
        checkUser();
    }

    //检查是否新用户
    @Override
    public void checkUser() {
//        mSplashView.showGuide();
        mSplashView.gotoHomePage();
    }

}
