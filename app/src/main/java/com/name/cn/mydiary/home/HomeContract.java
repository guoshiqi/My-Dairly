package com.name.cn.mydiary.home;

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

        void setTitle(String title);

        void setDescription(String description);


    }

    interface Presenter extends BasePresenter {

        void saveDiary(String title, String description);

        boolean isDataMissing();
    }
}
