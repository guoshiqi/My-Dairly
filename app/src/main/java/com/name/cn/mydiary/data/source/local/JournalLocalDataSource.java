package com.name.cn.mydiary.data.source.local;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.name.cn.mydiary.data.bookdetail.Journal;
import com.name.cn.mydiary.data.source.JournalDataSource;
import com.name.cn.mydiary.framework.GreenDaoManager;
import com.name.cn.mydiary.util.schedulers.BaseSchedulerProvider;
import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.SqlBrite;

import java.util.List;

import rx.Observable;
import rx.functions.Func1;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Concrete implementation of a data source as a db.
 * Created by Administrator on 2016-12-11.
 */

public class JournalLocalDataSource implements JournalDataSource {
    @Nullable
    private static JournalLocalDataSource INSTANCE;



    @NonNull
    private Func1<Cursor, Journal> mTaskMapperFunction;

    // Prevent direct instantiation.
    private JournalLocalDataSource(@NonNull Context context,
                                 @NonNull BaseSchedulerProvider schedulerProvider) {
        checkNotNull(context, "context cannot be null");
        checkNotNull(schedulerProvider, "scheduleProvider cannot be null");


    }

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
