package com.zhuanghongji.wan

import android.util.Log
import android.widget.Button
import com.zhuanghongji.wan.base_common.base.BaseActivity
import com.zhuanghongji.wan.base_common.http.HttpManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainActivity : BaseActivity() {

    private val TAG = "MainActivity"

    private lateinit var btnPerform: Button

    override fun getLayoutResID(): Int {
        return R.layout.activity_main
    }

    override fun initView() {
        btnPerform = findViewById(R.id.btn_perform)
    }

    override fun initEvent() {
        btnPerform.setOnClickListener {
            performRequest()
        }
    }

    override fun initElse() {
        Log.i(TAG, "initElse")
    }

    override fun performRequest() {
        Log.i(TAG, "performRequest")
        HttpManager.apiService.getBanner()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.i(TAG, "onNext")
            }, {
                Log.i(TAG, "onError")
            }, {
                Log.i(TAG, "onCompleted")
            }).dispose()
    }
}
