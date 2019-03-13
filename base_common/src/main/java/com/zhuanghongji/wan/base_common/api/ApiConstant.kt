package com.zhuanghongji.wan.base_common.api

/**
 * WAN ANDROID OPEN API 相关常量
 */
object ApiConstant {

    /**
     * The base url of all network request
     */
    const val BASE_URL = "https://www.wanandroid.com"

    /**
     * API PATH 登录
     */
    const val PATH_USER_LOGIN = "/user/login"

    /**
     * API PATH 注册
     */
    const val PATH_USER_REGISTER = "/user/register"

    /**
     * API PATH 收藏网址
     */
    const val PATH_COLLECT_WEBSITE = "/lg/collect"

    /**
     *  API PATH 取消收藏网址
     */
    const val PATH_UN_COLLECT_WEBSITE = "/lg/uncollect"

    /**
     *  API PATH 文章
     */
    const val PATH_ARTICLE = "/article"

    /**
     * API PATH 待办
     */
    const val PATH_TODO = "/lg/todo"
}