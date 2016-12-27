package com.name.cn.mydiary.data;

import android.support.test.InstrumentationRegistry;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;

import com.name.cn.mydiary.data.source.local.BookLocalDataSource;
import com.name.cn.mydiary.data.source.local.UserLocalDataSource;
import com.name.cn.mydiary.util.schedulers.BaseSchedulerProvider;
import com.name.cn.mydiary.util.schedulers.ImmediateSchedulerProvider;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import rx.observers.TestSubscriber;

import static org.junit.Assert.assertNotNull;

/**
 * test for user and config
 * Created by guoshiqi on 2016/12/26.
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class UserLocalDataSourceTest {


    private final static String TITLE = "title";

    private UserLocalDataSource mLocalDataSource;

    @Before
    public void setup() {
        BookLocalDataSource.destroyInstance();
        BaseSchedulerProvider mSchedulerProvider = new ImmediateSchedulerProvider();

        mLocalDataSource = UserLocalDataSource.getInstance(
                InstrumentationRegistry.getTargetContext(), mSchedulerProvider);
    }

    @After
    public void cleanUp() {
        mLocalDataSource.deleteAllConfig();
        mLocalDataSource.deleteAllUser();
    }

    @Test
    public void testPreConditions() {
        assertNotNull(mLocalDataSource);
    }

    @Test
    public void saveUser() {
        //init
        final User user = new User();

        //save
        mLocalDataSource.saveUser(user);

        //check
        TestSubscriber<User> testSubscriber = new TestSubscriber<User>();
        mLocalDataSource.getUser(user.getId()).subscribe(testSubscriber);
        testSubscriber.assertValues(user);
    }

    @Test
    public void checkConfigNotNull() {
        //init
        Config config = new Config(null, "1");
        final User user = new User();

        //save
        mLocalDataSource.saveUser(user);
        mLocalDataSource.saveConfig(config);
        user.setConfigId(config.getId());

        //check
        TestSubscriber<Config> testSubscriber = new TestSubscriber<Config>();
        mLocalDataSource.getConfig(user.getConfigId()).subscribe(testSubscriber);
        testSubscriber.assertValue(user.getConfig());
    }

    @Test
    public void updateConfig() {
        //init user
        Config config = new Config(null, "1");
        final User user = new User();
        mLocalDataSource.saveConfig(config);
        mLocalDataSource.saveUser(user);

        user.setConfigId(config.getId());
        config.setBitmapId("2");
        //update config

        mLocalDataSource.saveConfig(config);
        //check
        TestSubscriber<Config> testSubscriber = new TestSubscriber<Config>();
        mLocalDataSource.getConfig(user.getConfigId()).subscribe(testSubscriber);
        testSubscriber.assertValue(config);

    }
}
