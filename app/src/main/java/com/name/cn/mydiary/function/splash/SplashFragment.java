package com.name.cn.mydiary.function.splash;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.ImageView;

import com.name.cn.mydiary.R;
import com.name.cn.mydiary.framework.BaseFragment;
import com.name.cn.mydiary.util.IntentUtils;

/**
 * 启动页fragment
 * Created by Administrator on 2016-12-25.
 */

public class SplashFragment extends BaseFragment implements SplashContract.View {

    private SplashContract.Presenter mPresenter;


    public static SplashFragment newInstance() {
        return new SplashFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.subscribe();
    }

    @Override
    public void onStop() {
        super.onStop();
        mPresenter.unSubscribe();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_splash_layout, container, false);
        //setupView
        ImageView startView = (ImageView) root.findViewById(R.id.splash_fragment_image);
        ViewStub guideView = (ViewStub) root.findViewById(R.id.guide_layout);

        return root;
    }


    @Override
    public void showSplashPage(boolean isNeedUpdate) {

    }

    @Override
    public void showGuide() {

    }

    @Override
    public void gotoHomePage() {
        IntentUtils.SplashToHomeActivity(this, getContext());
        getActivity().finish();
    }

    @Override
    public void setPresenter(@NonNull SplashContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
