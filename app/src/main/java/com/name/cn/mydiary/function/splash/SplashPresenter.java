package com.name.cn.mydiary.function.splash;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.name.cn.mydiary.data.BookList;
import com.name.cn.mydiary.data.Config;
import com.name.cn.mydiary.data.User;
import com.name.cn.mydiary.data.source.UserDataSource;
import com.name.cn.mydiary.framework.AppConstants;
import com.name.cn.mydiary.util.DateUtils;
import com.name.cn.mydiary.util.SPUtils;
import com.name.cn.mydiary.util.schedulers.BaseSchedulerProvider;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.CompletableSource;
import io.reactivex.disposables.CompositeDisposable;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * 启动页presenter
 * Created by Administrator on 2016-12-25.
 */

public class SplashPresenter implements SplashContract.Presenter {
    @NonNull
    private final UserDataSource mUserRepository;

    @NonNull
    private final SplashContract.View mSplashView;

    @NonNull
    private final BaseSchedulerProvider mSchedulerProvider;

    @Nullable
    private User user;


    public SplashPresenter(@NonNull UserDataSource userRepository,
                           @NonNull SplashContract.View splashView,
                           @NonNull BaseSchedulerProvider schedulerProvider) {
        this.mUserRepository = checkNotNull(userRepository, "booksRepository cannot be null");
        this.mSplashView = checkNotNull(splashView, "homeView cannot be null");
        this.mSchedulerProvider = checkNotNull(schedulerProvider, "schedulerProvider cannot be null");

        mSubscriptions = new CompositeDisposable();
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
    private CompositeDisposable mSubscriptions;


    //检查数据库是否需要升级
    @Override
    public void checkDataBaseVersion() {
        checkUser();
    }

    //检查是否新用户
    @Override
    public void checkUser() {
        Long userId = (Long) SPUtils.get(AppConstants.APP_USER_ID, AppConstants.DEFAULT_LONG);
        if (userId.equals(AppConstants.DEFAULT_LONG)) {
            createNewUserAndShowGuide();
        } else {
            mSubscriptions.add(mUserRepository
                    .getUser(userId)
                    .subscribeOn(mSchedulerProvider.io())
                    .observeOn(mSchedulerProvider.ui())
                    .subscribe(
                            //onnext
                            user -> {
                                if (user != null) {
//                                    goGuideWithAnim();
                                    normalGoHomeAnim(user);
                                } else {
                                    createNewUserAndShowGuide();
                                }
                            }
                            , // onError
                            __ -> {
                                if (mSplashView.isActive()) {
                                    createNewUserAndShowGuide();
                                }
                            }));
        }
    }

    @Override
    public void saveUser(int sex, String name, String url) {
        checkNotNull(user);
        user.setSex(sex);
        user.setName(name);
        user.setHeadPictureUrl(url);
        mUserRepository.saveUser(user);
        mSplashView.gotoHomePage(user);
    }


    private void createNewUserAndShowGuide() {
        user = new User(null, DateUtils.getNowTime());
        Config config = new Config(null, "as");
        BookList bookList = new BookList(null);
        mUserRepository.saveConfig(config);
        mUserRepository.saveBookList(bookList);
        user.setConfigId(config.getId());
        user.setBookListId(bookList.getId());
        mUserRepository.saveUser(user);
        Long userId = user.getId();
        SPUtils.put(AppConstants.APP_USER_ID, userId);
        goGuideWithAnim();
    }

    private void normalGoHomeAnim(User user) {
        Completable pathAnim = Completable.create(mSplashView::runPathAnim);
        Completable backAnim = Completable.create(mSplashView::runBackGroundAnim);
        Completable scaleAnim = Completable.create(mSplashView::runScaleAnim);
        mSubscriptions.add(pathAnim
                .mergeWith(backAnim)
                .andThen(scaleAnim)
                .andThen(new CompletableSource() {
                    @Override
                    public void subscribe(CompletableObserver cs) {
                        mSplashView.gotoHomePage(user);
                    }
                })
                .subscribe());
    }


    private void goGuideWithAnim() {
        Completable pathAnim = Completable.create(mSplashView::runPathAnim);
        Completable backAnim = Completable.create(mSplashView::runBackGroundAnim);
        Completable circleAnim = Completable.create(mSplashView::runCircleAnim);
        mSubscriptions.add(pathAnim
                .mergeWith(backAnim)
                .andThen(circleAnim)
                .andThen(new CompletableSource() {
                    @Override
                    public void subscribe(CompletableObserver cs) {
                        mSplashView.showGuide();
                    }
                })
                .subscribe());
    }

}
