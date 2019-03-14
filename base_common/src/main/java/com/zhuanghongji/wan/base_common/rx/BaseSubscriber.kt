package com.zhuanghongji.wan.base_common.rx

import com.zhuanghongji.wan.base_common.api.BaseResult
import io.reactivex.subscribers.ResourceSubscriber

abstract class BaseSubscriber<T: BaseResult>: ResourceSubscriber<T>() {

    abstract fun onSuccess(t: T)

    override fun onStart() {
        super.onStart()
    }

    override fun onNext(t: T) {

    }

    override fun onError(t: Throwable?) {

    }

    override fun onComplete() {

    }
}