package com.name.cn.mydiary.data.bookdetail;

import com.google.common.base.Objects;
import com.name.cn.mydiary.util.DateUtils;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.NotNull;

import java.util.Date;


/**
 * 日记
 * Created by guoshiqi on 2016/12/9.
 */

@Entity
public class Journal {


    @Id(autoincrement = true)
    private Long id;

    @NotNull
    private Long bookId;

    @NotNull
    private String name;

    @NotNull
    private Date createTime;

    @Generated(hash = 1562390721)
    public Journal() {
    }

    @Generated(hash = 384723743)
    public Journal(Long id, @NotNull Long bookId, @NotNull String name,
                   @NotNull Date createTime) {
        this.id = id;
        this.bookId = bookId;
        this.name = name;
        this.createTime = createTime;
    }

    @Keep
    public Journal(Long id, @NotNull Long bookId, @NotNull String name) {
        this.id = id;
        this.bookId = bookId;
        this.name = name;
        this.createTime = DateUtils.getNowTime();
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Keep
    public String getStringId() {
        return id.toString();
    }

    @Keep
    public String getStringJournalOwnId() {
        return getId().toString();
    }

    @Keep
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Journal needCompare = (Journal) obj;
        return Objects.equal(id, needCompare.id) && Objects.equal(bookId, needCompare.bookId)
                && Objects.equal(name, needCompare.name);

    }

    public Long getBookId() {
        return this.bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
