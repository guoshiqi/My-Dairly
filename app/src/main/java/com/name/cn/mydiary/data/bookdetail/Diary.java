package com.name.cn.mydiary.data.bookdetail;

import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.ToMany;

import java.util.List;
import com.name.cn.mydiary.data.source.local.DaoSession;
import com.name.cn.mydiary.data.source.local.JournalDao;
import com.name.cn.mydiary.data.source.local.DiaryDao;


/**
 * 日记本
 * Created by guoshiqi on 2016/12/9.
 */

@Entity
public class Diary {

    @Id
    private Long Id;

    @NotNull
    private String name;

    private Long diaryOwnerId;

    private Long journalOwnId;

    @ToMany(referencedJoinProperty = "journalOwnId")
    private List<Journal> journalList;

    /**
     * Used to resolve relations
     */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /**
     * Used for active entity operations.
     */
    @Generated(hash = 21166549)
    private transient DiaryDao myDao;

    @Generated(hash = 1277024524)
    public Diary(Long Id, @NotNull String name, Long diaryOwnerId, Long journalOwnId) {
        this.Id = Id;
        this.name = name;
        this.diaryOwnerId = diaryOwnerId;
        this.journalOwnId = journalOwnId;
    }

    @Generated(hash = 112123061)
    public Diary() {
    }

    public Long getId() {
        return this.Id;
    }

    public void setId(Long Id) {
        this.Id = Id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }


    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 2084009828)
    public List<Journal> getJournalList() {
        if (journalList == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            JournalDao targetDao = daoSession.getJournalDao();
            List<Journal> journalListNew = targetDao._queryDiary_JournalList(Id);
            synchronized (this) {
                if (journalList == null) {
                    journalList = journalListNew;
                }
            }
        }
        return journalList;
    }

    /**
     * Resets a to-many relationship, making the next get call to query for a fresh result.
     */
    @Generated(hash = 100255691)
    public synchronized void resetJournalList() {
        journalList = null;
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


    public Long getJournalOwnId() {
        return this.journalOwnId;
    }

    public void setJournalOwnId(Long journalOwnId) {
        this.journalOwnId = journalOwnId;
    }

    public Long getDiaryOwnerId() {
        return this.diaryOwnerId;
    }

    public void setDiaryOwnerId(Long diaryOwnerId) {
        this.diaryOwnerId = diaryOwnerId;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 629297785)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getDiaryDao() : null;
    }
}
