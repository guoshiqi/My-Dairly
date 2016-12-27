package com.name.cn.mydiary.data.bookdetail;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;

/**
 * 备忘录具体条目
 * Created by guoshiqi on 2016/12/9.
 */

@Entity
public class MemorandumItem {

    @Id(autoincrement = true)
    private Long id;

    @NotNull
    private Long bookId;

    @NotNull
    private String name;



    @Generated(hash = 16864648)
    public MemorandumItem() {
    }

    @Generated(hash = 1814057740)
    public MemorandumItem(Long id, @NotNull Long bookId, @NotNull String name) {
        this.id = id;
        this.bookId = bookId;
        this.name = name;
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

    public Long getBookId() {
        return this.bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }



}
