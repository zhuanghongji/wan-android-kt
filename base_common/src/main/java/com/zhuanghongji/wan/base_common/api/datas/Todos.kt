package com.zhuanghongji.wan.base_common.api.datas

import com.squareup.moshi.Json

/**
 * 待办
 */
data class Todos (

    @Json(name = "curPage") val curPage: Int,

    @Json(name = "datas") val datas: MutableList<Todo>,

    @Json(name = "offset") val offset: Int,

    @Json(name = "over") val over: Boolean,

    @Json(name = "pageCount") val pageCount: Int,

    @Json(name = "size") val size: Int,

    @Json(name = "total") val total: Int
)