package com.zhuanghongji.wan.base_common.rx.scheduler

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * 从 "Trampoline" 切换到 "Android 主线程"
 */
class TrampolineMainScheduler<T> private constructor()
    : BaseScheduler<T>(Schedulers.trampoline(), AndroidSchedulers.mainThread())