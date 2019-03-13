package com.zhuanghongji.wan.base_common.api

import com.squareup.moshi.Json

/**
 * 接口返回结果，继承自 [BaseResult]
 *
 * 返回结果均为：
 *
 * ```json
 * {
 *     "data": ...,
 *     "errorCode": 0,
 *     "errorMsg": ""
 * }
 * ```
 */
data class ApiResult<T> (

    @Json(name = "data") val data: T
) : BaseResult()

