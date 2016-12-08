package com.name.cn.mydairly.data;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.NotNull;

import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;


/**
 * 日记user类型，默认只有一个
 * Created by Administrator on 2016-12-08.
 */

@Entity(indexes = {@Index(value = "text DESC",unique = true)})
public class User {
    @Id
    private Long id;

    @NotNull
    private Date text;

    @Generated(hash = 279264224)
    public User(Long id, @NotNull Date text) {
        this.id = id;
        this.text = text;
    }

    @Generated(hash = 586692638)
    public User() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getText() {
        return this.text;
    }

    public void setText(Date text) {
        this.text = text;
    }
}
