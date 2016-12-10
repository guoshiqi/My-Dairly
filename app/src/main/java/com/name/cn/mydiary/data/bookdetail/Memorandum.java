package com.name.cn.mydiary.data.bookdetail;

import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToMany;

import java.util.List;

import com.name.cn.mydiary.data.source.local.DaoSession;
import com.name.cn.mydiary.data.source.local.MemorandumItemDao;
import com.name.cn.mydiary.data.source.local.MemorandumDao;

/**
 * 一本备忘录
 * Created by guoshiqi on 2016/12/9.
 */

@Entity
public class Memorandum {

    @Id
    private Long id;

    private Long memorandumOwnerId;

    private Long memorandumItemOwnId;

    @ToMany(referencedJoinProperty = "memorandumItemOwnId")
    private List<MemorandumItem> memorandumItems;

    /**
     * Used to resolve relations
     */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /**
     * Used for active entity operations.
     */
    @Generated(hash = 1780113397)
    private transient MemorandumDao myDao;

    @Generated(hash = 1000068299)
    public Memorandum(Long id, Long memorandumOwnerId, Long memorandumItemOwnId) {
        this.id = id;
        this.memorandumOwnerId = memorandumOwnerId;
        this.memorandumItemOwnId = memorandumItemOwnId;
    }

    @Generated(hash = 178772008)
    public Memorandum() {
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
    @Generated(hash = 2091999616)
    public List<MemorandumItem> getMemorandumItems() {
        if (memorandumItems == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            MemorandumItemDao targetDao = daoSession.getMemorandumItemDao();
            List<MemorandumItem> memorandumItemsNew = targetDao
                    ._queryMemorandum_MemorandumItems(id);
            synchronized (this) {
                if (memorandumItems == null) {
                    memorandumItems = memorandumItemsNew;
                }
            }
        }
        return memorandumItems;
    }

    /**
     * Resets a to-many relationship, making the next get call to query for a fresh result.
     */
    @Generated(hash = 839245472)
    public synchronized void resetMemorandumItems() {
        memorandumItems = null;
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


    public Long getMemorandumItemOwnId() {
        return this.memorandumItemOwnId;
    }

    public void setMemorandumItemOwnId(Long memorandumItemOwnId) {
        this.memorandumItemOwnId = memorandumItemOwnId;
    }

    public Long getMemorandumOwnerId() {
        return this.memorandumOwnerId;
    }

    public void setMemorandumOwnerId(Long memorandumOwnerId) {
        this.memorandumOwnerId = memorandumOwnerId;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 544473699)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getMemorandumDao() : null;
    }
}
