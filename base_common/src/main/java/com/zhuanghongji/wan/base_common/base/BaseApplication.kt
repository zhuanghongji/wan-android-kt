package com.zhuanghongji.wan.base_common.base

import android.app.Application

/**
 * 各个模块的
 *
 * 要想使用 BaseApplication，必须在组件中实现自己的 Application，并且继承BaseApplication；
 *
 * - 组件模块中实现的 Application 必须在 debug 包中的 AndroidManifest.xml 中注册，否则无法使用；
 * - 组件模块的 Application 需置于 java/debug 文件夹中，不得放于主代码；
 * - 组件模块中获取 Context 的方法必须为 `:ConstantUtils.getAPPContext()`，不允许其他写法；
 */
open class BaseApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        // 创建 Application 代理并执行相关代理方法
        val baseApplication = BaseAppDelegate(this)
        baseApplication.onCreate()
    }
}