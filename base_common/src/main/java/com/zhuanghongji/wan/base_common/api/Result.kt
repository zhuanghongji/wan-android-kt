package com.zhuanghongji.wan.base_common.api

data class Result<T> (

    val errorCode: Int = 0,

    val errorMsg: String = "",

    val data: T
)

