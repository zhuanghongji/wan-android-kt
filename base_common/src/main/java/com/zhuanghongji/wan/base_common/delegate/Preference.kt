package com.zhuanghongji.wan.base_common.delegate

import android.content.Context
import android.content.SharedPreferences
import com.zhuanghongji.wan.base_common.impl.App
import java.io.*
import kotlin.reflect.KProperty

/**
 * Kotlin 委托属性 + SharePreference 实例
 */
class Preference<T>(

    /** name*/
    val name: String,

    /** default value if not found */
    private val default: T
) {
    companion object {

        private val fileName = "wan_share_preference"

        private val prefs: SharedPreferences by lazy {
            App.context.getSharedPreferences(fileName, Context.MODE_PRIVATE)
        }

        /**
         * Clear all SharePreference data
         */
        fun clear() {
            prefs.edit().clear().apply()
        }

        /**
         * Remove data by key
         */
        fun remove(key: String) {
            prefs.edit().remove(key).apply()
        }

        /**
         * Check if key exits
         *
         * @return `true` when it exits, else `false`
         */
        fun contains(key: String): Boolean {
            return prefs.contains(key)
        }

        /**
         * Get all key and value from the SharePreference
         */
        fun getAll(): Map<String, *> {
            return prefs.all
        }
    }

    operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return get(name, default)
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        put(name, value)
    }

    @Suppress("UNCHECKED_CAST", "NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    private fun get(name: String, default: T): T = with(prefs) {
        val res: Any = when (default) {
            is Long -> getLong(name, default)
            is String -> getString(name, default)
            is Int -> getInt(name, default)
            is Boolean -> getBoolean(name, default)
            is Float -> getFloat(name, default)
            else -> deSerialization(getString(name, serialize(default)))
        }
        return res as T
    }

    private fun put(name: String, value: T) = with(prefs.edit()) {
        when (value) {
            is Long -> putLong(name, value)
            is String -> putString(name, value)
            is Int -> putInt(name, value)
            is Boolean -> putBoolean(name, value)
            is Float -> putFloat(name, value)
            else -> putString(name, serialize(value))
        }.apply()
    }

    /**
     * 序列化对象
     *
     * @throws IOException
     */
    @Throws(IOException::class)
    private fun <A> serialize(obj: A): String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        val objectOutputStream = ObjectOutputStream(
            byteArrayOutputStream)
        objectOutputStream.writeObject(obj)
        var serStr = byteArrayOutputStream.toString("ISO-8859-1")
        serStr = java.net.URLEncoder.encode(serStr, "UTF-8")
        objectOutputStream.close()
        byteArrayOutputStream.close()
        return serStr
    }

    /**
     * 反序列化对象
     *
     * @param str
     * @throws IOException
     * @throws ClassNotFoundException
     */
    @Suppress("UNCHECKED_CAST")
    @Throws(IOException::class, ClassNotFoundException::class)
    private fun <A> deSerialization(str: String): A {
        val redStr = java.net.URLDecoder.decode(str, "UTF-8")
        val byteArrayInputStream = ByteArrayInputStream(
            redStr.toByteArray(charset("ISO-8859-1")))
        val objectInputStream = ObjectInputStream(
            byteArrayInputStream)
        val obj = objectInputStream.readObject() as A
        objectInputStream.close()
        byteArrayInputStream.close()
        return obj
    }
}