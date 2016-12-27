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

    @Id(autoincrement = true)
    private Long id;


    private String bitmapId;


   

    @Generated(hash = 589037648)
    public Config() {
    }


    @Generated(hash = 1003127772)
    public Config(Long id, String bitmapId) {
        this.id = id;
        this.bitmapId = bitmapId;
    }


    public Long getId() {
        return this.id;
    }


    public void setId(Long id) {
        this.id = id;
    }





    @Keep
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Config needCompare = (Config) obj;
        return Objects.equal(id, needCompare.id) && Objects.equal(bitmapId, needCompare.bitmapId);
    }


    public String getBitmapId() {
        return this.bitmapId;
    }


    public void setBitmapId(String bitmapId) {
        this.bitmapId = bitmapId;
    }
}
