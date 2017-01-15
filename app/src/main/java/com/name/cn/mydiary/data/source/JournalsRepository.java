package com.name.cn.mydiary.data.source;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;

import com.name.cn.mydiary.data.bookdetail.Journal;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Concrete implementation to load 日记 from the data sources into a cache.
 * Created by Administrator on 2016-12-11.
 */

public class JournalsRepository implements JournalDataSource {

    @Nullable
    private static JournalsRepository INSTANCE = null;
    @NonNull
    private final JournalDataSource mJournalLocalDataSource;

    //cache
    @VisibleForTesting
    @Nullable
    Map<String, Map<String, Journal>> mCachedJournals;

    /**
     * Marks the cache as invalid, to force an update the next time data is requested. This variable
     * has package local visibility so it can be accessed from tests.
     */
    @VisibleForTesting
    private boolean mCacheIsDirty = false;

    private JournalsRepository(@NonNull JournalDataSource journalLocalDataSource) {

        mJournalLocalDataSource = checkNotNull(journalLocalDataSource);
    }

    /**
     * Returns the single instance of this class, creating it if necessary.
     *
     * @param journalLocalDataSource the device storage data source
     * @return the {@link JournalsRepository} instance
     */
    public static JournalsRepository getInstance(@NonNull JournalDataSource journalLocalDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new JournalsRepository(journalLocalDataSource);
        }
        return INSTANCE;
    }

    /**
     * Used to force {@link #getInstance(JournalDataSource)} to create a new instance
     * next time it's called.
     */
    public static void destroyInstance() {
        INSTANCE = null;
    }

    /**
     * Gets tasks from cache, local data source (greenDao) whichever is
     * available first.
     */
    @Override
    public Observable<List<Journal>> getAllJournals() {
        // Respond immediately with cache if available and not dirty
        if (mCachedJournals != null && !mCacheIsDirty) {
            return Observable.fromIterable(getTotalJournal()).toList().toObservable();
        } else if (mCachedJournals == null) {
            mCachedJournals = new LinkedHashMap<>();
        }


        // Query the local storage if available.
        return getAndCacheLocalJournals();

    }

    @Override
    public Observable<List<Journal>> getJournals(@NonNull String bookId) {
        checkNotNull(bookId);

        if (mCachedJournals != null) {
            return Observable.fromIterable(mCachedJournals.get(bookId).values()).toList().toObservable();
        }
        return getJournalWithOwnDiaryIdFromLocalRepository(bookId);
    }

    @Override
    public Observable<Journal> getJournal(@NonNull String journalId) {
        checkNotNull(journalId);

        final Journal cachedJournal = getJournalWithId(journalId);

        // Respond immediately with cache if available
        if (cachedJournal != null) {
            return Observable.just(cachedJournal);
        }

        // Load from server/persisted if needed.

        // Do in memory cache update to keep the app UI up to date
        if (mCachedJournals == null) {
            mCachedJournals = new LinkedHashMap<>();
        }

        // Is the task in the local data source


        return getJournalWithIdFromLocalRepository(journalId);
    }

    @Override
    public void saveJournal(@NonNull Journal journal) {
        checkNotNull(journal);
        mJournalLocalDataSource.saveJournal(journal);

        // Do in memory cache update to keep the app UI up to date
        if (mCachedJournals == null) {
            mCachedJournals = new LinkedHashMap<>();
        }
        if (mCachedJournals.get(journal.getStringJournalOwnId())==null){
            mCachedJournals.put(journal.getStringJournalOwnId(),new LinkedHashMap<>());
        }
        mCachedJournals.get(journal.getStringJournalOwnId()).put(journal.getStringId(), journal);
    }

    @Override
    public void refreshJournals() {
        //null
    }

    @Override
    public void deleteAllJournals() {
        mJournalLocalDataSource.deleteAllJournals();

        if (mCachedJournals == null) {
            mCachedJournals = new LinkedHashMap<>();
        }
        mCachedJournals.clear();
    }

    @Override
    public void deleteJournal(@NonNull String journalId) {
        mJournalLocalDataSource.deleteJournal(checkNotNull(journalId));
        Journal journal=getJournalWithId(journalId);
        mCachedJournals.get(journal.getStringJournalOwnId()).remove(journalId);
    }

    private Observable<List<Journal>> getAndCacheLocalJournals() {
        return mJournalLocalDataSource.getAllJournals()
                .flatMap(new Function<List<Journal>, ObservableSource<List<Journal>>>() {
                    @Override
                    public ObservableSource<List<Journal>> apply(List<Journal> journals) throws Exception {
                        return Observable.fromIterable(journals)
                                .doOnNext(journal -> dataToCache(journal))
                                .toList()
                                .toObservable();
                    }

                });
    }

    @Nullable
    private Journal getJournalWithId(@NonNull String id) {
        checkNotNull(id);
        if (mCachedJournals == null || mCachedJournals.isEmpty()) {
            return null;
        } else {
            for (Map<String, Journal> value : mCachedJournals.values()) {
                if (value.containsKey(id))
                    return value.get(id);
            }
        }
        return null;
    }

    @NonNull
    private Observable<Journal> getJournalWithIdFromLocalRepository(@NonNull final String journalId) {
        return mJournalLocalDataSource
                .getJournal(journalId)
                .doOnNext(journal -> mCachedJournals.get(journal.getStringJournalOwnId()).put(journalId, journal))
                .firstElement()
                .toObservable();
    }

    private Observable<List<Journal>> getJournalWithOwnDiaryIdFromLocalRepository(@NonNull final String journalOwnId) {
        return mJournalLocalDataSource
                .getJournals(journalOwnId)
                .doOnNext(journals -> {
                    Map<String, Journal> map = mCachedJournals.get(journalOwnId);
                    if (map != null)
                        for (Journal journal : journals) {
                            map.put(journal.getStringId(), journal);
                        }
                })
                .firstElement()
                .toObservable();
    }

    private Collection<Journal> getTotalJournal() {
        Collection<Journal> coll = null;
        for (Map<String, Journal> value : mCachedJournals.values()) {
            if (coll == null) {
                coll = value.values();
            } else {
                coll.addAll(value.values());
            }
        }
        return coll;
    }

    private void dataToCache(Journal journal) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mCachedJournals.putIfAbsent(journal.getStringJournalOwnId(), new LinkedHashMap<>());
        } else if (mCachedJournals.get(journal.getStringJournalOwnId()) == null) {
            mCachedJournals.put(journal.getStringJournalOwnId(), new LinkedHashMap<>());
        }
        mCachedJournals.get(journal.getStringJournalOwnId()).put(journal.getId().toString(), journal);
    }
}
