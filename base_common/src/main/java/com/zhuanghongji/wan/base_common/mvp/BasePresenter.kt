package com.zhuanghongji.wan.base_common.mvp

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import org.greenrobot.eventbus.EventBus

abstract class BasePresenter<M: IModel, V: IView>: IPresenter<V>, LifecycleObserver {

    protected var mModel: M? = null

    protected var mView: V? = null

    private val isViewAttached: Boolean
        get() = mView != null

    private var mCompositeDisposable: CompositeDisposable? = null

    open fun createModel(): M? = null

    open fun useEventBus(): Boolean = false

    override fun attachView(view: V) {
        mView = view
        mModel = createModel()
        if (mView is LifecycleOwner) {
            val viewLifecycleOwner = mView as LifecycleOwner
            viewLifecycleOwner.lifecycle.addObserver(this)
            if (mModel != null && mModel is LifecycleObserver) {
                viewLifecycleOwner.lifecycle.addObserver(mModel as LifecycleObserver)
            }
        }

        if (useEventBus()) {
            EventBus.getDefault().register(this)
        }
    }

    override fun detachView() {
        if (!isViewAttached) throw MvpViewNotAttachedException()
    }

    @Deprecated("")
    open fun addSubscription(disposable: Disposable?) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = CompositeDisposable()
        }
        disposable?.let { mCompositeDisposable?.add(it) }
    }

    open fun addDisposable(disposable: Disposable?) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = CompositeDisposable()
        }
        disposable?.let { mCompositeDisposable?.add(it) }
    }

    private fun unDispose() {
        // 保证 Activity 结束时取消
        mCompositeDisposable?.clear()
        mCompositeDisposable = null
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy(owner: LifecycleOwner) {
        owner.lifecycle.removeObserver(this)
    }

    private class MvpViewNotAttachedException internal constructor()
        : RuntimeException("Please call IPresenter.attachView(IView) before requesting data to the presenter")
}