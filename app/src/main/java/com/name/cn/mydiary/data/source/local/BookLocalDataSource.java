package com.name.cn.mydiary.data.source.local;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.name.cn.mydiary.data.bookdetail.Book;
import com.name.cn.mydiary.data.source.BookDataSource;
import com.name.cn.mydiary.data.source.local.dao.BookDao;
import com.name.cn.mydiary.framework.GreenDaoManager;
import com.name.cn.mydiary.util.schedulers.BaseSchedulerProvider;

import org.greenrobot.greendao.annotation.NotNull;

import java.util.List;

import rx.Observable;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * 首页展示出item
 * Created by guoshiqi on 2016/12/16.
 */

public class BookLocalDataSource implements BookDataSource {

    @Nullable
    private static BookLocalDataSource INSTANCE;

    @NotNull
    private BookDao dao;


    public static BookLocalDataSource getInstance(
            @NonNull Context context,
            @NonNull BaseSchedulerProvider schedulerProvider) {
        if (INSTANCE == null) {
            INSTANCE = new BookLocalDataSource(context, schedulerProvider);
        }
        return INSTANCE;
    }


    // Prevent direct instantiation.
    private BookLocalDataSource(@NonNull Context context,
                                @NonNull BaseSchedulerProvider schedulerProvider) {
        checkNotNull(context, "context cannot be null");
        checkNotNull(schedulerProvider, "scheduleProvider cannot be null");
        dao = GreenDaoManager.getInstance().getmDaoSession().getBookDao();
    }

    @Override
    public Observable<List<Book>> getAllBooks(Long bookListId) {
        return Observable.just(dao.queryBuilder().where(BookDao.Properties.BookListId.eq(bookListId)).list());
    }


    @Override
    public Observable<Book> getBook(Long id) {
        return Observable.just(dao.load(id));
    }

    @Override
    public void saveBook(Book book) {
        dao.save(book);
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }


    @Override
    public void deleteAllBooks() {
        dao.deleteAll();
    }

    @Override
    public void deleteBooks(Long bookListId) {
        dao.queryBuilder().where(BookDao.Properties.BookListId.eq(bookListId)).buildDelete();
    }
}
