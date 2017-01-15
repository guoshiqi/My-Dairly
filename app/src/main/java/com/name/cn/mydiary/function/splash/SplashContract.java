package com.name.cn.mydiary.function.splash;

import com.name.cn.mydiary.data.User;
import com.name.cn.mydiary.framework.BasePresenter;
import com.name.cn.mydiary.framework.BaseView;

import io.reactivex.CompletableEmitter;

/**
 * 约定
 * Created by Administrator on 2016-12-25.
 */

public interface SplashContract {
    interface View extends BaseView<Presenter> {
        void showSplashPage(boolean isNeedUpdate);

        void showGuide();

        void gotoHomePage(User user);

        boolean isActive();

        void runPathAnim(CompletableEmitter completableEmitter);

        void runBackGroundAnim(CompletableEmitter emitter);

        void runCircleAnim(CompletableEmitter emitter);

        void runScaleAnim(CompletableEmitter emitter);

        void runAlphaAnim(CompletableEmitter emitter);

    }


    interface Presenter extends BasePresenter {
        void checkDataBaseVersion();

        void checkUser();

        void saveUser(int sex, String name, String url);
    }
}
