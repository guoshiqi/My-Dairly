package com.name.cn.mydiary.data.source.local;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.name.cn.mydiary.data.bookdetail.Journal;
import com.name.cn.mydiary.data.source.JournalDataSource;
import com.name.cn.mydiary.framework.GreenDaoManager;
import com.name.cn.mydiary.util.schedulers.BaseSchedulerProvider;
import com.name.cn.mydiary.util.schedulers.SchedulerProvider;

import org.greenrobot.greendao.annotation.NotNull;

import java.util.List;

import rx.Observable;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Concrete implementation of a data source as a db.
 * Created by Administrator on 2016-12-11.
 */

public class JournalLocalDataSource implements JournalDataSource {
    @Nullable
    private static JournalLocalDataSource INSTANCE;

    @NotNull
    private JournalDao dao;

    public static JournalLocalDataSource getInstance(
            @NonNull Context context,
            @NonNull BaseSchedulerProvider schedulerProvider) {
        if (INSTANCE == null) {
            INSTANCE = new JournalLocalDataSource(context, schedulerProvider);
        }
        return INSTANCE;
    }


    // Prevent direct instantiation.
    private JournalLocalDataSource(@NonNull Context context,
                                   @NonNull BaseSchedulerProvider schedulerProvider) {
        checkNotNull(context, "context cannot be null");
        checkNotNull(schedulerProvider, "scheduleProvider cannot be null");
        dao = GreenDaoManager.getInstance().getmDaoSession().getJournalDao();
    }

    @Override
    public Observable<List<Journal>> getAllJournals() {
        return Observable.just(dao.loadAll()).subscribeOn(SchedulerProvider.getInstance().io());
    }

    @Override
    public Observable<List<Journal>> getJournals(@NonNull String journalOwnId) {

        GreenDaoManager.getInstance().getmDaoSession().clear();
        return Observable.from(dao._queryDiary_JournalList(Long.valueOf(journalOwnId))).toList().subscribeOn(SchedulerProvider.getInstance().io());
    }

    @Override
    public Observable<Journal> getJournal(@NonNull String journalId) {

        return Observable.just(dao.load(Long.valueOf(journalId))).subscribeOn(SchedulerProvider.getInstance().io());
    }

    @Override
    public void saveJournal(@NonNull Journal journal) {
        checkNotNull(journal);
        dao.save(journal);
    }

    @Override
    public void refreshJournals() {
        //null
    }

    @Override
    public void deleteAllJournals() {
        dao.deleteAll();
    }

    @Override
    public void deleteJournal(@NonNull String journalId) {
        dao.deleteByKey(Long.valueOf(journalId));
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}
