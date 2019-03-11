package com.zhuanghongji.wan.base_common.base

import android.content.IntentFilter
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.MenuItem
import android.view.MotionEvent
import android.view.Window
import android.view.WindowManager
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.launcher.ARouter
import com.zhuanghongji.wan.base_common.constants.PreferenceConstant
import com.zhuanghongji.wan.base_common.delegate.Preference
import com.zhuanghongji.wan.base_common.events.NetworkChangedEvent
import com.zhuanghongji.wan.base_common.receivers.NetworkChangedReceiver
import com.zhuanghongji.wan.base_common.utils.KeyBoardUtil
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * Base Activity
 */
abstract class BaseActivity: AppCompatActivity() {

    protected var isLogin: Boolean by Preference(PreferenceConstant.IS_LOGIN, false)

    protected var hasNetwork: Boolean by Preference(PreferenceConstant.HAS_NETWORK, false)

    private var mNetworkChangedReceiver: NetworkChangedReceiver? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        // 无默认标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        // 强制所有 Activity 都为竖屏
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        super.onCreate(savedInstanceState)

        setContentView(getLayoutResID())
        ARouter.getInstance().inject(this)
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

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (ev?.action == MotionEvent.ACTION_UP) {
            val v = currentFocus
            // 如果不是落在 EditText 区域，就需要关闭输入法
            if (KeyBoardUtil.isHideKeyboard(v, ev)) {
                KeyBoardUtil.hideKeyBoard(this, v)
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                onBackPressed()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        // super.onBackPressed()
        // Fragment 逐个出栈
        val count = supportFragmentManager.backStackEntryCount
        if (count == 0) {
            super.onBackPressed()
        } else {
            supportFragmentManager.popBackStack()
        }
    }

    open fun useEventBus(): Boolean = true

    @LayoutRes abstract fun getLayoutResID(): Int

    abstract fun initView()

    abstract fun initEvent()

    abstract fun initData()

    abstract fun initElse()

    abstract fun start()

    @Suppress("unused")
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onNetworkChangedEvent(event: NetworkChangedEvent) {
        hasNetwork = event.isConnected
    }

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