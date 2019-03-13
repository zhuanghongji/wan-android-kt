package com.zhuanghongji.wan.base_common.api

/**
 * 接口返回结果的 Base 类
 */
open class BaseResult {

    /**
     * 如果为负数则认为错误，此时 [errorMsg] 会包含错误信息，详情见 [ErrorCode]。
     */
    var errorCode: Int = 0

    /**
     * 错误信息
     */
    var errorMsg: String = ""
}