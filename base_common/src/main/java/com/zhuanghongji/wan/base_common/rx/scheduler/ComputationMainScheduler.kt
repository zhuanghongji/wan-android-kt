package com.zhuanghongji.wan.base_common.rx.scheduler

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * 从 "计算线程" 切换到 "Android 主线程"
 */
class ComputationMainScheduler<T> private constructor()
    : BaseScheduler<T>(Schedulers.computation(), AndroidSchedulers.mainThread())