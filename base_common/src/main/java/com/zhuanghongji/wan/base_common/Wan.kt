package com.zhuanghongji.wan.base_common

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager

@SuppressLint("StaticFieldLeak")
object Wan {

    var context: Context? = null

    fun init(context: Context) {
        this.context = context.applicationContext
    }

    fun getAppContext(): Context {
        if (context != null) return context!!

        throw NullPointerException("You should init context first before get it.")
    }

    /**
     * 判断当前 App 是否是 Debug 版本
     *
     * @return `true` 是；`false` 不是
     */
    fun isAppDebug(): Boolean {
        val context = getAppContext()
        val packageName = context.packageName
        if (packageName.isEmpty()) {
            return false
        }
        return try {
            val pm = context.packageManager
            val ai = pm.getApplicationInfo(packageName, 0)
            0 != ai.flags and ApplicationInfo.FLAG_DEBUGGABLE
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
            false
        }
    }
}