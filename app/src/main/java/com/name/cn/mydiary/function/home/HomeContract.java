package com.name.cn.mydiary.function.home;

import com.name.cn.mydiary.framework.BasePresenter;
import com.name.cn.mydiary.framework.BaseView;

/**
 * This specifies the contract between the view and the presenter.
 * 约定
 * Created by Administrator on 2016-12-12.
 */

public interface HomeContract {

    interface View extends BaseView<Presenter>{

        void showEmptyDiaryError();

        void showBooksList();




    }

    interface Presenter extends BasePresenter {

        void saveBook(String title, String description);

    }
}
