package com.name.cn.mydiary.data.source;

import android.support.annotation.NonNull;

import com.name.cn.mydiary.data.bookdetail.Journal;

import java.util.List;

import rx.Observable;

/**
 * Concrete implementation to load 日记 from the data sources into a cache.
 * Created by Administrator on 2016-12-11.
 */

public class JournalsRepository implements JournalDataSource {
    @Override
    public Observable<List<Journal>> getJournals(@NonNull String journalOwnId) {
        return null;
    }

    @Override
    public Observable<Journal> getJournal(@NonNull String journalId) {
        return null;
    }

    @Override
    public void saveJournal(@NonNull Journal journal) {

    }

    @Override
    public void refreshJournals() {

    }

    @Override
    public void deleteAllJournals() {

    }

    @Override
    public void deleteJournal(@NonNull String taskId) {

    }
}
