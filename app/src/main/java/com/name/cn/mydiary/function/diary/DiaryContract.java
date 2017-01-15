package com.name.cn.mydiary.function.diary;

import com.name.cn.mydiary.framework.BasePresenter;
import com.name.cn.mydiary.framework.BaseView;

/**
 * contract for 3 fragment and  1 activity
 * Created by guoshiqi on 2017/1/3.
 */

public interface DiaryContract {

    interface DiaryListView extends BaseView<Presenter> {

    }

    interface CalendarView extends BaseView<Presenter> {

    }

    interface AddDiaryView extends BaseView<Presenter> {

    }

    interface Presenter extends BasePresenter{

    }
}
