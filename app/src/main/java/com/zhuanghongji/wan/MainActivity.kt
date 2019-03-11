package com.zhuanghongji.wan

import android.util.Log
import android.widget.TextView
import com.zhuanghongji.wan.base_common.base.BaseMvpActivity

class MainActivity : BaseMvpActivity<MainContract.View, MainContract.Presenter>(), MainContract.View {

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
        super.initView()
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

    override fun createPresenter(): MainContract.Presenter = MainPresenter()

    override fun setBanner(banners: String) {
        tvText.text = banners
    }

    private fun requestBanner() {
        mPresenter?.requestBanner()
    }
}
