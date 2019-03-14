package com.zhuanghongji.wan.base_common.http.exception

import com.alibaba.fastjson.JSONException
import com.alibaba.fastjson.JSONPathException
import com.orhanobut.logger.Logger
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.text.ParseException

class ExceptionHandler {

    companion object {
        private const val TAG = "ExceptionHandler"
        var errorCode = ErrorStatus.UNKNOWN_ERROR
        var errorMsg = "请求失败，请稍后重试"

        /**
         * 根据给定 [Throwable] 类型参数 [e] 的具体类型的类获取异常描述语
         *
         * 用于网络请求失败时。
         *
         * @return Pair(errorCode, errorMsg)
         */
        fun getExceptionPair(e: Throwable): Pair<Int, String> {
            e.printStackTrace()

            when(e) {
                is SocketTimeoutException, is ConnectException, is HttpException, is UnknownHostException -> {
                    errorCode = ErrorStatus.NETWORK_ERROR
                    errorMsg = "网络连接异常"
                }
                is JSONPathException, is JSONException, is ParseException -> {
                    errorCode = ErrorStatus.SERVER_ERROR
                    errorMsg = "数据解析异常"
                }
                is ApiException -> {
                    errorCode = ErrorStatus.SERVER_ERROR
                    errorMsg = e.message.toString()
                }
                is IllegalArgumentException -> {
                    errorCode = ErrorStatus.SERVER_ERROR
                    errorMsg = "参数错误"
                }
                else -> {
                    errorMsg = "未知错误"
                    errorCode = ErrorStatus.UNKNOWN_ERROR
                }
            }
            Logger.e(TAG, "$errorMsg：%s", e.message)
            return Pair(errorCode, errorMsg)
        }
    }
}