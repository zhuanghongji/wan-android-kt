package com.zhuanghongji.wan.base_common.http.cookies

import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl

class CookieManager: CookieJar {

    private val COOKIE_STORE = PersistentCookieStore()

    override fun saveFromResponse(url: HttpUrl, cookies: MutableList<Cookie>) {
        if (cookies.isNotEmpty()) {
            for (cookie in cookies) {
                COOKIE_STORE.add(url, cookie)
            }
        }
    }

    override fun loadForRequest(url: HttpUrl): MutableList<Cookie> {
        return COOKIE_STORE.getCookies(url).toMutableList()
    }

    fun clearAllCookie(url: HttpUrl, cookie: Cookie): Boolean {
        return COOKIE_STORE.remove(url, cookie)
    }

    fun getCookies(): List<Cookie> {
        return COOKIE_STORE.getAllCookies()
    }
}