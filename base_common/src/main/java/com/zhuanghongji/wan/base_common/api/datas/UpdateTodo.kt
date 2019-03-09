package com.zhuanghongji.wan.base_common.api.datas

import com.squareup.moshi.Json

/**
 * 更新的待办
 */
data class UpdateTodo (

    @Json(name = "title") val title: String,

    @Json(name = "content") val content: String,

    @Json(name = "date") val date: String,

    @Json(name = "status") val status: Int,

    @Json(name = "type") val type: Int
)