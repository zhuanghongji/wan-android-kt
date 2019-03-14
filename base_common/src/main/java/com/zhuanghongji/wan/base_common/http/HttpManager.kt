package com.zhuanghongji.wan.base_common.http

import com.zhuanghongji.wan.base_common.BuildConfig
import com.zhuanghongji.wan.base_common.api.ApiConstant
import com.zhuanghongji.wan.base_common.api.ApiService
import com.zhuanghongji.wan.base_common.http.intercepter.CacheInterceptor
import com.zhuanghongji.wan.base_common.http.intercepter.CommonParamInterceptor
import com.zhuanghongji.wan.base_common.http.intercepter.CookieInterceptor
import com.zhuanghongji.wan.base_common.http.intercepter.HeaderInterceptor
import com.zhuanghongji.wan.base_common.impl.App
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

/**
 * Http 管理类
 */
object HttpManager {

    /** 最大缓存大小 50M */
    private const val MAX_CACHE_SIZE: Long = 1024 * 1024 * 50

    /** 默认超时时间(s) */
    private const val DEFAULT_TIMEOUT: Long = 15

    private var retrofit: Retrofit? = null

    val apiService: ApiService by lazy {
        getRetrofit()!!.create(ApiService::class.java)
    }

    private fun getRetrofit(): Retrofit? {
        if (retrofit == null) {
            synchronized(HttpManager::class.java) {
                if (retrofit == null) {
                    retrofit = Retrofit.Builder()
                        .baseUrl(ApiConstant.BASE_URL)
                        .client(getOkHttpClient())
                        .addConverterFactory(MoshiConverterFactory.create())
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .build()
                }
            }
        }
        return retrofit
    }

    private fun getOkHttpClient(): OkHttpClient {
        val builder = OkHttpClient().newBuilder()
        val httpLoginInterceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            httpLoginInterceptor.level = HttpLoggingInterceptor.Level.BODY
        } else {
            httpLoginInterceptor.level = HttpLoggingInterceptor.Level.NONE
        }

        // 设置请求的缓大小和位置
        val cacheFile = File(App.context.cacheDir, "cache")
        val cache = Cache(cacheFile, MAX_CACHE_SIZE)

        builder.run {
            addInterceptor(httpLoginInterceptor)
            addInterceptor(HeaderInterceptor())
            addInterceptor(CookieInterceptor())
            addInterceptor(CacheInterceptor())
            addInterceptor(CommonParamInterceptor())
                .cache(cache)
            connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            retryOnConnectionFailure(true)
        }
        return builder.build()
    }

}