package com.zhuanghongji.wan.base_common.api

/**
 * WAN ANDROID API 请求响应码
 */
object ErrorCode {

    /**
     * 表示网络请求成功，不建议依赖任何非 0 的 errorCode.
     */
    const val SUCCESS = 0

    /**
     * 代表登录失效，需要重新登录
     */
    const val IVALID_TOKEN = -1001
}