package com.zhuanghongji.wan.base_common.mvp

import io.reactivex.disposables.Disposable

interface IModel {

    fun addDisposable(disposable: Disposable?)

    fun onDetch()
}