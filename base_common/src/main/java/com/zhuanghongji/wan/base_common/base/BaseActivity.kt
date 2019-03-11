package com.zhuanghongji.wan.base_common.base

import android.content.IntentFilter
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import com.zhuanghongji.wan.base_common.constants.PreferenceConstant
import com.zhuanghongji.wan.base_common.delegate.Preference
import com.zhuanghongji.wan.base_common.receivers.NetworkChangedReceiver
import org.greenrobot.eventbus.EventBus

/**
 * Base Activity
 */
abstract class BaseActivity: AppCompatActivity() {

    protected var isLogin: Boolean by Preference(PreferenceConstant.IS_LOGIN, false)

    protected var hasNetwork: Boolean by Preference(PreferenceConstant.HAS_NETWORK, false)

    private var mNetworkChangedReceiver: NetworkChangedReceiver? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutResID())
        if (useEventBus()) {
            EventBus.getDefault().register(this)
        }

        initData()
        initView()
        initEvent()
        initElse()
        start()
    }

    override fun onResume() {
        super.onResume()
        registerNeNetworkChangedReceiver()
    }

    override fun onPause() {
        super.onPause()
        unRegisterNeNetworkChangedReceiver()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (useEventBus()) {
            EventBus.getDefault().unregister(this)
        }
    }

    open fun useEventBus(): Boolean = true

    @LayoutRes abstract fun getLayoutResID(): Int

    abstract fun initView()

    abstract fun initEvent()

    abstract fun initData()

    abstract fun initElse()

    abstract fun start()

    /**
     * 注册网络变化的广播监听器
     */
    private fun registerNeNetworkChangedReceiver() {
        val filter = IntentFilter()
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE")
        mNetworkChangedReceiver = NetworkChangedReceiver()
        registerReceiver(mNetworkChangedReceiver, filter)
    }

    /**
     * 解注册网络变化的广播监听器
     */
    private fun unRegisterNeNetworkChangedReceiver() {
        if (mNetworkChangedReceiver != null) {
            unregisterReceiver(mNetworkChangedReceiver)
            mNetworkChangedReceiver = null
        }
    }
}