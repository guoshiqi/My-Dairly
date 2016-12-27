package com.name.cn.mydiary.function.home;

import android.support.annotation.NonNull;

import com.name.cn.mydiary.data.bookdetail.Book;
import com.name.cn.mydiary.data.source.BookDataSource;
import com.name.cn.mydiary.util.EspressoIdlingResource;
import com.name.cn.mydiary.util.schedulers.BaseSchedulerProvider;

import java.util.List;

import rx.Observable;
import rx.Subscription;
import rx.functions.Func1;
import rx.subscriptions.CompositeSubscription;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * mvp
 * Created by Administrator on 2016-12-12.
 */

public class HomePresenter implements HomeContract.Presenter {

    @NonNull
    private final BookDataSource mBooksRepository;

    @NonNull
    private final HomeContract.View mBooksView;

    @NonNull
    private final BaseSchedulerProvider mSchedulerProvider;

    @NonNull
    private HomeFilterType mCurrentFiltering = HomeFilterType.ALL_BOOKS;

    @NonNull
    private Long mBookListId;

    private boolean mFirstLoad = true;


    private boolean mIsDataMissing;


    public HomePresenter(@NonNull Long bookListId,
                         @NonNull BookDataSource booksRepository,
                         @NonNull HomeContract.View homeView,
                         @NonNull BaseSchedulerProvider schedulerProvider) {
        this.mBookListId=checkNotNull(bookListId,"bookListId cannot be null");
        this.mBooksRepository = checkNotNull(booksRepository, "booksRepository cannot be null");
        this.mBooksView = checkNotNull(homeView, "homeView cannot be null");
        this.mSchedulerProvider = checkNotNull(schedulerProvider, "schedulerProvider cannot be null");

        mSubscriptions = new CompositeSubscription();
        mBooksView.setPresenter(this);
    }

    @Override
    public void subscribe() {
        loadBooks(false);
    }

    @Override
    public void unSubscribe() {
        mSubscriptions.clear();
    }

    @NonNull
    private CompositeSubscription mSubscriptions;


    @Override
    public void result(int requestCode, int resultCode) {

    }

    @Override
    public void addNewBook() {
        mBooksView.showAddBookView();
    }

    @Override
    public void saveBook(String title) {
        createBook(title);
    }

    @Override
    public void setFiltering(HomeFilterType requestType) {

    }

    @Override
    public void loadBooks(boolean forceUpdate) {
        loadBooks(forceUpdate || mFirstLoad, true);
        mFirstLoad = false;
    }

    /**
     * @param forceUpdate   Pass in true to refresh the data in the {@link BookDataSource}
     * @param showLoadingUI Pass in true to display a loading icon in the UI
     */
    private void loadBooks(final boolean forceUpdate, final boolean showLoadingUI) {
        if (showLoadingUI) {
            mBooksView.setLoadingIndicator(true);
        }


        // The network request might be handled in a different thread so make sure Espresso knows
        // that the app is busy until the response is handled.
        EspressoIdlingResource.increment(); // App is busy until further notice

        mSubscriptions.clear();
        Subscription subscription = mBooksRepository
                .getAllBooks(mBookListId)
                .flatMap(new Func1<List<Book>, Observable<Book>>() {
                    @Override
                    public Observable<Book> call(List<Book> books) {
                        return Observable.from(books);
                    }
                })
                .filter(book -> {
                    switch (mCurrentFiltering) {
                        case ALL_BOOKS:
                        default:
                            return true;
                    }
                })
                .toList()
                .subscribeOn(mSchedulerProvider.computation())
                .observeOn(mSchedulerProvider.ui())
                .doOnTerminate(() -> {
                    if (!EspressoIdlingResource.getIdlingResource().isIdleNow()) {
                        EspressoIdlingResource.decrement(); // Set app as idle.
                    }
                })
                .subscribe(
                        // onNext
                        this::processBooks,
                        // onError
                        throwable -> mBooksView.showEmptyDiaryError(),
                        // onCompleted
                        () -> mBooksView.setLoadingIndicator(false));
        mSubscriptions.add(subscription);
    }

    private void processBooks(@NonNull List<Book> books) {
        if (books.isEmpty()) {
            // Show a message indicating there are no tasks for that filter type.
//            processEmptyTasks();
        } else {
            // Show the list of tasks
            mBooksView.showBooks(books);
            // Set the filter label's text.
            showFilterLabel();
        }
    }

    private void showFilterLabel() {
        switch (mCurrentFiltering) {
//            case ACTIVE_TASKS:
//                mTasksView.showActiveFilterLabel();
//                break;
//            case COMPLETED_TASKS:
//                mTasksView.showCompletedFilterLabel();
//                break;
//            default:
//                mTasksView.showAllFilterLabel();
//                break;
        }
    }

    private void createBook(String title) {
        Book newBook = new Book(null, mBookListId, Book.BOOK_DIARY, title, "");
        if (newBook.isEmpty()) {

        } else {
            mBooksRepository.saveBook(newBook);
            loadBooks(true, true);
        }
    }
}
