package com.name.cn.mydiary.data.source;

import android.content.Context;

import com.google.common.collect.Lists;
import com.name.cn.mydiary.data.bookdetail.Journal;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

;

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * 测试cache
 * Created by guoshiqi on 2016/12/15.
 */

public class JournalsRepositoryTest {

    private final static String JOURNAL_TITLE = "title";

    private final static String JOURNAL_TITLE2 = "title2";

    private final static String JOURNAL_TITLE3 = "title3";

    private static List<Journal> JOURNALS = Lists.newArrayList(new Journal(1L, 2L, "as"),
            new Journal(2L, 2L, "asd"));

    private static List<Journal> JOURNALSD = Lists.newArrayList(new Journal(2L, 2L, "asd"));

    private TestObserver<List<Journal>> mJournalsTestSubscriber;

    private JournalsRepository mJournalsRepository;
    @Mock
    private JournalDataSource mJournalsLocalDataSource;

    @Mock
    private Context mContext;

    @Before
    public void setupTasksRepository() {
        // Mockito has a very convenient way to inject mocks by using the @Mock annotation. To
        // inject the mocks in the test the initMocks method needs to be called.
        MockitoAnnotations.initMocks(this);

        // Get a reference to the class under test
        mJournalsRepository = JournalsRepository.getInstance(mJournalsLocalDataSource);

        mJournalsTestSubscriber = new TestObserver<>();
    }

    @After
    public void destroyRepositoryInstance() {
        JournalsRepository.destroyInstance();
    }


    @Test
    public void getTasks_requestsAllTasksFromLocalDataSource() {
        // Given that the local data source has data available
        setJournalsAvailable(mJournalsLocalDataSource, JOURNALS);

        // When tasks are requested from the tasks repository
        mJournalsRepository.getAllJournals().subscribe(mJournalsTestSubscriber);

        // Then tasks are loaded from the local data source
        verify(mJournalsLocalDataSource).getAllJournals();
        mJournalsTestSubscriber.assertValue(JOURNALS);
    }

    @Test
    public void deleteJournal() {
        //save two;
        Journal newJournal1 = new Journal(1L, 2L, "as");
        Journal newJournal2 = new Journal(2L, 2L, "asd");
        mJournalsRepository.saveJournal(newJournal1);
        mJournalsRepository.saveJournal(newJournal2);

        //delete one
        mJournalsRepository.deleteJournal("2");

        //verify
        verify(mJournalsLocalDataSource).deleteJournal("2");

        assertThat(mJournalsRepository.mCachedJournals.get("2").size(),is(0) );
    }

    private void setJournalsNotAvailable(JournalDataSource dataSource) {
        when(dataSource.getAllJournals()).thenReturn(Observable.just(Collections.<Journal>emptyList()));
    }

    private void setJournalsAvailable(JournalDataSource dataSource, List<Journal> tasks) {
        // don't allow the data sources to complete.
        when(dataSource.getAllJournals()).thenReturn(Observable.just(tasks).concatWith(Observable.<List<Journal>>never()));
    }

    private void setJournalNotAvailable(JournalDataSource dataSource, String journalId) {
        when(dataSource.getJournal(eq(journalId))).thenReturn(Observable.<Journal>just(null).concatWith(Observable.<Journal>never()));
    }

    private void setJournalAvailable(JournalDataSource dataSource, Journal journal) {
        when(dataSource.getJournal(eq(journal.getStringId()))).thenReturn(Observable.just(journal).concatWith(Observable.<Journal>never()));
    }

}
