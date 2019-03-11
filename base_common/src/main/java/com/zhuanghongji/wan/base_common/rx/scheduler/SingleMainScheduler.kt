package com.zhuanghongji.wan.base_common.rx.scheduler

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * 从 "Single" 切换到 "Android 主线程"
 */
class SingleMainScheduler<T> private constructor():
        BaseScheduler<T>(Schedulers.single(), AndroidSchedulers.mainThread())