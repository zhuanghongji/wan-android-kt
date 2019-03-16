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

    /** `true` 已登录；`false` 未登录 */
    protected var isLogin: Boolean by Preference(PreferenceConstant.IS_LOGIN, false)

    /** `true` 当前有网络；`false` 当前没网络 */
    protected var hasNetwork: Boolean by Preference(PreferenceConstant.HAS_NETWORK, false)

    /** 网络变化广播通知的接收器 */
    private var mNetworkChangedReceiver: NetworkChangedReceiver? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        // 隐藏标题栏、强制所有 Activity 都为竖屏
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        super.onCreate(savedInstanceState)

        setContentView(getLayoutResID())
        ARouter.getInstance().inject(this)
        if (useEventBus()) {
            EventBus.getDefault().register(this)
        }

        beforeInit()

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
        // 点击返回按钮时，栈里有 Fragment 的话则让其出栈先
        val count = supportFragmentManager.backStackEntryCount
        if (count == 0) {
            super.onBackPressed()
        } else {
            supportFragmentManager.popBackStack()
        }
    }

    /**
     * 是否使用 EventBus
     *
     * @return true 使用（默认则）；false 不使用
     */
    open fun useEventBus(): Boolean = true

    /**
     * 获取 Activity Content View 的资源 ID
     */
    @LayoutRes abstract fun getLayoutResID(): Int

    /**
     * beforeInit
     */
    abstract fun beforeInit()

    /**
     * 通过 findViewById 初始化当前页面的所有视图
     */
    abstract fun initView()

    /**
     * 初始化视图的点击、长按等事件
     */
    abstract fun initEvent()

    /**
     * 初始化从 Intent 中传过来的数据
     */
    abstract fun initData()

    /**
     * 其它初始化操作（比如设置视图的状态、显示内容等）
     */
    abstract fun initElse()

    /**
     * 初始化完成，执行其它操作（比如开始网络请求）
     */
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
     * 反注册网络变化的广播监听器
     */
    private fun unRegisterNeNetworkChangedReceiver() {
        if (mNetworkChangedReceiver != null) {
            unregisterReceiver(mNetworkChangedReceiver)
            mNetworkChangedReceiver = null
        }
    }
}