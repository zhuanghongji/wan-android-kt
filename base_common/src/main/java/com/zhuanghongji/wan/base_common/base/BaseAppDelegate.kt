package com.zhuanghongji.wan.base_common.base

import android.app.Application
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import com.zhuanghongji.wan.base_common.BuildConfig
import com.zhuanghongji.wan.base_common.Wan
import com.zhuanghongji.wan.base_common.service.InitializeService

/**
 * Application 代理
 *
 * - 执行初始化相关逻辑
 */
class BaseAppDelegate(private val application: Application) {

    /**
     * The delegate of [Application.onCreate]
     */
    fun onCreate() {
        Wan.init(application)

        initLogger()
        InitializeService.start(application)
    }

    /**
     * 初始化 [Logger]
     */
    private fun initLogger() {
        val formatStrategy = PrettyFormatStrategy.newBuilder()
            .showThreadInfo(false)  // (Optional) Whether to show thread info or not. Default true
            .methodCount(0)         // (Optional) How many method line to show. Default 2
            .methodOffset(7)        // (Optional) Hides internal method calls up to offset. Default 5
            // .logStrategy(customLog)  // (Optional) Changes the log strategy to print out. Default LogCat
            .tag("Wan")            // (Optional) Global tag for every log. Default PRETTY_LOGGER
            .build()

        Logger.addLogAdapter(object: AndroidLogAdapter(formatStrategy) {
            override fun isLoggable(priority: Int, tag: String?): Boolean {
                return BuildConfig.DEBUG
            }
        })
    }
}