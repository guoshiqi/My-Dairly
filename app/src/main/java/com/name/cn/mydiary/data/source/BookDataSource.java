package com.name.cn.mydiary.data.source;

import com.name.cn.mydiary.data.bookdetail.Book;

import java.util.List;

import io.reactivex.Observable;


/**
 * 对于首页每本note的数据操作
 * Created by guoshiqi on 2016/12/16.
 */

public interface BookDataSource {

    Observable<List<Book>> getAllBooks(Long bookId);

    Observable<Book> getBook(Long id);

    void saveBook(Book book);

    void deleteAllBooks();

    void deleteBooks(Long bookListId);


}
