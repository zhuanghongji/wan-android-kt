package com.zhuanghongji.wan.base_common.http.intercepter

import com.zhuanghongji.wan.base_common.impl.App
import com.zhuanghongji.wan.base_common.utils.NetworkUtil
import okhttp3.Interceptor
import okhttp3.Response

/**
 * 没有网络时的缓存拦截器
 *
 * 有网无网的拦截器见 [CacheInterceptor]
 */
class OfflineCacheIntercepter: Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)
        if (!NetworkUtil.isNetworkAvailable(App.context)) {
            // 没有网络时设置超时时间为 4 周（仅对 GET 有效，对 POST 无效）
            val maxStale = 60 * 60 * 24 * 28
            response.newBuilder()
                .header("Cache-Control", "public, only-if-cached, max-stale=$maxStale")
                .removeHeader("nyn")
                .build()
        }
        return response
    }

}