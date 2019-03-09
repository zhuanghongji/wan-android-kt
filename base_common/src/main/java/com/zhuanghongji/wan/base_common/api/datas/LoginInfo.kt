package com.zhuanghongji.wan.base_common.api.datas

import com.squareup.moshi.Json

/**
 * 登录信息
 */
data class LoginInfo (

    @Json(name = "chapterTops") val chapterTops: MutableList<String>,

    @Json(name = "collectIds") val collectIds: MutableList<String>,

    @Json(name = "email") val email: String,

    @Json(name = "icon") val icon: String,

    @Json(name = "id") val id: Int,

    @Json(name = "password") val password: String,

    @Json(name = "token") val token: String,

    @Json(name = "type") val type: Int,

    @Json(name = "username") val username: String
)