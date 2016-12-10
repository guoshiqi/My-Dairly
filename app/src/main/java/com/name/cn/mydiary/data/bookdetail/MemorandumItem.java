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

    @Id
    private Long id;

    private Long memorandumItemOwnId;

    @NotNull
    private String name;

    @Generated(hash = 2049789004)
    public MemorandumItem(Long id, Long memorandumItemOwnId, @NotNull String name) {
        this.id = id;
        this.memorandumItemOwnId = memorandumItemOwnId;
        this.name = name;
    }

    @Generated(hash = 16864648)
    public MemorandumItem() {
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

    public Long getMemorandumItemOwnId() {
        return this.memorandumItemOwnId;
    }

    public void setMemorandumItemOwnId(Long memorandumItemOwnId) {
        this.memorandumItemOwnId = memorandumItemOwnId;
    }

}
