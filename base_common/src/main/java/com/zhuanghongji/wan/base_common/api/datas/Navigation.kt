package com.zhuanghongji.wan.base_common.api.datas

import com.squareup.moshi.Json

/**
 * 导航
 */
data class Navigation (

    @Json(name = "articles") val articles: MutableList<Article>,

    @Json(name = "cid") val cid: Int,

    @Json(name = "name") val name: String
)