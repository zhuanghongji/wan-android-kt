package com.zhuanghongji.wan.base_common.api

import org.reactivestreams.Subscriber

abstract class  ApiCallback<T>: Subscriber<Result<T>> {

    abstract fun onSuccess(data: T)

    abstract fun onFailure(code: Int, message: String)

    open fun onFinish() {
        // default do nothing
    }

    override fun onNext(result: Result<T>) {
        if (result.errorCode == 0) {
            onSuccess(result.data)
        } else {

        }
    }

    override fun onError(t: Throwable) {
        onFailure(-2, t.message ?: "网络异常，请稍后重试")
        onFinish()
    }

    override fun onComplete() {
        onFinish()
    }
}