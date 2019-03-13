package com.zhuanghongji.wan.base_common.http.intercepter

import com.zhuanghongji.wan.base_common.api.ApiConstant
import com.zhuanghongji.wan.base_common.utils.CookieUtil
import okhttp3.Interceptor
import okhttp3.Response

/**
 * 保存 Cookie 的拦截器
 */
class CookieInterceptor: Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)

        val requestUrl = request.url().toString()
        val isCookieRequest = requestUrl.contains(ApiConstant.PATH_USER_LOGIN)
                || requestUrl.contains(ApiConstant.PATH_USER_REGISTER)

        val cookies = response.headers(CookieUtil.SET_COOKIE_KEY)
        if (isCookieRequest && cookies.isNotEmpty()) {
            val cookie = CookieUtil.encodeCookie(cookies)
            val domain = request.url().host()
            CookieUtil.saveCookie(requestUrl, domain, cookie)
        }
        return response
    }
}