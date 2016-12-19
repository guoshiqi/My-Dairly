package com.name.cn.mydiary.function.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.name.cn.mydiary.R;
import com.name.cn.mydiary.data.bookdetail.Book;
import com.name.cn.mydiary.widget.ScrollChildSwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * 首页
 * Created by Administrator on 2016-12-12.
 */

public class HomeFragment extends Fragment implements HomeContract.View {

    private HomeContract.Presenter mPresenter;

    private BooksAdapter mBooksAdapter;

    private BookItemListener mItemListener;


    public static HomeFragment newInstance() {
        return new HomeFragment();
    }


    public HomeFragment() {

    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBooksAdapter = new BooksAdapter(new ArrayList<>(0), mItemListener);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.subscribe();
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.unSubscribe();
    }


    @Override
    public void setPresenter(@NonNull HomeContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mPresenter.result(requestCode, resultCode);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home_layout, container, false);
        //setupView
        RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.books_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(mBooksAdapter);

        // Set up  no tasks view

        // Set up progress indicator
        final ScrollChildSwipeRefreshLayout swipeRefreshLayout =
                (ScrollChildSwipeRefreshLayout) root.findViewById(R.id.refresh_layout);
        swipeRefreshLayout.setColorSchemeColors(
                ContextCompat.getColor(getActivity(), R.color.colorPrimary),
                ContextCompat.getColor(getActivity(), R.color.colorAccent),
                ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark)
        );
        // Set the scrolling view in the custom SwipeRefreshLayout.
        swipeRefreshLayout.setScrollUpChild(recyclerView);

        swipeRefreshLayout.setOnRefreshListener(() -> mPresenter.loadBooks(false));

        return root;
    }

    @Override
    public void showEmptyDiaryError() {

    }

    @Override
    public void showBooksList() {

    }

    private static class BooksAdapter extends RecyclerView.Adapter {
        private List<Book> mBooks;
        private BookItemListener mItemListener;

        public BooksAdapter(List<Book> books, BookItemListener itemListener) {
            setList(books);
            mItemListener = itemListener;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return null;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        }


        @Override
        public int getItemCount() {
            return 0;
        }

        public void setList(List<Book> books) {
            mBooks = checkNotNull(books);
        }
    }

    public interface BookItemListener {

        void onBookClick(Book clickedTask);

    }
}
