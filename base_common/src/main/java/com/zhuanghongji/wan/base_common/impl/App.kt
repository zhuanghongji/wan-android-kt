package com.zhuanghongji.wan.base_common.impl

import android.content.Context
import com.zhuanghongji.wan.base_common.base.BaseApplication
import kotlin.properties.Delegates

class App: BaseApplication() {

    companion object {
        private val TAG = "BaseApplication"
        var context: Context by Delegates.notNull()
            private set

        lateinit var instant: App
    }

    override fun onCreate() {
        super.onCreate()
        instant = this
        context = applicationContext
    }
}