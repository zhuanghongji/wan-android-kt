package com.zhuanghongji.wan.base_common.api.datas

import com.squareup.moshi.Json

/**
 * 常用网站
 */
data class Friend (

    @Json(name = "icon") val icon: String,

    @Json(name = "id") val id: Int,

    @Json(name = "link") val link: String,

    @Json(name = "name") val name: String,

    @Json(name = "order") val order: Int,

    @Json(name = "visible") val visible: Int
)