package com.name.cn.mydiary.function.splash;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;

import com.eftimoff.androipathview.PathView;
import com.name.cn.mydiary.R;
import com.name.cn.mydiary.data.User;
import com.name.cn.mydiary.framework.AppConstants;
import com.name.cn.mydiary.framework.BaseFragment;
import com.name.cn.mydiary.framework.DiaryApplication;
import com.name.cn.mydiary.util.PhotoPickUtils;
import com.name.cn.mydiary.util.ScreenUtils;

import java.io.IOException;

import io.reactivex.CompletableEmitter;


/**
 * 启动页fragment
 * Created by Administrator on 2016-12-25.
 */

public class SplashFragment extends BaseFragment implements SplashContract.View {

    private SplashContract.Presenter mPresenter;

    private ImageView startView, headView;

    private PathView pathView;

    private View root;

    private PhotoPickUtils photoPickUtils;
    private int sex = User.MALE;
    private String name, url;

//    private AnimatorSet animatorColorSet;


    public static SplashFragment newInstance() {
        return new SplashFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter.subscribe();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
        mPresenter.unSubscribe();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_splash_layout, container, false);
        //setupView
        startView = (ImageView) root.findViewById(R.id.splash_overlay_image);

        pathView = (PathView) root.findViewById(R.id.pathView);
        return root;
    }


    @Override
    public void showSplashPage(boolean isNeedUpdate) {

    }

    @Override
    public void showGuide() {
        goGuideStepOne();
    }

    @Override
    public void gotoHomePage(User user) {
        getActivity().getIntent().putExtra(AppConstants.PASS_JSON, user.toJson().toString());
        getActivity().setResult(Activity.RESULT_OK, getActivity().getIntent());
        getActivity().finish();
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void setPresenter(@NonNull SplashContract.Presenter presenter) {
        mPresenter = presenter;
    }


    @Override
    public void runPathAnim(CompletableEmitter emitter) {
        pathView.getPathAnimator()
                .delay(200)
                .duration(1000)
                .interpolator(new AccelerateDecelerateInterpolator())
                .listenerEnd(emitter::onComplete)
                .start();
        pathView.useNaturalColors();
        pathView.setFillAfter(true);
    }

    @Override
    public void runBackGroundAnim(CompletableEmitter emitter) {
        ObjectAnimator backGroundAnimator =
                (ObjectAnimator) AnimatorInflater.loadAnimator(getContext(), R.animator.color_animtor);
        backGroundAnimator.setEvaluator(new ArgbEvaluator());
        backGroundAnimator.setTarget(pathView);
        backGroundAnimator.setStartDelay(1000);
        backGroundAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                emitter.onComplete();
            }
        });
        backGroundAnimator.start();
    }

    @Override
    public void runCircleAnim(CompletableEmitter emitter) {
        ViewStub guideView = (ViewStub) root.findViewById(R.id.guide_layout);
        if (guideView != null)
            guideView.inflate();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            int[] screenPix = ScreenUtils.getScreen(getContext());
            Animator animator = ViewAnimationUtils.createCircularReveal(pathView, screenPix[0] / 2, screenPix[1] / 2, screenPix[0], 0f);
            animator.setDuration(1000)
                    .addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            pathView.setVisibility(View.GONE);
                            emitter.onComplete();
                        }
                    });
            animator.setStartDelay(1200);
            animator.start();
        }
    }

    /**
     * 先缩小后放大
     */
    @Override
    public void runScaleAnim(CompletableEmitter emitter) {
        ObjectAnimator alpha = ObjectAnimator.ofFloat(getView(), "alpha", 1f, 0f);
        alpha.setStartDelay(500);
        alpha.setDuration(100);//设置动画时间
        startView.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.splash_init_path));
        AnimatorSet scaleViewAnimator =
                (AnimatorSet) AnimatorInflater.loadAnimator(getContext(), R.animator.scale_animtor);
        scaleViewAnimator.setTarget(pathView);
        scaleViewAnimator.setStartDelay(500);
        scaleViewAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                emitter.onComplete();
            }
        });
        scaleViewAnimator.playTogether(alpha);
        scaleViewAnimator.start();
    }

    @Override
    public void runAlphaAnim(CompletableEmitter emitter) {
        ObjectAnimator alpha = ObjectAnimator.ofFloat(getView(), "alpha", 1f, 0f);
        alpha.setStartDelay(500);
        alpha.setDuration(600);//设置动画时间
        alpha.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                emitter.onComplete();
            }
        });
        alpha.start();//启动动画。
    }

    private void goGuideStepOne() {
        AppCompatTextView title = (AppCompatTextView) root.findViewById(R.id.appCompatTextView);
        AppCompatButton buttonLeft = (AppCompatButton) root.findViewById(R.id.guide_left);
        AppCompatButton buttonRight = (AppCompatButton) root.findViewById(R.id.guide_right);
        AppCompatTextView skip = (AppCompatTextView) root.findViewById(R.id.skip_btn);
        title.setText(getString(R.string.guide_step1_question));
        buttonLeft.setOnClickListener(v -> {
            sex = User.MALE;
            goGuideStepTwo();
        });
        buttonRight.setOnClickListener(v -> {
            sex = User.FEMALE;
            goGuideStepTwo();
        });
        skip.setOnClickListener(v -> mPresenter.saveUser(sex, name, url));
    }

    private void goGuideStepTwo() {
        AppCompatButton buttonLeft = (AppCompatButton) root.findViewById(R.id.guide_left);
        AppCompatButton buttonRight = (AppCompatButton) root.findViewById(R.id.guide_right);
        AppCompatEditText editText = (AppCompatEditText) root.findViewById(R.id.edit_name);
        AppCompatTextView title = (AppCompatTextView) root.findViewById(R.id.appCompatTextView);
        AppCompatButton confirm = (AppCompatButton) root.findViewById(R.id.guide_confirm);
        headView = (ImageView) root.findViewById(R.id.head_icon);
        buttonLeft.setVisibility(View.GONE);
        buttonRight.setVisibility(View.GONE);
        confirm.setVisibility(View.VISIBLE);
        editText.setVisibility(View.VISIBLE);
        headView.setVisibility(View.VISIBLE);
        title.setText(getString(R.string.guide_step2_question));
        headView.setOnClickListener(v -> {
            if (photoPickUtils == null)
                photoPickUtils = new PhotoPickUtils(getActivity(), "photo", null);
            photoPickUtils.showPhotoSelect();
        });
        confirm.setOnClickListener(v -> {
            name = editText.getText().toString().trim();
            mPresenter.saveUser(sex, name, url);
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (photoPickUtils != null)
            photoPickUtils.onActivityResultWithClip(requestCode, resultCode, data, uri -> {
                try {
                    url = uri.getPath();
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
                    headView.setImageBitmap(bitmap);
                } catch (IOException e) {
                    DiaryApplication.getInstance().showToast("未找到图片");
                }
            });
    }


}
