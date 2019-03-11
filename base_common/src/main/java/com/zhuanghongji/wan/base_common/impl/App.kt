package com.zhuanghongji.wan.base_common.impl

import android.content.Context
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import com.zhuanghongji.wan.base_common.BuildConfig
import com.zhuanghongji.wan.base_common.base.BaseApplication
import kotlin.properties.Delegates


/**
 * App
 */
class App: BaseApplication() {

    companion object {
        private val TAG = "BaseApplication"
        var context: Context by Delegates.notNull()
            private set

        lateinit var instance: App
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        context = applicationContext

        initLogger()
    }

    private fun initLogger() {
        val formatStrategy = PrettyFormatStrategy.newBuilder()
            .showThreadInfo(false)  // (Optional) Whether to show thread info or not. Default true
            .methodCount(0)         // (Optional) How many method line to show. Default 2
            .methodOffset(7)        // (Optional) Hides internal method calls up to offset. Default 5
            // .logStrategy(customLog)  // (Optional) Changes the log strategy to print out. Default LogCat
            .tag("Wan")  // (Optional) Global tag for every log. Default PRETTY_LOGGER
            .build()

        Logger.addLogAdapter(object: AndroidLogAdapter(formatStrategy) {
            override fun isLoggable(priority: Int, tag: String?): Boolean {
                return BuildConfig.DEBUG
            }
        })
    }
}