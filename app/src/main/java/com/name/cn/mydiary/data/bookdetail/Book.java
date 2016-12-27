package com.name.cn.mydiary.data.bookdetail;

import com.google.common.base.Objects;
import com.google.common.base.Strings;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.NotNull;

/**
 * book通过type区分日记等
 * Created by guoshiqi on 2016/12/16.
 */
@Entity
public class Book {

    public static final int BOOK_DIARY=1;
    public static final int BOOK_MEMORANDUM=2;

    @Id(autoincrement = true)
    private Long id;

    //对应bookList中bookId一个用户含有一个书单的书单id
    @NotNull
    private Long bookListId;

    @NotNull
    private int type;

    private String title;

    private String desc;






    @Generated(hash = 1839243756)
    public Book() {
    }




    @Generated(hash = 356085757)
    public Book(Long id, @NotNull Long bookListId, int type, String title, String desc) {
        this.id = id;
        this.bookListId = bookListId;
        this.type = type;
        this.title = title;
        this.desc = desc;
    }




    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Keep
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Book needCompare = (Book) obj;
        return Objects.equal(id, needCompare.id) && Objects.equal(bookListId, needCompare.bookListId)
                && Objects.equal(type, needCompare.type)&& Objects.equal(title, needCompare.title)
                && Objects.equal(desc, needCompare.desc);
    }

    @Keep
    public boolean isEmpty() {
        return Strings.isNullOrEmpty(title);
    }


    public Long getBookListId() {
        return this.bookListId;
    }




    public void setBookListId(Long bookListId) {
        this.bookListId = bookListId;
    }
}
