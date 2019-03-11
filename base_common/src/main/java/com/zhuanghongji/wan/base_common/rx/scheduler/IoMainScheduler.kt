package com.zhuanghongji.wan.base_common.rx.scheduler

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * 从 "IO 线程" 切换到 "Android 主线程"
 */
class IoMainScheduler<T>
    : BaseScheduler<T>(Schedulers.io(), AndroidSchedulers.mainThread())