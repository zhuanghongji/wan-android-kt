package com.zhuanghongji.wan.base_common.base

import android.view.View
import com.zhuanghongji.wan.base_common.ext.toast
import com.zhuanghongji.wan.base_common.mvp.IPresenter
import com.zhuanghongji.wan.base_common.mvp.IView

abstract class BaseMvpFragment<V: IView, P: IPresenter<V>>: BaseFragment(), IView {

    protected var mPresenter: P? = null

    protected abstract fun createPresenter(): P

    override fun initView(view: View) {
        mPresenter = createPresenter()
        @Suppress("UNCHECKED_CAST") mPresenter?.attachView(this as V)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mPresenter?.detachView()
        mPresenter = null
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