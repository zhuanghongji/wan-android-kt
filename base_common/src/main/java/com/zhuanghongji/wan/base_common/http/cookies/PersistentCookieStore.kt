package com.zhuanghongji.wan.base_common.http.cookies

import android.content.Context
import android.content.SharedPreferences
import android.text.TextUtils
import com.zhuanghongji.wan.base_common.impl.App
import okhttp3.Cookie
import okhttp3.HttpUrl
import java.io.*
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import kotlin.collections.ArrayList

class PersistentCookieStore {

    private val TAG = "PersistentCookieStore"
    private val COOKIE_PREFS = "cookie_prefs"

    private val cookies: HashMap<String, ConcurrentHashMap<String, Cookie>> = HashMap()
    private val cookiesPrefs: SharedPreferences

    init {
        cookiesPrefs = App.context.getSharedPreferences(COOKIE_PREFS, Context.MODE_PRIVATE)
        val prefsMap = cookiesPrefs.all
        for (entry in prefsMap) {
            val cookieNames = TextUtils.split(entry.value as String, ",")
            for (name in cookieNames) {
                val encodeCookie = cookiesPrefs.getString(name, null) ?: continue
                val decodedCookie = decodeCookie(encodeCookie) ?: continue

                val key = entry.key
                if (cookies.containsKey(key)) {
                    cookies[key]?.put(name, decodedCookie)
                }
                cookies[key] = ConcurrentHashMap()
            }
        }
    }

    private fun getCookieToken(cookie: Cookie): String = cookie.name() + "@" + cookie.domain()

    fun add(url: HttpUrl, cookie: Cookie) {
        val name = getCookieToken(cookie)
        val host = url.host()

        // 将 Cookies 缓存到内存中，如果缓存过期则重置该 Cookie
        if (!cookie.persistent()) {
            if (!cookies.containsKey(host)) {
                cookies[host] = ConcurrentHashMap(10)
            }
            cookies[host]?.put(name, cookie)
        } else {
            if (cookies.containsKey(host)) {
                cookies[host]?.remove(name)
            }
        }
        // 将 Cookie 持久化到本地
        with(cookiesPrefs.edit()) {
            putString(host, TextUtils.join(",", cookies[host]?.entries))
            putString(name, encodeCookie(OkHttpCookies(cookie)))
            apply()
        }
    }

    fun getCookies(url: HttpUrl): List<Cookie> {
        val list: ArrayList<Cookie> = ArrayList()
        val host = url.host()
        if (cookies.containsKey(host)) {
            list.addAll(cookies[host]?.values!!)
        }
        return list
    }

    fun getAllCookies(): List<Cookie> {
        val result = ArrayList<Cookie>()
        for (key in cookies.keys) {
            result.addAll(cookies[key]?.values!!)
        }
        return result
    }

    fun remove(url: HttpUrl, cookie: Cookie): Boolean {
        val name = getCookieToken(cookie)
        val host = url.host()
        return if (cookies.containsKey(host) && cookies[host]?.containsKey(name)!!) {
            cookies[host]?.remove(name)
            with(cookiesPrefs.edit()) {
                if (cookiesPrefs.contains(name)) {
                    remove(name)
                }
                putString(host, TextUtils.join(",", cookies[host]?.keys))
                apply()
            }
            true
        } else {
            false
        }
    }

    fun clear() {
        cookies.clear()
        with(cookiesPrefs.edit()) {
            clear()
            apply()
        }
    }

    /**
     * 将 Cookie 对象序列化成字符串
     *
     * @param cookie 要被序列化的 Cookie
     * @return 序列后的字符串
     */
    private fun encodeCookie(cookie: OkHttpCookies?): String? {
        if (cookie == null) {
            return null
        }

        val os = ByteArrayOutputStream()
        try {
            val outputStream = ObjectOutputStream(os)
            outputStream.writeObject(cookie)
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        }
        return byteArrayToHexString(os.toByteArray())
    }

    /**
     * 将字符串反序列化成 Cookie
     *
     * @param cookieStr Cookie 字符串
     * @return Cookie object
     */
    private fun decodeCookie(cookieStr: String): Cookie? {
        val bytes = hexStringToByteArray(cookieStr)
        val byteArrayInputSteam = ByteArrayInputStream(bytes)

        var cookie: Cookie? = null
        try {
            val objectInputStream = ObjectInputStream(byteArrayInputSteam)
            cookie = (objectInputStream.readObject() as OkHttpCookies).getCookies()
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: ClassCastException) {
            e.printStackTrace()
        }
        return cookie
    }

    /**
     * 二进制数组转十六进制字符串
     *
     * @param bytes byte array to be converted
     * @return string containing hex values
     */
    private fun byteArrayToHexString(bytes: ByteArray): String {
        val sb = StringBuilder(bytes.size * 2)
        for (element in bytes) {
            val v = element.toInt() and 0xff
            if (v < 16) {
                sb.append('0')
            }
            sb.append(Integer.toHexString(v))
        }
        return sb.toString().toUpperCase(Locale.US)
    }

    /**
     * 十六进制字符串转二进制数组
     *
     * @param hexString string of hex-encoded values
     * @return decoded byte array
     */
    private fun hexStringToByteArray(hexString: String): ByteArray {
        val len = hexString.length
        val data = ByteArray(len / 2)
        var i = 0
        while (i < len) {
            data[i / 2] = ((Character.digit(hexString[i], 16) shl 4)
                    + Character.digit(hexString[i + 1], 16)).toByte()
            i += 2
        }
        return data
    }
}