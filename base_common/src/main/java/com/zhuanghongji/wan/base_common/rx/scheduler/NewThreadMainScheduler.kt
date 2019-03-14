package com.zhuanghongji.wan.base_common.rx.scheduler

import com.zhuanghongji.wan.base_common.rx.BaseScheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * 从 "新线程" 切换到 "Android 主线程"
 */
class NewThreadMainScheduler<T> private constructor()
    : BaseScheduler<T>(Schedulers.newThread(), AndroidSchedulers.mainThread())