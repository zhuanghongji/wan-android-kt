package com.zhuanghongji.wan.base_common.http.exception

/**
 * 请求 WAN ANDROID OPEN API 时发生的异常
 */
class ApiException: RuntimeException {

    private var code: Int? = null

    constructor(throwable: Throwable, code: Int?): super(throwable) {
        this.code = code
    }

    constructor(message: String): super(Throwable(message))
}