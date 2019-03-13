package com.zhuanghongji.wan.base_common.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.zhuanghongji.wan.base_common.constants.PreferenceConstant
import com.zhuanghongji.wan.base_common.delegate.Preference
import com.zhuanghongji.wan.base_common.events.NetworkChangedEvent
import com.zhuanghongji.wan.base_common.view.MultipleStatusView
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * Base Fragment
 */
abstract class BaseFragment: Fragment() {

    /** `true` 已登录；`false` 未登录 */
    protected var isLogin: Boolean by Preference(PreferenceConstant.IS_LOGIN, false)

    /** `true` 当前有网络；`false` 当前没网络 */
    protected var hasNetwork: Boolean by Preference(PreferenceConstant.HAS_NETWORK, true)

    /**
     * `true` 视图已完成加载；`false` 未完成
     *
     *  会在 [onActivityCreated] 中至为 `true` 表示视图已经创建并准备好了
     */
    private var isViewPrepare = false

    /**
     * `true` 已加载过数据；false 未加载过
     *
     * 延迟调用 [lazyLoad] 方法实现懒加载
     */
    private var hasLoadData = false

    /** 多种状态的 View */
    protected var mLayoutStatusView: MultipleStatusView? = null

    /**
     * 获取页面布局 ID
     */
    @LayoutRes abstract fun getLayoutResID(): Int

    /**
     * 通过 findViewById 初始化所有视图
     */
    abstract fun initView(view: View)

    /**
     * 懒加载
     */
    abstract fun lazyLoad()

    /**
     * 是否使用 EventBus
     *
     * @return `true` 使用（默认值）；`false` 不使用
     */
    open fun useEventBus(): Boolean = true

    open val mRetryClickListener: View.OnClickListener = View.OnClickListener {
        lazyLoad()
    }

    /**
     * 无网状态 -> 有网状态，自动重连操作，子类可重写该方法
     */
    open fun doReConnected() {
        lazyLoad()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(getLayoutResID(), null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (useEventBus()) {
            EventBus.getDefault().register(this)
        }
        isViewPrepare = true
        initView(view)
        lazyLoadDataIfPrepared()

        // 设置 "重试" 点击事件
        mLayoutStatusView?.setOnClickListener(mRetryClickListener)
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        // 在开发过程中，我们常会使用 ViewPager 同时加载多个 Fragment，为了提高用户体验
        // 我们可以利用 getUserVisibleHint() 和 setUserVisibleHint() 实现延迟加载
        //
        // 当 Fragment 被用户可见时，setUserVisibleHint() 会被调用且传入 true 值
        // 当 Fragment 不被用户可见时，也会被调用，但得到的是 false 值
        if (isVisibleToUser) {
            lazyLoadDataIfPrepared()
        }
    }

    /**
     * 如果页面已经准备好的话，则调用 [lazyLoad] 加载数据
     */
    private fun lazyLoadDataIfPrepared() {
        if (userVisibleHint && isViewPrepare && !hasLoadData) {
            hasLoadData = true
            lazyLoad()
        }
    }

    @Suppress("unused")
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onNetworkChangedEvent(event: NetworkChangedEvent) {
        if (event.isConnected) {
            doReConnected()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (useEventBus()) {
            EventBus.getDefault().unregister(this)
        }
    }
}