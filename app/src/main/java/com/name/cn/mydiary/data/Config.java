package com.name.cn.mydiary.data;

import com.google.common.base.Objects;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Keep;

/**
 * 和用户有关的全局配置
 * Created by guoshiqi on 2016/12/26.
 */

@Entity
public class Config {

    @Id
    private Long id;


    private int bitmapId;


    @Generated(hash = 238064950)
    public Config(Long id, int bitmapId) {
        this.id = id;
        this.bitmapId = bitmapId;
    }


    @Generated(hash = 589037648)
    public Config() {
    }


    public Long getId() {
        return this.id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public int getBitmapId() {
        return this.bitmapId;
    }


    public void setBitmapId(int bitmapId) {
        this.bitmapId = bitmapId;
    }


    @Keep
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Config needCompare = (Config) obj;
        return Objects.equal(id, needCompare.id) && Objects.equal(bitmapId, needCompare.bitmapId);
    }
}
