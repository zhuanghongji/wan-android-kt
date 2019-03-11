package com.zhuanghongji.wan.base_common.rx

import android.util.Log
import com.zhuanghongji.wan.base_common.api.BaseResult
import com.zhuanghongji.wan.base_common.http.exception.ErrorStatus
import io.reactivex.observers.ResourceObserver

abstract class BaseObserver<T: BaseResult>: ResourceObserver<T>() {

    companion object {

        val TAG = "BaseObserver"
    }

    abstract fun onSuccess()

    override fun onStart() {
        super.onStart()
        Log.i(TAG, "onStart")
    }

    override fun onNext(t: T) {
        when {
            t.errorCode == ErrorStatus.SUCCESS -> onSuccess()
            else -> Log.i(TAG, "un know errcode = ${t.errorCode}")
        }
    }

    override fun onError(e: Throwable) {
        Log.i(TAG, "onError")
    }

    override fun onComplete() {
        Log.i(TAG, "onComplete")
    }
}