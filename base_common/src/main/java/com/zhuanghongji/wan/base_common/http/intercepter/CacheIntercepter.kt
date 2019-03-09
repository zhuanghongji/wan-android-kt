package com.zhuanghongji.wan.base_common.http.intercepter

import com.zhuanghongji.wan.base_common.impl.App
import com.zhuanghongji.wan.base_common.utils.NetworkUtil
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response

/**
 * 缓存拦截器
 */
class CacheIntercepter: Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        if (!NetworkUtil.isNetworkAvailable(App.context)) {
            request = request.newBuilder()
                .cacheControl(CacheControl.FORCE_CACHE)
                .build()
        }

        val response = chain.proceed(request)
        if (NetworkUtil.isNetworkAvailable(App.context)) {
            // 有网络时设置缓存超时时间为 0 个小时（只对 GET 有用，POST 没有缓冲）
            val maxAge = 60 * 3
            response.newBuilder()
                .header("Cache-Control", "public, max-age=$maxAge")
                .removeHeader("Retrofit")
                .build()
        } else {
            // 没有网络时设置超时时间为 4 周（只对 GET 有用，POST 没有缓冲）
            val maxStale = 60 * 60 * 24 * 7 * 4
            response.newBuilder()
                .header("Cache-Control", "public, only-if-cached, max-stale=$maxStale")
                .removeHeader("nyn")
                .build()
        }
        return response
    }
}