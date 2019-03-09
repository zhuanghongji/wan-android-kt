package com.zhuanghongji.wan.base_common.api.datas

import com.squareup.moshi.Json

/**
 * 新增的待办
 */
data class AddTodo (

    @Json(name = "title") val title: String,

    @Json(name = "content") val content: String,

    @Json(name = "date") val date: String,

    @Json(name = "type") val type: Int
)