package com.zhuanghongji.wan.base_common.http.intercepter

import okhttp3.Interceptor
import okhttp3.Response

/**
 * 设置通用参数的拦截器
 */
class CommonParamIntercepter: Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        val modifiedUrl = originalRequest.url().newBuilder()
            .addQueryParameter("phoneSystem", "")
            .addQueryParameter("phoneSystem", "")
            .build()
        val request = originalRequest.newBuilder().url(modifiedUrl).build()
        return chain.proceed(request)
    }

}