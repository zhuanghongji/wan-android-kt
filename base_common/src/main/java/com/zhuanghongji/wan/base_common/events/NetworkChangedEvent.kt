package com.zhuanghongji.wan.base_common.events

/**
 * 网络变化事件
 *
 * 通过 [isConnected] 可知道当前网络是否处于连接状态
 */
data class NetworkChangedEvent(

    /** true 网络已连接；false 网络未连接 */
    var isConnected: Boolean
)