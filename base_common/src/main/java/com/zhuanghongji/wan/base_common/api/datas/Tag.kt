package com.zhuanghongji.wan.base_common.api.datas

import com.squareup.moshi.Json

/**
 * 标签
 */
data class Tag (

    @Json(name = "name") val name: String,

    @Json(name = "url") val url: String
)