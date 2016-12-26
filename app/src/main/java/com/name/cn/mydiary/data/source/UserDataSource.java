package com.name.cn.mydiary.data.source;

import com.name.cn.mydiary.data.Config;
import com.name.cn.mydiary.data.User;

import rx.Observable;


/**
 * user and config
 * Created by guoshiqi on 2016/12/26.
 */

public interface UserDataSource {

    Observable<User> getUser(Long Id);

    Observable<Config> getConfig(Long Id);

    void saveUser(User user);

    void saveConfig(Config config);

    void deleteAllUser();

    void deleteAllConfig();

    void deleteUser(Long Id);

    void deleteConfig(Long Id);
}
