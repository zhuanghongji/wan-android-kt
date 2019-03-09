package com.zhuanghongji.wan.base_common.api.datas

import com.squareup.moshi.Json

/**
 * 轮播图
 */
data class Banner (

    @Json(name = "desc") val desc: String,

    @Json(name = "id") val id: Int,

    @Json(name = "imagePath") val imagePath: String,

    @Json(name = "isVisible") val isVisible: Int,

    @Json(name = "order") val order: Int,

    @Json(name = "title") val title: String,

    @Json(name = "type") val type: Int,

    @Json(name = "url") val url: String
)