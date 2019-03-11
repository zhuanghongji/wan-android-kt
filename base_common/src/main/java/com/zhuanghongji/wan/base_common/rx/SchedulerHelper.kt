package com.zhuanghongji.wan.base_common.rx

import com.zhuanghongji.wan.base_common.rx.scheduler.IoMainScheduler

object SchedulerHelper {

    fun <T> ioToMain(): IoMainScheduler<T> {
        return IoMainScheduler()
    }
}