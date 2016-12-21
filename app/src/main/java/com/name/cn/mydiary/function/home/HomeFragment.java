package com.name.cn.mydiary.function.home;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
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
        mBooksAdapter = new BooksAdapter(R.layout.item_home_books_layout, new ArrayList<>());
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

        // Set up floating action button
        FloatingActionButton fab =
                (FloatingActionButton) getActivity().findViewById(R.id.fab_add_book);

        fab.setImageResource(R.drawable.ic_add);
        fab.setOnClickListener(__ -> mPresenter.addNewBook());

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
    public void showAddBookView() {
        CustomDialogInputView inputView = new CustomDialogInputView(getContext());
        new AlertDialog.Builder(getContext()).setTitle(R.string.dialog_title).setView(inputView)
                .setPositiveButton(R.string.dialog_positive, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mPresenter.saveBook(inputView.getInput());
                        dialog.dismiss();
                    }
                })
                .setNegativeButton(R.string.dialog_negative, null)
                .create().show();
    }

    @Override
    public void setLoadingIndicator(boolean active) {
        if (getView() == null) {
            return;
        }
        final SwipeRefreshLayout srl =
                (SwipeRefreshLayout) getView().findViewById(R.id.refresh_layout);

        // Make sure setRefreshing() is called after the layout is done with everything else.
        srl.post(() -> srl.setRefreshing(active));
    }

    @Override
    public void showBooks(List<Book> books) {
        mBooksAdapter.setNewData(books);

        //TODO 需要改变列表状态
    }

    private static class BooksAdapter extends BaseQuickAdapter<Book, BaseViewHolder> {

        public BooksAdapter(int layoutResId, List<Book> data) {
            super(layoutResId, data);
        }

        public BooksAdapter(List<Book> data) {
            super(data);
        }

        @Override
        protected void convert(BaseViewHolder baseViewHolder, Book book) {
            baseViewHolder.setText(R.id.image_title, book.getTitle());
        }
    }

    public interface BookItemListener {

        void onBookClick(Book clickedTask);

    }
}
