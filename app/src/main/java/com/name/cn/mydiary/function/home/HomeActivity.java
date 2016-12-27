package com.name.cn.mydiary.function.home;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import com.name.cn.mydiary.Injection;
import com.name.cn.mydiary.R;
import com.name.cn.mydiary.framework.AppConstants;
import com.name.cn.mydiary.framework.BaseActivity;
import com.name.cn.mydiary.util.ActivityUtils;

import org.json.JSONException;
import org.json.JSONObject;

public class HomeActivity extends BaseActivity {
    private static final String CURRENT_FILTERING_KEY = "CURRENT_FILTERING_KEY";

    private HomePresenter mHomePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //getData
        Long bookListId = AppConstants.DEFAULT_LONG;
        try {
            JSONObject object = new JSONObject(getIntent().getStringExtra(AppConstants.PASS_JSON));
            bookListId = object.optLong(HomeFragment.ARGUMENT_SHOW_BOOK_LIST_ID, AppConstants.DEFAULT_LONG);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Set up the toolbar.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        HomeFragment homeFragment =
                (HomeFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (homeFragment == null) {
            // Create the fragment
            homeFragment = HomeFragment.newInstance(bookListId);
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), homeFragment, R.id.contentFrame);
        }

        // Create the presenter
        mHomePresenter = new HomePresenter(bookListId,
                Injection.provideBooksRepository(getApplicationContext()),
                homeFragment,
                Injection.provideSchedulerProvider());

        // Load previously saved state, if available.
        if (savedInstanceState != null) {
            HomeFilterType currentFiltering =
                    (HomeFilterType) savedInstanceState.getSerializable(CURRENT_FILTERING_KEY);
            mHomePresenter.setFiltering(currentFiltering);
        }

    }
}
