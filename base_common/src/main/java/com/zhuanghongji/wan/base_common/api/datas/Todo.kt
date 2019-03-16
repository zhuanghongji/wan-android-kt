package com.zhuanghongji.wan.base_common.api.datas

import com.squareup.moshi.Json
import java.io.Serializable

/**
 * 待办
 */
data class Todo (

    @Json(name = "id") val id: Int,

    @Json(name = "completeDate") val completeDate: String,

    @Json(name = "completeDateStr") val completeDateStr: String,

    @Json(name = "content") val content: String,

    @Json(name = "date") val date: Long,

    @Json(name = "dateStr") val dateStr: String,

    @Json(name = "status") val status: Int,

    @Json(name = "title") val title: String,

    @Json(name = "type") val type: Int,

    @Json(name = "userId") val userId: Int,

    @Json(name = "priority") val priority: Int
) : Serializable