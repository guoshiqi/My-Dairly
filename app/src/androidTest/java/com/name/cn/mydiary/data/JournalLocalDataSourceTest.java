package com.name.cn.mydiary.data;

import android.support.test.InstrumentationRegistry;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;

import com.name.cn.mydiary.data.bookdetail.Journal;
import com.name.cn.mydiary.data.source.local.JournalLocalDataSource;
import com.name.cn.mydiary.util.schedulers.BaseSchedulerProvider;
import com.name.cn.mydiary.util.schedulers.ImmediateSchedulerProvider;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;


import io.reactivex.observers.TestObserver;
import io.reactivex.subscribers.TestSubscriber;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

/**
 * 数据库测试
 * Created by guoshiqi on 2016/12/15.
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class JournalLocalDataSourceTest {

    private final static String TITLE = "title";

    private final static String TITLE2 = "title2";

    private final static String TITLE3 = "title3";

    private JournalLocalDataSource mLocalDataSource;

    @Before
    public void setup() {
        JournalLocalDataSource.destroyInstance();
        BaseSchedulerProvider mSchedulerProvider = new ImmediateSchedulerProvider();

        mLocalDataSource = JournalLocalDataSource.getInstance(
                InstrumentationRegistry.getTargetContext(), mSchedulerProvider);
    }

    @After
    public void cleanUp() {
        mLocalDataSource.deleteAllJournals();
    }

    @Test
    public void testPreConditions() {
        assertNotNull(mLocalDataSource);
    }

    @Test
    public void saveJournal_retrievesTask() {
        // Given a new journal
        final Journal newJournal = new Journal(null, 1L, TITLE);

        // When saved into the persistent repository
        mLocalDataSource.saveJournal(newJournal);

        // Then the task can be retrieved from the persistent repository
        TestObserver<Journal> testSubscriber = new TestObserver<Journal>();
        mLocalDataSource.getJournal(newJournal.getStringId()).subscribe(testSubscriber);
        testSubscriber.assertValues(newJournal);
    }

    @Test
    public void deleteAllJournals_emptyListOfRetrievedTask() {
        // Given a new task in the persistent repository and a mocked callback
        Journal newJournal = new Journal(null, 1L, TITLE);
        mLocalDataSource.saveJournal(newJournal);


        // When all tasks are deleted
        mLocalDataSource.deleteAllJournals();

        // Then the retrieved tasks is an empty list
        TestObserver<List<Journal>> testSubscriber = new TestObserver<List<Journal>>();
        mLocalDataSource.getAllJournals().subscribe(testSubscriber);
        List<Journal> result = testSubscriber.values().get(0);
        assertThat(result.isEmpty(), is(true));

    }

    @Test
    public void getTasks_retrieveSavedTasks() {
        // Given 2 new tasks in the persistent repository
        final Journal newJournal1 = new Journal(null, 1L, TITLE);
        mLocalDataSource.saveJournal(newJournal1);
        final Journal newJournal2 = new Journal(null, 2L, TITLE);
        mLocalDataSource.saveJournal(newJournal2);


        // Then the tasks can be retrieved from the persistent repository
        TestObserver<List<Journal>> testSubscriber = new TestObserver<List<Journal>>();
        mLocalDataSource.getAllJournals().subscribe(testSubscriber);
        List<Journal> result = testSubscriber.values().get(0);
        assertThat(result, hasItems(newJournal1, newJournal2));

    }

    @Test
    public void getTask_whenTaskNotSaved() {
        //Given that no task has been saved
        //When querying for a task, null is returned.
        TestObserver<Journal> testSubscriber = new TestObserver<Journal>();
        mLocalDataSource.getJournal("1").subscribe(testSubscriber);
        testSubscriber.assertNoValues();
    }

    @Test
    public void getJournals_WithOwnBookId() {
        //插入2种bookid的对象
        final Journal newJournal1 = new Journal(null, 1L, TITLE);
        mLocalDataSource.saveJournal(newJournal1);
        final Journal newJournal2 = new Journal(null, 2L, TITLE);
        mLocalDataSource.saveJournal(newJournal2);
        final Journal newJournal3 = new Journal(null, 2L, TITLE);
        mLocalDataSource.saveJournal(newJournal3);


        //获取其中一种bookid的list
        TestObserver<List<Journal>> testSubscriber = new TestObserver<List<Journal>>();
        mLocalDataSource.getJournals("2").subscribe(testSubscriber);
        testSubscriber.assertComplete();
        testSubscriber.assertNoErrors();
        List<Journal> result = testSubscriber.values().get(0);
        assertThat(result, not(hasItems(newJournal1)));
    }
}
