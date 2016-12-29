package com.name.cn.mydiary.function.splash;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.transition.Explode;
import android.transition.Fade;
import android.view.Window;

import com.name.cn.mydiary.Injection;
import com.name.cn.mydiary.R;
import com.name.cn.mydiary.framework.AppConstants;
import com.name.cn.mydiary.framework.BaseActivity;
import com.name.cn.mydiary.util.ActivityUtils;
import com.name.cn.mydiary.util.SPUtils;

/**
 * 启动引导页面(1，加载启动图片 2，获取用户(新用户开始引导fragment，否则进行下一步) 3，获取用户个性化配置 ）)
 * Created by guoshiqi on 2016/12/22.
 */

public class SplashActivity extends BaseActivity {

    private SplashPresenter mSplashPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_layout);


        SplashFragment splashFragment =
                (SplashFragment) getSupportFragmentManager().findFragmentById(R.id.splash_fragment);
        if (splashFragment == null) {
            // Create the fragment
            splashFragment = SplashFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), splashFragment, R.id.splash_fragment);
        }

        // Create the presenter
        mSplashPresenter = new SplashPresenter(
                Injection.provideUserRepository(getApplicationContext()),
                splashFragment,
                Injection.provideSchedulerProvider());

    }
}
