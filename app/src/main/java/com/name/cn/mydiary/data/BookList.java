package com.name.cn.mydiary.data;



import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToMany;

import java.util.List;


import com.name.cn.mydiary.data.bookdetail.Diary;
import com.name.cn.mydiary.data.bookdetail.Memorandum;
import com.name.cn.mydiary.data.source.local.DaoSession;
import com.name.cn.mydiary.data.source.local.DiaryDao;
import com.name.cn.mydiary.data.source.local.MemorandumDao;
import com.name.cn.mydiary.data.source.local.BookListDao;

/**
 * 首页包含类型
 * Created by guoshiqi on 2016/12/9.
 */

@Entity
public class BookList {

    @Id
    private Long id;

    private Long memorandumOwnerId;

    @ToMany(referencedJoinProperty = "memorandumOwnerId")
    private List<Memorandum> memorandumList;

    private Long diaryOwnerId;

    @ToMany(referencedJoinProperty = "diaryOwnerId")
    private List<Diary> diaryList;

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

    @Generated(hash = 397993549)
    public BookList(Long id, Long memorandumOwnerId, Long diaryOwnerId) {
        this.id = id;
        this.memorandumOwnerId = memorandumOwnerId;
        this.diaryOwnerId = diaryOwnerId;
    }

    @Generated(hash = 1714324117)
    public BookList() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 1999649470)
    public List<Memorandum> getMemorandumList() {
        if (memorandumList == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            MemorandumDao targetDao = daoSession.getMemorandumDao();
            List<Memorandum> memorandumListNew = targetDao
                    ._queryBookList_MemorandumList(id);
            synchronized (this) {
                if (memorandumList == null) {
                    memorandumList = memorandumListNew;
                }
            }
        }
        return memorandumList;
    }

    /**
     * Resets a to-many relationship, making the next get call to query for a fresh result.
     */
    @Generated(hash = 277136916)
    public synchronized void resetMemorandumList() {
        memorandumList = null;
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

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 508891475)
    public List<Diary> getDiaryList() {
        if (diaryList == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            DiaryDao targetDao = daoSession.getDiaryDao();
            List<Diary> diaryListNew = targetDao._queryBookList_DiaryList(id);
            synchronized (this) {
                if (diaryList == null) {
                    diaryList = diaryListNew;
                }
            }
        }
        return diaryList;
    }

    /**
     * Resets a to-many relationship, making the next get call to query for a fresh result.
     */
    @Generated(hash = 1078514341)
    public synchronized void resetDiaryList() {
        diaryList = null;
    }


    public Long getMemorandumOwnerId() {
        return this.memorandumOwnerId;
    }

    public void setMemorandumOwnerId(Long memorandumOwnerId) {
        this.memorandumOwnerId = memorandumOwnerId;
    }

    public Long getDiaryOwnerId() {
        return this.diaryOwnerId;
    }

    public void setDiaryOwnerId(Long diaryOwnerId) {
        this.diaryOwnerId = diaryOwnerId;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1019987762)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getBookListDao() : null;
    }


}
