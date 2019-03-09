package com.zhuanghongji.wan.base_common.api.datas

/**
 * 待办类型
 */
data class TodoType (

    val type: Int,

    val name: String,

    var isSelected: Boolean
)