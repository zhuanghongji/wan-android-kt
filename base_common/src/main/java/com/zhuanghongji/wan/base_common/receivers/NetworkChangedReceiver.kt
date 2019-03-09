package com.zhuanghongji.wan.base_common.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.zhuanghongji.wan.base_common.constants.PreferenceConstant
import com.zhuanghongji.wan.base_common.delegate.Preference
import com.zhuanghongji.wan.base_common.events.NetworkChangedEvent
import com.zhuanghongji.wan.base_common.utils.NetworkUtil
import org.greenrobot.eventbus.EventBus

/**
 * 接收网络状态发生变化广播通知的接收器
 */
class NetworkChangedReceiver: BroadcastReceiver() {

    private var hasNetWork: Boolean by Preference(PreferenceConstant.HAS_NETWORK, true)

    override fun onReceive(context: Context?, intent: Intent?) {
        context ?: return
        val isConnected = NetworkUtil.isNetworkConnected(context)
        if (isConnected) {
            if (isConnected != hasNetWork) {
                EventBus.getDefault().post(NetworkChangedEvent(isConnected))
            }
        } else {
            EventBus.getDefault().post(NetworkChangedEvent(isConnected))
        }
    }

}