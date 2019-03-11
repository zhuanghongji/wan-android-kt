package com.zhuanghongji.wan.base_common.mvp

interface IPresenter<V: IView> {

    /**
     * 绑定 View
     */
    fun attachView(view: V)

    /**
     * 解绑 View
     */
    fun detachView()
}