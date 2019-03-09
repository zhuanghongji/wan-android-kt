package com.zhuanghongji.wan.base_common.api.datas

import com.squareup.moshi.Json

/**
 * 项目
 */
data class Project (

    @Json(name = "children") val children: List<Any>,

    @Json(name = "courseId") val courseId: Int,

    @Json(name = "id") val id: Int,

    @Json(name = "name") val name: String,

    @Json(name = "order") val order: Int,

    @Json(name = "parentChapterId") val parentChapterId: Int,

    @Json(name = "visible") val visible: Int
)