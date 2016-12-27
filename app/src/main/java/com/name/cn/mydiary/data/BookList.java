package com.name.cn.mydiary.data;


import com.name.cn.mydiary.data.bookdetail.Book;
import com.name.cn.mydiary.data.source.local.dao.BookListDao;
import com.name.cn.mydiary.data.source.local.dao.DaoSession;

import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.ToMany;

import java.util.List;
import com.name.cn.mydiary.data.source.local.dao.BookDao;

/**
 * 首页包含类型
 * Created by guoshiqi on 2016/12/9.
 */

@Entity
public class BookList {

    @Id(autoincrement = true)
    private Long id;



    @ToMany(referencedJoinProperty="id")
    private List<Book> bookList;


    /**
     * Used to resolve relations
     */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /**
     * Used for active entity operations.
     */
    @Generated(hash = 1203780597)
    private transient BookListDao myDao;



    @Generated(hash = 1714324117)
    public BookList() {
    }







    @Generated(hash = 1978383357)
    public BookList(Long id) {
        this.id = id;
    }







    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
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





    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 1428566046)
    public synchronized void resetBookList() {
        bookList = null;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1019987762)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getBookListDao() : null;
    }







    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 1657237779)
    public List<Book> getBookList() {
        if (bookList == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            BookDao targetDao = daoSession.getBookDao();
            List<Book> bookListNew = targetDao._queryBookList_BookList(id);
            synchronized (this) {
                if (bookList == null) {
                    bookList = bookListNew;
                }
            }
        }
        return bookList;
    }


}
