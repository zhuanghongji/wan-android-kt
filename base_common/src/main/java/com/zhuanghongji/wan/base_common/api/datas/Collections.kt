package com.zhuanghongji.wan.base_common.api.datas

import com.squareup.moshi.Json

/**
 * 收藏列表
 */
data class Collections<T> (

    @Json(name = "curPage") val curPage: Int,

    @Json(name = "datas") val datas: List<T>,

    @Json(name = "offset") val offset: Int,

    @Json(name = "over") val over: Boolean,

    @Json(name = "pageCount") val pageCount: Int,

    @Json(name = "size") val size: Int,

    @Json(name = "total") val total: Int
)