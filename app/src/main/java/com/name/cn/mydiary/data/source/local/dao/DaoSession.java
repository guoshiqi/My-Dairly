package com.name.cn.mydiary.data.source.local.dao;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.name.cn.mydiary.data.bookdetail.MemorandumItem;
import com.name.cn.mydiary.data.User;
import com.name.cn.mydiary.data.bookdetail.Journal;
import com.name.cn.mydiary.data.BookList;
import com.name.cn.mydiary.data.bookdetail.Book;

import com.name.cn.mydiary.data.source.local.dao.MemorandumItemDao;
import com.name.cn.mydiary.data.source.local.dao.UserDao;
import com.name.cn.mydiary.data.source.local.dao.JournalDao;
import com.name.cn.mydiary.data.source.local.dao.BookListDao;
import com.name.cn.mydiary.data.source.local.dao.BookDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig memorandumItemDaoConfig;
    private final DaoConfig userDaoConfig;
    private final DaoConfig journalDaoConfig;
    private final DaoConfig bookListDaoConfig;
    private final DaoConfig bookDaoConfig;

    private final MemorandumItemDao memorandumItemDao;
    private final UserDao userDao;
    private final JournalDao journalDao;
    private final BookListDao bookListDao;
    private final BookDao bookDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        memorandumItemDaoConfig = daoConfigMap.get(MemorandumItemDao.class).clone();
        memorandumItemDaoConfig.initIdentityScope(type);

        userDaoConfig = daoConfigMap.get(UserDao.class).clone();
        userDaoConfig.initIdentityScope(type);

        journalDaoConfig = daoConfigMap.get(JournalDao.class).clone();
        journalDaoConfig.initIdentityScope(type);

        bookListDaoConfig = daoConfigMap.get(BookListDao.class).clone();
        bookListDaoConfig.initIdentityScope(type);

        bookDaoConfig = daoConfigMap.get(BookDao.class).clone();
        bookDaoConfig.initIdentityScope(type);

        memorandumItemDao = new MemorandumItemDao(memorandumItemDaoConfig, this);
        userDao = new UserDao(userDaoConfig, this);
        journalDao = new JournalDao(journalDaoConfig, this);
        bookListDao = new BookListDao(bookListDaoConfig, this);
        bookDao = new BookDao(bookDaoConfig, this);

        registerDao(MemorandumItem.class, memorandumItemDao);
        registerDao(User.class, userDao);
        registerDao(Journal.class, journalDao);
        registerDao(BookList.class, bookListDao);
        registerDao(Book.class, bookDao);
    }
    
    public void clear() {
        memorandumItemDaoConfig.clearIdentityScope();
        userDaoConfig.clearIdentityScope();
        journalDaoConfig.clearIdentityScope();
        bookListDaoConfig.clearIdentityScope();
        bookDaoConfig.clearIdentityScope();
    }

    public MemorandumItemDao getMemorandumItemDao() {
        return memorandumItemDao;
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public JournalDao getJournalDao() {
        return journalDao;
    }

    public BookListDao getBookListDao() {
        return bookListDao;
    }

    public BookDao getBookDao() {
        return bookDao;
    }

}