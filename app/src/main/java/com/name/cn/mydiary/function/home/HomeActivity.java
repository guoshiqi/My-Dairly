package com.name.cn.mydiary.function.home;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.name.cn.mydiary.Injection;
import com.name.cn.mydiary.R;
import com.name.cn.mydiary.framework.AppConstants;
import com.name.cn.mydiary.framework.BaseActivity;
import com.name.cn.mydiary.function.splash.SplashActivity;
import com.name.cn.mydiary.util.ActivityUtils;

import org.json.JSONException;
import org.json.JSONObject;

public class HomeActivity extends BaseActivity {
    private static final String CURRENT_FILTERING_KEY = "CURRENT_FILTERING_KEY";

    private HomePresenter mHomePresenter;

    private HomeFragment mHomeFragment;

    private Long bookListId;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            try {
                JSONObject object = new JSONObject(data.getStringExtra(AppConstants.PASS_JSON));
                bookListId = object.optLong(HomeFragment.ARGUMENT_SHOW_BOOK_LIST_ID, AppConstants.DEFAULT_LONG);
                initFragmentAndPresenter();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.transparent));
            getWindow()
                    .getDecorView()
                    .setSystemUiVisibility(
                            View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        } else {
            setTranslucentStatus(true);
        }

        setContentView(R.layout.activity_main);
        // Set up the toolbar.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
        }
        mHomeFragment =
                (HomeFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        //getData
        if (savedInstanceState != null) {
            bookListId = savedInstanceState.getLong(HomeFragment.ARGUMENT_SHOW_BOOK_LIST_ID, AppConstants.DEFAULT_LONG);
            initFragmentAndPresenter();
            // Load previously saved state, if available.
            HomeFilterType currentFiltering =
                    (HomeFilterType) savedInstanceState.getSerializable(CURRENT_FILTERING_KEY);
            mHomePresenter.setFiltering(currentFiltering);
        }
        Intent intent = new Intent(this, SplashActivity.class);
        startActivityForResult(intent, AppConstants.REQUEST_CODE_DEFAULT);
    }

    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (bookListId != null)
            outState.putLong(HomeFragment.ARGUMENT_SHOW_BOOK_LIST_ID, bookListId);
    }

    private void initFragmentAndPresenter() {
        if (mHomeFragment == null) {
            // Create the fragment
            mHomeFragment = HomeFragment.newInstance(bookListId);
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), mHomeFragment, R.id.contentFrame);
        }

        // Create the presenter
        mHomePresenter = new HomePresenter(bookListId,
                Injection.provideBooksRepository(getApplicationContext()),
                mHomeFragment,
                Injection.provideSchedulerProvider());
    }
}
