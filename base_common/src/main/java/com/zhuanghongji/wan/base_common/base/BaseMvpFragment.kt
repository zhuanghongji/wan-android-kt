package com.zhuanghongji.wan.base_common.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zhuanghongji.wan.base_common.ext.toast
import com.zhuanghongji.wan.base_common.mvp.IPresenter
import com.zhuanghongji.wan.base_common.mvp.IView

/**
 * 用于实现 MVP 模式的 Base Fragment（与 [BaseMvpActivity] 实现类似）
 *
 * @param V View 视图接口，由当前 Activity 作具体实现，管理视图的状态与显示
 * @param P Presenter 控制器接口，创建一个新的类来作具体实现，放置业务逻辑
 */
abstract class BaseMvpFragment<V: IView, P: IPresenter<V>>: BaseFragment(), IView {

    protected lateinit var mPresenter: P

    protected abstract fun createPresenter(): P

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mPresenter = createPresenter()
        // Make sure you fragment implements V interface
        @Suppress("UNCHECKED_CAST") mPresenter.attachView(this as V)

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mPresenter.detachView()
    }

    override fun showLoading() {
        // do nothing
    }

    override fun hideLoading() {
        // do nothing
    }

    override fun showDefaultMessage(message: String) {
        toast(message)
    }

    override fun showMessage(message: String) {
        toast(message)
    }

    override fun showError(errorMessage: String) {
        toast(errorMessage)
    }
}