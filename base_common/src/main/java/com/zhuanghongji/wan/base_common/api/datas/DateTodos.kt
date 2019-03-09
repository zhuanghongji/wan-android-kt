package com.zhuanghongji.wan.base_common.api.datas

import com.squareup.moshi.Json

/**
 * 待办列表
 */
data class DateTodos (

    @Json(name = "date") val date: Long,

    @Json(name = "todoList") val todoList: MutableList<Todo>
)