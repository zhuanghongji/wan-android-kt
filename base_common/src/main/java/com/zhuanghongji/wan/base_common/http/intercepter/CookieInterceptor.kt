package com.zhuanghongji.wan.base_common.http.intercepter

import com.zhuanghongji.wan.base_common.api.ApiConstant
import com.zhuanghongji.wan.base_common.manager.CookieManager
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
        val isCookieRequest = requestUrl.contains(ApiConstant.SAVE_USER_LOGIN_KEY)
                || requestUrl.contains(ApiConstant.SAVE_USER_REGISTER_KEY)

        val cookies = response.headers(CookieManager.SET_COOKIE_KEY)
        if (isCookieRequest && cookies.isNotEmpty()) {
            val cookie = CookieManager.encodeCookie(cookies)
            val domain = request.url().host()
            CookieManager.saveCookie(requestUrl, domain, cookie)
        }
        return response
    }
}