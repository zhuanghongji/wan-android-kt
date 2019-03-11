package com.zhuanghongji.wan.base_common.ext

import com.orhanobut.logger.Logger
import com.zhuanghongji.wan.base_common.api.BaseResult
import com.zhuanghongji.wan.base_common.http.exception.ErrorStatus
import com.zhuanghongji.wan.base_common.http.function.RetryWithDelay
import com.zhuanghongji.wan.base_common.impl.App
import com.zhuanghongji.wan.base_common.rx.SchedulerHelper
import com.zhuanghongji.wan.base_common.utils.NetworkUtil
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

const val TAG = "RxExt"

/**
 * 封装了线程切换，结果判断
 *
 * @param isShowLoading true 弹出加载对话框
 * @param onSuccess 接口调用成功且结果为 "Success" 时回调
 */
fun <T: BaseResult> Observable<T>.ss(
    isShowLoading: Boolean = true,
    onSuccess: (T) -> Unit
) {
    this.compose(SchedulerHelper.ioToMain())
        .retryWhen(RetryWithDelay())
        .subscribe(object : Observer<T> {
            override fun onSubscribe(d: Disposable) {
                Logger.i(TAG, "onSubscribe")
                if (isShowLoading) {
                    Logger.i(TAG, "showLoading")
                }
            }

            override fun onNext(t: T) {
                Logger.i(TAG, "onNext")
                when(t.errorCode) {
                    ErrorStatus.SUCCESS -> onSuccess(t)
                    ErrorStatus.TOKEN_INVAILD -> {
                        Logger.w(TAG, "Token 过期，请重新登录")
                    }
                    else -> Logger.w(TAG, "un handle error code")
                }
            }

            override fun onError(e: Throwable) {
                Logger.i(TAG, "onError")
                if (!NetworkUtil.isNetworkConnected(App.instance)) {
                    Logger.w(TAG, "showDefaultMsg")
                    onComplete()
                }
            }

            override fun onComplete() {
                Logger.i(TAG, "onComplete")
                if (isShowLoading) {
                    Logger.i(TAG, "hideLoading")
                }
            }
        })
}

/**
 * 封装了线程切换，结果判断
 *
 * @param isShowLoading true 弹出加载对话框
 * @param onSuccess 接口调用成功且结果为 "Success" 时回调
 * @return Disposable
 */
fun <T: BaseResult> Observable<T>.ssd (
    isShowLoading: Boolean = true,
    onSuccess: (T) -> Unit
): Disposable {
    if (isShowLoading) {
        Logger.i(TAG, "showLoading")
    }
    return this.compose(SchedulerHelper.ioToMain())
        .retryWhen(RetryWithDelay())
        .subscribe({
            when(it.errorCode) {
                ErrorStatus.SUCCESS -> onSuccess(it)
                ErrorStatus.TOKEN_INVAILD -> {
                    Logger.w(TAG, "Token 过期，请重新登录")
                }
                else -> Logger.w(TAG, "un handle error code")
            }
            Logger.i(TAG, "hideLoading")
        }, {
            Logger.i(TAG, "hideLoading")
            Logger.i(TAG, "handle error")
        })
}
