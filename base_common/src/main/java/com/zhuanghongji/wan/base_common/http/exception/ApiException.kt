package com.zhuanghongji.wan.base_common.http.exception

class ApiException: RuntimeException {

    private var code: Int? = null

    constructor(throwable: Throwable, code: Int?): super(throwable) {
        this.code = code
    }

    constructor(message: String): super(Throwable(message))
}