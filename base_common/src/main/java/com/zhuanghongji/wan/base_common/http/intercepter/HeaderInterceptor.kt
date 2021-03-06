package com.zhuanghongji.wan.base_common.http.intercepter

import com.zhuanghongji.wan.base_common.api.ApiConstant
import com.zhuanghongji.wan.base_common.delegate.Preference
import com.zhuanghongji.wan.base_common.utils.CookieUtil
import okhttp3.Interceptor
import okhttp3.Response

/**
 * 请求头拦截器
 */
class HeaderInterceptor: Interceptor {

    companion object {
        const val TOKEN_KEY = "token"
    }

    private var token: String by Preference(TOKEN_KEY, "")

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val builder = request.newBuilder()

        builder.addHeader("Content-type", "application/json; charset=utf-8")
        // .header("token", token)
        // .method(request.method(), request.body())

        val domain = request.url().host()
        val url = request.url().toString()
        val isContains = url.contains(ApiConstant.PATH_COLLECT_WEBSITE)
                || url.contains(ApiConstant.PATH_UN_COLLECT_WEBSITE)
                || url.contains(ApiConstant.PATH_ARTICLE)
                || url.contains(ApiConstant.PATH_TODO)
        if (domain.isEmpty() && isContains) {
            val spDomain: String by Preference(domain, "")
            val cookie: String = if (spDomain.isNotEmpty()) spDomain else ""
            if (cookie.isNotEmpty()) {
                // put cookie to the header
                builder.addHeader(CookieUtil.COOKIE_NAME, cookie)
            }
        }
        return chain.proceed(builder.build())
    }
}