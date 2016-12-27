package com.name.cn.mydiary.data.source;

import com.name.cn.mydiary.data.BookList;
import com.name.cn.mydiary.data.Config;
import com.name.cn.mydiary.data.User;

import rx.Observable;


/**
 * user and config
 * Created by guoshiqi on 2016/12/26.
 */

public interface UserDataSource {

    Observable<User> getUser(Long Id);

    Observable<Config> getConfig(Long Id);

    Observable<BookList> getBookList(Long Id);

    void saveBookList(BookList bookList);

    void saveUser(User user);

    void saveConfig(Config config);

    void deleteAllBookList();

    void deleteAllUser();

    void deleteAllConfig();

    void deleteUser(Long Id);

    void deleteConfig(Long Id);

    void deleteBookList(Long Id);
}
