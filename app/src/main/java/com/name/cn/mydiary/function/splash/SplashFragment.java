package com.name.cn.mydiary.function.splash;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;

import com.eftimoff.androipathview.PathView;
import com.name.cn.mydiary.R;
import com.name.cn.mydiary.data.User;
import com.name.cn.mydiary.framework.BaseFragment;

/**
 * 启动页fragment
 * Created by Administrator on 2016-12-25.
 */

public class SplashFragment extends BaseFragment implements SplashContract.View {

    private SplashContract.Presenter mPresenter;

    private ImageView startView;

    private ViewStub guideView;

//    private AnimatorSet animatorColorSet;


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
        startView = (ImageView) root.findViewById(R.id.splash_fragment_image);
        guideView = (ViewStub) root.findViewById(R.id.guide_layout);
        PathView pathView = (PathView) root.findViewById(R.id.pathView);
        pathView.getPathAnimator()
                .interpolator(new AccelerateInterpolator())
                .delay(100)
                .duration(1500)
                .interpolator(new AccelerateDecelerateInterpolator())
                .listenerStart(() -> showAnimWithFirstEnter(startView))
                .start();
        pathView.useNaturalColors();
        pathView.setFillAfter(true);
        return root;
    }


    @Override
    public void showSplashPage(boolean isNeedUpdate) {

    }

    @Override
    public void showGuide() {
        guideView.inflate();
    }

    @Override
    public void gotoHomePage(User user) {
//        IntentUtils.SplashToHomeActivity(this, getContext(), user.toJson().toString());
//        getActivity().finish();
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void setPresenter(@NonNull SplashContract.Presenter presenter) {
        mPresenter = presenter;
    }


    private void showAnimWithFirstEnter(View backView) {
        AnimatorSet animatorColorSet = new AnimatorSet();
        ObjectAnimator objectAnimator2 =
                (ObjectAnimator) AnimatorInflater.loadAnimator(getContext(), R.animator.color_animtor);
        objectAnimator2.setEvaluator(new ArgbEvaluator());
        objectAnimator2.setTarget(backView);
        animatorColorSet.play(objectAnimator2);
        animatorColorSet.setStartDelay(1200);
        animatorColorSet.start();
    }
}
