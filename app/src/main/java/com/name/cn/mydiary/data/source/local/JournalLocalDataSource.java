package com.name.cn.mydiary.data.source.local;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.name.cn.mydiary.data.bookdetail.Journal;
import com.name.cn.mydiary.data.source.JournalDataSource;
import com.name.cn.mydiary.framework.GreenDaoManager;
import com.name.cn.mydiary.util.schedulers.BaseSchedulerProvider;

import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.rx.RxDao;

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
    private RxDao<Journal, Long> rxDao;

    @NotNull
    private JournalDao dao;


    // Prevent direct instantiation.
    private JournalLocalDataSource(@NonNull Context context,
                                   @NonNull BaseSchedulerProvider schedulerProvider) {
        checkNotNull(context, "context cannot be null");
        checkNotNull(schedulerProvider, "scheduleProvider cannot be null");
        dao = GreenDaoManager.getInstance().getmDaoSession().getJournalDao();
        rxDao = GreenDaoManager.getInstance().getmDaoSession().getJournalDao().rx();

    }

    @Override
    public Observable<List<Journal>> getAllJournals() {
        return rxDao.loadAll();
    }

    @Override
    public Observable<List<Journal>> getJournals(@NonNull String journalOwnId) {

        GreenDaoManager.getInstance().getmDaoSession().clear();
        return Observable.from(dao._queryDiary_JournalList(Long.getLong(journalOwnId))).toList();
    }

    @Override
    public Observable<Journal> getJournal(@NonNull String journalId) {

        return rxDao.load(Long.getLong(journalId));
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
        dao.deleteByKey(Long.getLong(journalId));
    }
}
