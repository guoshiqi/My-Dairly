package com.name.cn.mydiary.function.splash;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.name.cn.mydiary.Injection;
import com.name.cn.mydiary.R;
import com.name.cn.mydiary.framework.BaseActivity;
import com.name.cn.mydiary.util.ActivityUtils;

/**
 * 启动引导页面(1，加载启动图片 2，获取用户(新用户开始引导fragment，否则进行下一步) 3，获取用户个性化配置 ）)
 * Created by guoshiqi on 2016/12/22.
 */

public class SplashActivity extends BaseActivity {

    private SplashPresenter mSplashPresenter;

    private SplashFragment splashFragment;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (splashFragment != null)
            splashFragment.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_layout);


        splashFragment =
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

    @Override
    public void onBackPressed() {

    }
}
