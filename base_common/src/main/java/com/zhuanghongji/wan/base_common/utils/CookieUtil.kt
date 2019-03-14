package com.zhuanghongji.wan.base_common.utils

import com.zhuanghongji.wan.base_common.delegate.Preference

/**
 * Cookie 工具类
 */
object CookieUtil {

    const val SET_COOKIE_KEY = "set-cookie"

    const val COOKIE_NAME = "Cookie"

    /**
     * 将 Cookie 编码成字符串
     */
    fun encodeCookie(cookies: List<String>): String {
        val sb = StringBuilder()
        val set = HashSet<String>()
        cookies.map {
            cookie -> cookie.split(";".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        }.forEach {
            it.filterNot { s -> set.contains(s) }.forEach { s -> set.add(s) }
        }

        val ite = set.iterator()
        while (ite.hasNext()) {
            val cookie = ite.next()
            sb.append(cookie).append(";")
        }
        val last = sb.lastIndexOf(";")
        if (sb.length - 1 == last) {
            sb.deleteCharAt(last)
        }
        return sb.toString()
    }

    /**
     * 将 [url] 和 [domain] 作为键分别保存 Cookie 值到 [Preference] 中
     *
     * @param cookies 由 [encodeCookie] 编码过的 Cookies 字符串
     */
    @Suppress("UNUSED_VALUE", "ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    fun saveCookie(url: String?, domain: String?, cookies: String) {
        url ?: return
        var spUrl: String by Preference(url, cookies)
        spUrl = cookies

        domain ?: return
        var spDomain: String by Preference(domain, cookies)
        spDomain = cookies
    }
}