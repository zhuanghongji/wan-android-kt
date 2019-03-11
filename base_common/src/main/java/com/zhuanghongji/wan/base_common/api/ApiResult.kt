package com.zhuanghongji.wan.base_common.api

import com.squareup.moshi.Json

/**
 * 接口返回结果
 */
data class ApiResult<T> (

    @Json(name = "data") val data: T
) : BaseResult()
