package com.zhuanghongji.wan.base_common.api.datas

import com.squareup.moshi.Json

/**
 * 文章
 */
data class Article(

    @Json(name = "apkLink") val apkLink: String,

    @Json(name = "author") val author: String,

    @Json(name = "chapterId") val chapterId: Int,

    @Json(name = "chapterName") val chapterName: String,

    @Json(name = "collect") var collect: Boolean,

    @Json(name = "courseId") val courseId: Int,

    @Json(name = "desc") val desc: String,

    @Json(name = "envelopePic") val envelopePic: String,

    @Json(name = "fresh") val fresh: Boolean,

    @Json(name = "id") val id: Int,

    @Json(name = "link") val link: String,

    @Json(name = "niceDate") val niceDate: String,

    @Json(name = "origin") val origin: String,

    @Json(name = "projectLink") val projectLink: String,

    @Json(name = "publishTime") val publishTime: Long,

    @Json(name = "superChapterId") val superChapterId: Int,

    @Json(name = "superChapterName") val superChapterName: String,

    @Json(name = "tags") val tags: MutableList<Tag>,

    @Json(name = "title") val title: String,

    @Json(name = "type") val type: Int,

    @Json(name = "userId") val userId: Int,

    @Json(name = "visible") val visible: Int,

    @Json(name = "zan") val zan: Int,

    @Json(name = "top") var top: String
)