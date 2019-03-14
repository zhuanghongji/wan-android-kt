package com.zhuanghongji.wan.base_common.rx

import com.zhuanghongji.wan.base_common.rx.scheduler.IoMainScheduler

/**
 * RxJava 调度器辅助类
 */
object SchedulerHelper {

    fun <T> ioToMain(): IoMainScheduler<T> {
        return IoMainScheduler()
    }
}