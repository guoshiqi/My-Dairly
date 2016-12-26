/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.name.cn.mydiary;

import android.content.Context;
import android.support.annotation.NonNull;

import com.name.cn.mydiary.data.source.BooksRepository;
import com.name.cn.mydiary.data.source.JournalDataSource;
import com.name.cn.mydiary.data.source.JournalsRepository;
import com.name.cn.mydiary.data.source.UserRepository;
import com.name.cn.mydiary.data.source.local.BookLocalDataSource;
import com.name.cn.mydiary.data.source.local.JournalLocalDataSource;
import com.name.cn.mydiary.data.source.local.UserLocalDataSource;
import com.name.cn.mydiary.util.schedulers.BaseSchedulerProvider;
import com.name.cn.mydiary.util.schedulers.SchedulerProvider;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Enables injection of mock implementations for
 * {@link JournalDataSource} at compile time. This is useful for testing, since it allows us to use
 * a fake instance of the class to isolate the dependencies and run a test hermetically.
 */
public class Injection {

    public static JournalsRepository provideTasksRepository(@NonNull Context context) {
        checkNotNull(context);
        return JournalsRepository.getInstance(JournalLocalDataSource.getInstance(context, provideSchedulerProvider()));
    }

    public static BaseSchedulerProvider provideSchedulerProvider() {
        return SchedulerProvider.getInstance();
    }

    public static BooksRepository provideBooksRepository(@NonNull Context context) {
        checkNotNull(context);
        return BooksRepository.getInstance(BookLocalDataSource.getInstance(context, provideSchedulerProvider()));
    }

    public static UserRepository provideUserRepository(@NonNull Context context) {
        checkNotNull(context);
        return UserRepository.getInstance(UserLocalDataSource.getInstance(context, provideSchedulerProvider()));
    }

}
