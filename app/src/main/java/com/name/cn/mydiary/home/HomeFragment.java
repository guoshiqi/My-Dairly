package com.name.cn.mydiary.home;

import android.support.v4.app.Fragment;

/**
 * 首页
 * Created by Administrator on 2016-12-12.
 */

public class HomeFragment extends Fragment implements HomeContract.View{

    public static HomeFragment newInstance(){
        return new HomeFragment();
    }



    @Override
    public void showEmptyDiaryError() {

    }

    @Override
    public void showBooksList() {

    }

    @Override
    public void setPresenter(HomeContract.Presenter presenter) {

    }
}
