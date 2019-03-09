package com.zhuanghongji.wan.base_common.api.datas

import com.squareup.moshi.Json

/**
 * 热搜
 */
data class HotKey (

    @Json(name = "id") val id: Int,

    @Json(name = "link") val link: String,

    @Json(name = "name") val name: String,

    @Json(name = "order") val order: Int,

    @Json(name = "visible") val visible: Int
)