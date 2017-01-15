package com.name.cn.mydiary.data.source;

import android.content.Context;

import com.google.common.collect.Lists;
import com.name.cn.mydiary.data.bookdetail.Book;
import com.name.cn.mydiary.data.bookdetail.Journal;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;


import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * cacheTest
 * Created by Administrator on 2016-12-18.
 */

public class BookRepositoryTest {


    private final static String JOURNAL_TITLE = "title";

    private final static String JOURNAL_TITLE2 = "title2";

    private final static String JOURNAL_TITLE3 = "title3";

    private static List<Book> Books = Lists.newArrayList(new Book(null, 1L, Book.BOOK_DIARY, "as", "as"),
            new Book(null, 1L, Book.BOOK_DIARY, "asd", "asd"));

    private static List<Book> BookD = Lists.newArrayList(new Book(null, 1L, Book.BOOK_DIARY, "as", "as"));
    private TestObserver<List<Book>> mBooksTestSubscriber;

    private BooksRepository mBooksRepository;
    @Mock
    private BookDataSource mBooksLocalDataSource;

    @Mock
    private Context mContext;

    @Before
    public void setupTasksRepository() {
        // Mockito has a very convenient way to inject mocks by using the @Mock annotation. To
        // inject the mocks in the test the initMocks method needs to be called.
        MockitoAnnotations.initMocks(this);

        // Get a reference to the class under test
        mBooksRepository = BooksRepository.getInstance(mBooksLocalDataSource);

        mBooksTestSubscriber = new TestObserver<>();
    }

    @After
    public void destroyRepositoryInstance() {
        BooksRepository.destroyInstance();
    }


    @Test
    public void getTasks_requestsAllTasksFromLocalDataSource() {
        // Given that the local data source has data available
        setBooksAvailable(mBooksLocalDataSource, Books, 1L);

        // When tasks are requested from the tasks repository
        mBooksRepository.getAllBooks(1L).subscribe(mBooksTestSubscriber);

        // Then tasks are loaded from the local data source
        verify(mBooksLocalDataSource).getAllBooks(1L);
        mBooksTestSubscriber.assertValue(Books);
    }

    @Test
    public void deleteJournal() {
        //save two;
        Book newBook1 = new Book(null, 1L, Book.BOOK_DIARY, "as", "as");
        Book newBook2 = new Book(null, 2L, Book.BOOK_DIARY, "as", "asd");
        mBooksRepository.saveBook(newBook1);
        mBooksRepository.saveBook(newBook2);

        //delete one
        mBooksRepository.deleteBooks(1L);

        //verify
        verify(mBooksLocalDataSource).deleteBooks(1L);

        assertThat(mBooksRepository.cacheMap.size(), is(1));
    }

    private void setBooksNotAvailable(BookDataSource dataSource) {
        when(dataSource.getAllBooks(eq(1L))).thenReturn(Observable.just(Collections.<Book>emptyList()));
    }

    private void setBooksAvailable(BookDataSource dataSource, List<Book> tasks, Long id) {
        // don't allow the data sources to complete.
        when(dataSource.getAllBooks(eq(id))).thenReturn(Observable.just(tasks).concatWith(Observable.<List<Book>>never()));
    }

    private void setBookNotAvailable(BookDataSource dataSource, Long bookId) {
        when(dataSource.getBook(eq(bookId))).thenReturn(Observable.<Book>just(null).concatWith(Observable.<Book>never()));
    }

    private void setBookAvailable(BookDataSource dataSource, Book book) {
        when(dataSource.getBook(eq(book.getId()))).thenReturn(Observable.just(book).concatWith(Observable.<Book>never()));
    }
}
