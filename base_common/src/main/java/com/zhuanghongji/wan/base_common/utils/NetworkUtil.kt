package com.zhuanghongji.wan.base_common.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.telephony.TelephonyManager
import java.io.IOException
import java.net.HttpURLConnection
import java.net.NetworkInterface
import java.net.SocketException
import java.net.URL

/**
 * 网络相关工具类
 */
class NetworkUtil {

    companion object {

        /** Network is available */
        var NET_CNNT_OK = 1

        /** Network is timeout */
        var NET_CNNT_TIMEOUT = 2

        /** Network is not ready */
        var NET_NOT_PREPARE = 3

        /** Network error */
        var NET_ERROR = 4

        /** 网络超时的事件(ms) */
        private const val TIMEOUT_VALUE = 3000

        /**
         * 检查当前网络是否可用
         *
         * @return true 可用；false 不可用
         */
        @JvmStatic
        fun isNetworkAvailable(context: Context): Boolean {
            val manager = context.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE)
                    as ConnectivityManager
            val info = manager.activeNetworkInfo
            return info != null && info.isAvailable
        }

        /**
         * 检查当前网络是否已连接
         *
         * @return true 已连接；false 未连接
         */
        @JvmStatic
        fun isNetworkConnected(context: Context): Boolean {
            val manager = context.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE)
                    as ConnectivityManager
            val info = manager.activeNetworkInfo
            return info != null && info.isConnected
        }

        /**
         * 获取 IP 地址
         *
         * @return IP 地址
         */
        @JvmStatic
        fun getLocalIpAddress(): String {
            var ret = ""
            try {
                val en = NetworkInterface.getNetworkInterfaces()
                while (en.hasMoreElements()) {
                    val enumIpAddress = en.nextElement().inetAddresses
                    while (enumIpAddress.hasMoreElements()) {
                        val netAddress = enumIpAddress.nextElement()
                        if (!netAddress.isLoopbackAddress) {
                            ret = netAddress.hostAddress.toString()
                       }
                    }
                }
            } catch (ex: SocketException) {
                ex.printStackTrace()
            }

            return ret
        }


        /**
         * ping "http://www.baidu.com"
         *
         * @return true success
         */
        @JvmStatic
        private fun pingNetWork(): Boolean {
            var result = false
            var httpUrl: HttpURLConnection? = null
            try {
                httpUrl = URL("http://www.baidu.com")
                    .openConnection() as HttpURLConnection
                httpUrl.connectTimeout = TIMEOUT_VALUE
                httpUrl.connect()
                result = true
            } catch (e: IOException) {
            } finally {
                if (null != httpUrl) {
                    httpUrl.disconnect()
                }
            }
            return result
        }

        /**
         * check is3G
         *
         * @param context
         * @return boolean
         */
        @JvmStatic
        fun is3G(context: Context): Boolean {
            val connectivityManager = context
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetInfo = connectivityManager.activeNetworkInfo
            return activeNetInfo != null && activeNetInfo.type == ConnectivityManager.TYPE_MOBILE
        }

        /**
         * isWifi
         *
         * @param context
         * @return boolean
         */
        @JvmStatic
        fun isWifi(context: Context): Boolean {
            val connectivityManager = context
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetInfo = connectivityManager.activeNetworkInfo
            return activeNetInfo != null && activeNetInfo.type == ConnectivityManager.TYPE_WIFI
        }

        /**
         * is2G
         *
         * @param context
         * @return boolean
         */
        @JvmStatic
        fun is2G(context: Context): Boolean {
            val connectivityManager = context
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetInfo = connectivityManager.activeNetworkInfo
            return activeNetInfo != null && (activeNetInfo.subtype == TelephonyManager.NETWORK_TYPE_EDGE
                    || activeNetInfo.subtype == TelephonyManager.NETWORK_TYPE_GPRS || activeNetInfo
                .subtype == TelephonyManager.NETWORK_TYPE_CDMA)
        }

        /**
         * is wifi on
         */
        @JvmStatic
        fun isWifiEnabled(context: Context): Boolean {
            val mgrConn = context
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val mgrTel = context
                .getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            return mgrConn.activeNetworkInfo != null && mgrConn
                .activeNetworkInfo.state == NetworkInfo.State.CONNECTED || mgrTel
                .networkType == TelephonyManager.NETWORK_TYPE_UMTS
        }

        /**
         * 判断 MOBILE 网络是否可用
         */
        fun isMobile(context: Context?): Boolean {
            if (context != null) {
                // 获取手机所有连接管理对象(包括对 wi-fi, net 等连接的管理)
                val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                // 获取 NetworkInfo 对象
                val networkInfo = manager.activeNetworkInfo
                // 判断 NetworkInfo 对象是否为空 并且类型是否为 MOBILE
                if (null != networkInfo && networkInfo.type == ConnectivityManager.TYPE_MOBILE) {
                    return networkInfo.isAvailable
                }
            }
            return false
        }
    }
}