package com.zhuanghongji.wan.base_common.base

import android.os.Bundle
import com.zhuanghongji.wan.base_common.ext.toast
import com.zhuanghongji.wan.base_common.mvp.IPresenter
import com.zhuanghongji.wan.base_common.mvp.IView

/**
 * 用于实现 MVP 模式的 Base Activity
 *
 * @param V View 视图接口，由当前 Activity 作具体实现，管理视图的状态与显示
 * @param P Presenter 控制器接口，创建一个新的类来作具体实现，放置业务逻辑
 */
abstract class BaseMvpActivity<V: IView, P: IPresenter<V>>: BaseActivity(), IView {

    protected lateinit var mPresenter: P

    protected abstract fun createPresenter(): P

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun beforeInit() {
        mPresenter = createPresenter()
        // Make sure your activity implements V interface
        @Suppress("UNCHECKED_CAST") mPresenter.attachView(this as V)
    }

    override fun onDestroy() {
        super.onDestroy()
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