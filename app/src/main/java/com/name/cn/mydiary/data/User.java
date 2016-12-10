package com.name.cn.mydiary.data;



import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.ToOne;

import java.util.Date;
import com.name.cn.mydiary.data.source.local.DaoSession;
import com.name.cn.mydiary.data.source.local.BookListDao;
import com.name.cn.mydiary.data.source.local.UserDao;



/**
 * 日记user类型，默认只有一个
 * Created by Administrator on 2016-12-08.
 */

@Entity
public class User {
    @Id(autoincrement = true)
    private Long Id;

    @NotNull
    private Date date;

    private Long bookListId;

    @ToOne(joinProperty = "bookListId")
    private BookList bookList;

    /**
     * Used to resolve relations
     */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /**
     * Used for active entity operations.
     */
    @Generated(hash = 1507654846)
    private transient UserDao myDao;

    @Generated(hash = 1015856170)
    private transient Long bookList__resolvedKey;


    public User(Long Id, @NotNull Date date) {
        this.Id = Id;
        this.date = date;
    }


    public User() {
    }


    @Generated(hash = 1221087395)
    public User(Long Id, @NotNull Date date, Long bookListId) {
        this.Id = Id;
        this.date = date;
        this.bookListId = bookListId;
    }


    public Date getText() {
        return this.date;
    }

    public void setText(Date text) {
        this.date = text;
    }


    public Date getDate() {
        return this.date;
    }


    public void setDate(Date date) {
        this.date = date;
    }


    public Long getBookListId() {
        return this.bookListId;
    }


    public void setBookListId(Long bookListId) {
        this.bookListId = bookListId;
    }


    /**
     * To-one relationship, resolved on first access.
     */
    @Generated(hash = 2105435313)
    public BookList getBookList() {
        Long __key = this.bookListId;
        if (bookList__resolvedKey == null || !bookList__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            BookListDao targetDao = daoSession.getBookListDao();
            BookList bookListNew = targetDao.load(__key);
            synchronized (this) {
                bookList = bookListNew;
                bookList__resolvedKey = __key;
            }
        }
        return bookList;
    }


    /**
     * called by internal mechanisms, do not call yourself.
     */
    @Generated(hash = 2070771230)
    public void setBookList(BookList bookList) {
        synchronized (this) {
            this.bookList = bookList;
            bookListId = bookList == null ? null : bookList.getId();
            bookList__resolvedKey = bookListId;
        }
    }


    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }


    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }


    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }


    public Long getId() {
        return this.Id;
    }


    public void setId(Long Id) {
        this.Id = Id;
    }


    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 2059241980)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getUserDao() : null;
    }
}
