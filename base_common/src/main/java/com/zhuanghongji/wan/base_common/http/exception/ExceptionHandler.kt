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

        fun handleException(e: Throwable): String {
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
            return errorMsg
        }
    }
}