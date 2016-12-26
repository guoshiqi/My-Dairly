package com.name.cn.mydiary.function.splash;

import com.name.cn.mydiary.data.User;
import com.name.cn.mydiary.framework.BasePresenter;
import com.name.cn.mydiary.framework.BaseView;

/**
 * 约定
 * Created by Administrator on 2016-12-25.
 */

public interface SplashContract {
    interface View extends BaseView<Presenter>{
        void showSplashPage(boolean isNeedUpdate);

        void showGuide();

        void gotoHomePage();
    }


    interface Presenter extends BasePresenter{
        void checkDataBaseVersion();

        void checkUser();


    }
}
