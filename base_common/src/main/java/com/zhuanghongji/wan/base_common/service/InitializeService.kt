package com.zhuanghongji.wan.base_common.service

import android.app.IntentService
import android.content.Context
import android.content.Intent
import com.orhanobut.logger.Logger

/**
 * 用于执行初始化耗时长的逻辑的 Service
 *
 * 继承自 [IntentService]，从而避免启动白屏时间过长。
 *
 * IntentService 有以下特点：
 * - 在任务完成后自动停止
 * - 任务在队列中执行，有先后顺序
 * - 任务在子线程中运行，可以执行耗时任务
 */
class InitializeService: IntentService("InitializeService") {

    companion object {
        const val TAG = "InitializeService"
        const val ACTION_INIT = "initApplication"

        /**
         * 启动 InitializeService 以执行耗时的初始化操作
         */
        fun start(context: Context) {
            val intent = Intent(context, InitializeService::class.java)
            intent.action = ACTION_INIT
            context.startService(intent)
        }
    }

    override fun onHandleIntent(intent: Intent?) {
        if (intent != null && ACTION_INIT == intent.action) {
            initApplication()
        }
    }

    /**
     * 在这里添加你的初始化任务
     */
    private fun initApplication() {
        Logger.i(TAG, "init application.")
    }
}