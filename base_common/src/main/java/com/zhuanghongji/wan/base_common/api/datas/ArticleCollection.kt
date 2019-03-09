package com.zhuanghongji.wan.base_common.api.datas

import com.squareup.moshi.Json

/**
 * 收藏的文章
 */
data class ArticleCollection (

    @Json(name = "author") val author: String,

    @Json(name = "chapterId") val chapterId: Int,

    @Json(name = "chapterName") val chapterName: String,

    @Json(name = "courseId") val courseId: Int,

    @Json(name = "desc") val desc: String,

    @Json(name = "envelopePic") val envelopePic: String,

    @Json(name = "id") val id: Int,

    @Json(name = "link") val link: String,

    @Json(name = "niceDate") val niceDate: String,

    @Json(name = "origin") val origin: String,

    @Json(name = "originId") val originId: Int,

    @Json(name = "publishTime") val publishTime: Long,

    @Json(name = "title") val title: String,

    @Json(name = "userId") val userId: Int,

    @Json(name = "visible") val visible: Int,

    @Json(name = "zan") val zan: Int
)