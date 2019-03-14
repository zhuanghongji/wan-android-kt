package com.zhuanghongji.wan.base_common.ext

import com.orhanobut.logger.Logger
import com.zhuanghongji.wan.base_common.Wan
import com.zhuanghongji.wan.base_common.api.BaseResult
import com.zhuanghongji.wan.base_common.http.exception.ErrorStatus
import com.zhuanghongji.wan.base_common.http.exception.ExceptionHandler
import com.zhuanghongji.wan.base_common.http.function.RetryWithDelay
import com.zhuanghongji.wan.base_common.mvp.IModel
import com.zhuanghongji.wan.base_common.mvp.IView
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
    model: IModel?,
    view: IView?,
    isShowLoading: Boolean = true,
    onSuccess: (T) -> Unit
) {
    this.compose(SchedulerHelper.ioToMain())
        .retryWhen(RetryWithDelay())
        .subscribe(object : Observer<T> {

            override fun onComplete() {
                Logger.t(TAG).i("onComplete")
                view?.hideLoading()
            }

            override fun onSubscribe(d: Disposable) {
                Logger.t(TAG).i("onSubscribe")
                if (isShowLoading) {
                    view?.showLoading()
                }
                model?.addDisposable(d)
                if (!NetworkUtil.isNetworkConnected(Wan.getAppContext())) {
                    view?.showDefaultMessage("网络未连接")
                    onComplete()
                }
            }

            override fun onNext(t: T) {
                Logger.t(TAG).i("onNext, t = %s", t)
                when(t.errorCode) {
                    ErrorStatus.SUCCESS -> onSuccess(t)
                    ErrorStatus.TOKEN_INVALID -> {
                        Logger.w(TAG, "Token 过期，请重新登录")
                    }
                    else -> view?.showDefaultMessage(t.errorMsg)
                }
            }

            override fun onError(e: Throwable) {
                Logger.t(TAG).i("onError")
                view?.hideLoading()
                view?.showError(ExceptionHandler.getExceptionPair(e).second)
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
fun <T: BaseResult> Observable<T>.sss (
    view: IView?,
    isShowLoading: Boolean = true,
    onSuccess: (T) -> Unit
): Disposable {
    if (isShowLoading) {
        view?.showLoading()
    }
    return this.compose(SchedulerHelper.ioToMain())
        .retryWhen(RetryWithDelay())
        .subscribe({
            when(it.errorCode) {
                ErrorStatus.SUCCESS -> onSuccess(it)
                ErrorStatus.TOKEN_INVALID -> {
                    Logger.t(TAG).i("Token 过期，请重新登录")
                }
                else -> view?.showDefaultMessage(it.errorMsg)
            }
            view?.hideLoading()
        }, {
            view?.hideLoading()
            view?.showError(ExceptionHandler.getExceptionPair(it).second)
        })
}
