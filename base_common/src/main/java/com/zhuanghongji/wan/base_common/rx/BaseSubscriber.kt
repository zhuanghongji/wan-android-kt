package com.zhuanghongji.wan.base_common.rx

import com.zhuanghongji.wan.base_common.api.BaseResult
import io.reactivex.subscribers.ResourceSubscriber

abstract class BaseSubscriber<T: BaseResult>: ResourceSubscriber<T>() {

    abstract fun onSuccess(t: T)

    override fun onStart() {
        super.onStart()
    }

    override fun onNext(t: T) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onError(t: Throwable?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onComplete() {

    }
}