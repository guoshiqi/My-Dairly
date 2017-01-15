package com.name.cn.mydiary.data.source;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;

import com.name.cn.mydiary.data.bookdetail.Book;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

import static com.google.common.base.Preconditions.checkNotNull;


/**
 * book缓存
 * Created by guoshiqi on 2016/12/16.
 */

public class BooksRepository implements BookDataSource {

    @Nullable
    private static BooksRepository INSTANCE = null;

    @VisibleForTesting
    HashMap<Long, Book> cacheMap;

    @NonNull
    private final BookDataSource mBookLocalDataSource;

    /**
     * Marks the cache as invalid, to force an update the next time data is requested. This variable
     * has package local visibility so it can be accessed from tests.
     */
    @VisibleForTesting
    private boolean mCacheIsDirty = false;

    private BooksRepository(@NonNull BookDataSource bookLocalDataSource) {

        mBookLocalDataSource = checkNotNull(bookLocalDataSource);
    }

    /**
     * Returns the single instance of this class, creating it if necessary.
     *
     * @param bookLocalDataSource the device storage data source
     * @return the {@link JournalsRepository} instance
     */
    public static BooksRepository getInstance(@NonNull BookDataSource bookLocalDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new BooksRepository(bookLocalDataSource);
        }
        return INSTANCE;
    }

    /**
     * Used to force {@link #getInstance(BookDataSource)} to create a new instance
     * next time it's called.
     */
    public static void destroyInstance() {
        INSTANCE = null;
    }


    @Override
    public Observable<List<Book>> getAllBooks(Long bookListId) {
        checkNotNull(bookListId);
        if (cacheMap != null && !mCacheIsDirty) {
            List<Book> list = new ArrayList<>();
            for (Map.Entry<Long, Book> entry : cacheMap.entrySet()) {
                if (entry.getValue().getBookListId().equals(bookListId)) {
                    list.add(entry.getValue());
                }
            }
            return Observable.just(list);
        } else if (cacheMap == null) {
            cacheMap = new LinkedHashMap<>();
        }


        // Query the local storage if available.
        return getAndCacheLocalJournals(bookListId);
    }

    @Override
    public Observable<Book> getBook(@NonNull Long id) {
        checkNotNull(id);
        if (cacheMap != null) {
            return Observable.just(cacheMap.get(id));
        }
        if (cacheMap == null) {
            cacheMap = new LinkedHashMap<>();
        }
        return getBookWithIdFromLocalRepository(id);
    }

    @Override
    public void saveBook(Book book) {
        checkNotNull(book);
        mBookLocalDataSource.saveBook(book);
        if (cacheMap == null) {
            cacheMap = new LinkedHashMap<>();
        }
        cacheMap.put(book.getId(), book);
    }

    @Override
    public void deleteAllBooks() {
        mBookLocalDataSource.deleteAllBooks();
        if (cacheMap == null) {
            cacheMap = new LinkedHashMap<>();
        }
        cacheMap.clear();
    }

    @Override
    public void deleteBooks(Long bookListId) {
        checkNotNull(bookListId);
        mBookLocalDataSource.deleteBooks(bookListId);

        if (cacheMap == null) {
            cacheMap = new LinkedHashMap<>();
        }
        Iterator<Map.Entry<Long, Book>> it = cacheMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Long, Book> entry = it.next();
            if (entry.getValue().getBookListId().equals(bookListId)) {
                it.remove();
            }
        }
    }

    private Observable<List<Book>> getAndCacheLocalJournals(Long bookListId) {
        return mBookLocalDataSource.getAllBooks(bookListId)
                .flatMap(new Function<List<Book>, ObservableSource<List<Book>>>() {
                    @Override
                    public ObservableSource<List<Book>> apply(List<Book> books) throws Exception {
                        return Observable.fromIterable(books)
                                .doOnNext(book -> cacheMap.put(book.getId(), book))
                                .toList()
                                .toObservable();
                    }
                });
    }

    @NonNull
    private Observable<Book> getBookWithIdFromLocalRepository(@NonNull final Long bookId) {
        return mBookLocalDataSource
                .getBook(bookId)
                .doOnNext(book -> cacheMap.put(bookId, book))
                .firstElement()
                .toObservable();
    }

}
