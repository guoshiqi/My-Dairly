package com.name.cn.mydiary.data.source;

import android.support.annotation.NonNull;

import com.name.cn.mydiary.data.bookdetail.Journal;

import java.util.List;

import rx.Observable;

/**
 * 日记缓存使用接口
 * Created by Administrator on 2016-12-11.
 */

public interface JournalDataSource {

    Observable<List<Journal>> getAllJournals();

    Observable<List<Journal>> getJournals(@NonNull String bookId);

    Observable<Journal> getJournal(@NonNull String journalId);

    void saveJournal(@NonNull Journal journal);

    void refreshJournals();

    void deleteAllJournals();

    void deleteJournal(@NonNull String taskId);
}
