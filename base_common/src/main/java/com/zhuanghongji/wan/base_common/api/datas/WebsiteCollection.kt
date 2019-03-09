package com.zhuanghongji.wan.base_common.api.datas

import com.squareup.moshi.Json

/**
 * 收藏的网站
 */
data class WebsiteCollection (

    @Json(name = "desc") val desc: String,

    @Json(name = "icon") val icon: String,

    @Json(name = "id") val id: Int,

    @Json(name = "link") var link: String,

    @Json(name = "name") var name: String,

    @Json(name = "order") val order: Int,

    @Json(name = "userId") val userId: Int,

    @Json(name = "visible") val visible: Int
)