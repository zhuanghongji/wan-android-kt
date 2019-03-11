package com.zhuanghongji.wan

import android.util.Log
import android.widget.TextView
import com.orhanobut.logger.Logger
import com.zhuanghongji.wan.base_common.base.BaseActivity
import com.zhuanghongji.wan.base_common.ext.ss
import com.zhuanghongji.wan.base_common.http.HttpManager

class MainActivity : BaseActivity() {

    companion object {
        const val TAG = "MainActivity"
    }

    private lateinit var tvText: TextView

    override fun useEventBus(): Boolean {
        return false
    }

    override fun getLayoutResID(): Int {
        return R.layout.activity_main
    }

    override fun initView() {
        tvText = findViewById(R.id.tvText)
    }

    override fun initData() {
        // do nothing
    }

    override fun initEvent() {
        tvText.setOnClickListener {
            Log.i(TAG, "tvText")
        }
    }

    override fun initElse() {
        // do nothing
    }

    override fun start() {
        requestBanner()
    }

    private fun requestBanner() {
        HttpManager.apiService.getBanner().ss {
            Logger.t(TAG).d(it)
        }
    }
}
