package com.zhuanghongji.wan.base_common.api.datas

import com.squareup.moshi.Json

data class AllTodo (

    @Json(name = "type") val type: Int,

    @Json(name = "doneList") val doneList: MutableList<DateTodos>,

    @Json(name = "todoList") val todoList: MutableList<DateTodos>
)