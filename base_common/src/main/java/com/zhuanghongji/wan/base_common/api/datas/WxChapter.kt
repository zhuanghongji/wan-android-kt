package com.zhuanghongji.wan.base_common.api.datas

import com.squareup.moshi.Json

/**
 * 公众号列表
 */
data class WxChapter (

    @Json(name = "children") val children: MutableList<String>,

    @Json(name = "courseId") val courseId: Int,

    @Json(name = "id") val id: Int,

    @Json(name = "name") val name: String,

    @Json(name = "order") val order: Int,

    @Json(name = "parentChapterId") val parentChapterId: Int,

    @Json(name = "userControlSetTop") val userControlSetTop: Boolean,

    @Json(name = "visible") val visible: Int
)