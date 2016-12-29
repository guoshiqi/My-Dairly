package com.name.cn.mydiary.function.splash;

import android.animation.Animator;
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
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;

import com.eftimoff.androipathview.PathView;
import com.name.cn.mydiary.R;
import com.name.cn.mydiary.data.User;
import com.name.cn.mydiary.framework.BaseFragment;
import com.name.cn.mydiary.util.BaseAnimatorListenerAdapter;
import com.name.cn.mydiary.util.IntentUtils;
import com.name.cn.mydiary.util.ScreenUtils;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.functions.Action1;

/**
 * 启动页fragment
 * Created by Administrator on 2016-12-25.
 */

public class SplashFragment extends BaseFragment implements SplashContract.View {

    private SplashContract.Presenter mPresenter;

    private ImageView startView;

    private ViewStub guideView;

    private PathView pathView;

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
        startView = (ImageView) root.findViewById(R.id.splash_overlay_image);
        guideView = (ViewStub) root.findViewById(R.id.guide_layout);
        pathView = (PathView) root.findViewById(R.id.pathView);
        return root;
    }


    @Override
    public void showSplashPage(boolean isNeedUpdate) {

    }

    @Override
    public void showGuide() {
        pathView.getPathAnimator()
                .interpolator(new AccelerateInterpolator())
                .delay(100)
                .duration(1500)
                .interpolator(new AccelerateDecelerateInterpolator())
                .listenerStart(() -> showGuideFirstAnim(pathView))
                .start();
        pathView.useNaturalColors();
        pathView.setFillAfter(true);
    }

    @Override
    public void gotoHomePage(User user) {
        pathView.getPathAnimator()
                .interpolator(new AccelerateInterpolator())
                .delay(100)
                .duration(1500)
                .interpolator(new AccelerateDecelerateInterpolator())
                .listenerStart(() -> ShowAnimAndGoHome(pathView, user))
                .start();
        pathView.useNaturalColors();
        pathView.setFillAfter(true);
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void setPresenter(@NonNull SplashContract.Presenter presenter) {
        mPresenter = presenter;
    }


    //show guide Anim
    private void showGuideFirstAnim(View view) {
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator backGroundAnimator =
                (ObjectAnimator) AnimatorInflater.loadAnimator(getContext(), R.animator.color_animtor);
        backGroundAnimator.setEvaluator(new ArgbEvaluator());
        backGroundAnimator.setTarget(view);
        backGroundAnimator.addListener(new BaseAnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                guideView.inflate();
            }
        });
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            int[] screenPix = ScreenUtils.getScreen(getContext());
            Animator animator = ViewAnimationUtils.createCircularReveal(view, screenPix[0] / 2, screenPix[1] / 2, screenPix[0], 0f);
            animator.setDuration(1000);
            animator.setStartDelay(200);
            animatorSet.play(backGroundAnimator).before(animator);
        } else {
            animatorSet.play(backGroundAnimator);
        }
        animatorSet.setStartDelay(1200);
        animatorSet.start();
    }

    //go home Anim
    private void ShowAnimAndGoHome(View view, User user) {
        ObjectAnimator backGroundAnimator =
                (ObjectAnimator) AnimatorInflater.loadAnimator(getContext(), R.animator.color_animtor);
        backGroundAnimator.setEvaluator(new ArgbEvaluator());
        backGroundAnimator.setTarget(view);
        backGroundAnimator.setStartDelay(1200);
        backGroundAnimator.addListener(new BaseAnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                startView.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.splash_init_path));
                AnimatorSet scaleViewAnimator =
                        (AnimatorSet) AnimatorInflater.loadAnimator(getContext(), R.animator.scale_animtor);
                scaleViewAnimator.setTarget(view);
                scaleViewAnimator.addListener(new BaseAnimatorListenerAdapter(){
                    @Override
                    public void onAnimationStart(Animator animation) {
                        Observable.timer(1200,TimeUnit.MILLISECONDS).subscribe(
                                aLong -> {
                                    IntentUtils.SplashToHomeActivity(getContext(), user.toJson().toString());
                                    getActivity().finish();
                                }
                        );
                    }

                });
                scaleViewAnimator.setStartDelay(1000);
                scaleViewAnimator.start();
            }
        });
        backGroundAnimator.start();

    }
}
