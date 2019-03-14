package com.zhuanghongji.wan.base_common.http.cookies

import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl

/**
 * Cookie 管理类
 */
class CookieManager: CookieJar {

    private val cookieStore = PersistentCookieStore()

    /**
     * 保存当前 [url] 下的所有 Cookie
     */
    override fun saveFromResponse(url: HttpUrl, cookies: MutableList<Cookie>) {
        if (cookies.isNotEmpty()) {
            for (cookie in cookies) {
                cookieStore.add(url, cookie)
            }
        }
    }

    /**
     * 获取当前 [url] 对应的所有 Cookie
     */
    override fun loadForRequest(url: HttpUrl): MutableList<Cookie> {
        return cookieStore.getCookies(url).toMutableList()
    }

    /**
     * 清空当前 [url] 对应的所有 Cookie
     */
    fun clearAllCookie(url: HttpUrl, cookie: Cookie): Boolean = cookieStore.remove(url, cookie)

    /**
     * 获取 [PersistentCookieStore] 里面的所有的 Cookie
     */
    fun getCookies(): List<Cookie> = cookieStore.getAllCookies()
}