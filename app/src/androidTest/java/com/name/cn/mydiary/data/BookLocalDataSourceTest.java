package com.name.cn.mydiary.data;

import android.support.test.InstrumentationRegistry;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;

import com.name.cn.mydiary.data.bookdetail.Book;
import com.name.cn.mydiary.data.source.local.BookLocalDataSource;
import com.name.cn.mydiary.util.schedulers.BaseSchedulerProvider;
import com.name.cn.mydiary.util.schedulers.ImmediateSchedulerProvider;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import rx.observers.TestSubscriber;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

/**
 * bookTest
 * Created by Administrator on 2016-12-18.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class BookLocalDataSourceTest {
    private final static String TITLE = "title";

    private BookLocalDataSource mLocalDataSource;

    @Before
    public void setup() {
        BookLocalDataSource.destroyInstance();
        BaseSchedulerProvider mSchedulerProvider = new ImmediateSchedulerProvider();

        mLocalDataSource = BookLocalDataSource.getInstance(
                InstrumentationRegistry.getTargetContext(), mSchedulerProvider);
    }

    @After
    public void cleanUp() {
        mLocalDataSource.deleteAllBooks();
    }

    @Test
    public void testPreConditions() {
        assertNotNull(mLocalDataSource);
    }

    @Test
    public void saveBook() {
        // Given a new journal
        final Book newBook = new Book(null, 1L, Book.BOOK_DIARY, TITLE, TITLE);

        // When saved into the persistent repository
        mLocalDataSource.saveBook(newBook);

        // Then the task can be retrieved from the persistent repository
        TestSubscriber<Book> testSubscriber = new TestSubscriber<Book>();
        mLocalDataSource.getBook(newBook.getId()).subscribe(testSubscriber);
        testSubscriber.assertValues(newBook);
    }


    @Test
    public void deleteAllJournals_emptyListOfRetrievedTask() {
        // Given a new task in the persistent repository and a mocked callback
        final Book newBook1 = new Book(null, 1L, Book.BOOK_DIARY, TITLE, TITLE);
        mLocalDataSource.saveBook(newBook1);


        // When all tasks are deleted
        mLocalDataSource.deleteBooks(1L);

        // Then the retrieved tasks is an empty list
        TestSubscriber<List<Book>> testSubscriber = new TestSubscriber<List<Book>>();
        mLocalDataSource.getAllBooks(1L).subscribe(testSubscriber);
        List<Book> result = testSubscriber.getOnNextEvents().get(0);
        assertThat(result.isEmpty(), is(true));

    }


    @Test
    public void getTask_whenTaskNotSaved() {
        //Given that no task has been saved
        //When querying for a task, null is returned.
        TestSubscriber<Book> testSubscriber = new TestSubscriber<Book>();
        mLocalDataSource.getBook(1L).subscribe(testSubscriber);
        testSubscriber.assertValue(null);
    }
}
