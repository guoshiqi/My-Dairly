package com.name.cn.mydiary.function.home;

import android.support.annotation.NonNull;

import com.name.cn.mydiary.data.source.BookDataSource;
import com.name.cn.mydiary.util.schedulers.BaseSchedulerProvider;

import rx.subscriptions.CompositeSubscription;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * mvp
 * Created by Administrator on 2016-12-12.
 */

public class HomePresenter implements HomeContract.Presenter {

    @NonNull
    private final BookDataSource mBooksRepository;

    @NonNull
    private final HomeContract.View mView;

    @NonNull
    private final BaseSchedulerProvider mSchedulerProvider;


    private boolean mIsDataMissing;


    public HomePresenter(@NonNull BookDataSource booksRepository,
                         @NonNull HomeContract.View homeView,
                         @NonNull BaseSchedulerProvider schedulerProvider) {
        this.mBooksRepository = checkNotNull(booksRepository, "booksRepository cannot be null");
        this.mView = checkNotNull(homeView, "homeView cannot be null");
        this.mSchedulerProvider = checkNotNull(schedulerProvider, "schedulerProvider cannot be null");

        mSubscriptions = new CompositeSubscription();
        mView.setPresenter(this);
    }

    @Override
    public void subscribe() {
        loadBooks(false);
    }

    @Override
    public void unSubscribe() {
        mSubscriptions.clear();
    }

    @NonNull
    private CompositeSubscription mSubscriptions;


    @Override
    public void saveBook(String title, String description) {

    }

    private void loadBooks(boolean forceUpdate){

    }
}
