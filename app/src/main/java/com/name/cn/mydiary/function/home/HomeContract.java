package com.name.cn.mydiary.function.home;

import com.name.cn.mydiary.data.bookdetail.Book;
import com.name.cn.mydiary.framework.BasePresenter;
import com.name.cn.mydiary.framework.BaseView;

import java.util.List;

/**
 * This specifies the contract between the view and the presenter.
 * 约定
 * Created by Administrator on 2016-12-12.
 */

public interface HomeContract {

    interface View extends BaseView<Presenter>{

        void showEmptyDiaryError();

        void showAddBookView();

        void setLoadingIndicator(boolean active);

        void showBooks(List<Book> books);
    }

    interface Presenter extends BasePresenter {

        void result(int requestCode, int resultCode);

        void addNewBook();

        void saveBook(String title);

        void setFiltering(HomeFilterType requestType);

        void loadBooks(boolean forceUpdate);
    }
}
