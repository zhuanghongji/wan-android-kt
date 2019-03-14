package com.zhuanghongji.wan.base_common.http.intercepter

import com.zhuanghongji.wan.base_common.impl.App
import com.zhuanghongji.wan.base_common.utils.NetworkUtil
import okhttp3.Interceptor
import okhttp3.Response

/**
 * 有网的时候缓存
 *
 * 有网无网的拦截器见 [CacheInterceptor]
 */
class NetCacheIntercepter: Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)
        if (NetworkUtil.isNetworkAvailable(App.context)) {
            val maxAge = 60 * 3
            // 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
            response.newBuilder()
                .header("Cache-Control", "public, max-age=$maxAge")
                .removeHeader("Retrofit")
                .build()
        }
        return response
    }

}