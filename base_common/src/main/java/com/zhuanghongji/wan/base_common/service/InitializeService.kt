package com.zhuanghongji.wan.base_common.service

import android.app.IntentService
import android.content.Context
import android.content.Intent
import com.orhanobut.logger.Logger

/**
 *
 */
class InitializeService: IntentService("InitializeService") {

    companion object {
        const val TAG = "InitializeService"
        const val ACTION_INIT = "initApplication"

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

    fun initApplication() {
        Logger.i(TAG, "initApplication")
    }
}