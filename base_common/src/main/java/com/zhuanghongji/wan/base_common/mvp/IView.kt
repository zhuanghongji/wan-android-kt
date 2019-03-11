package com.zhuanghongji.wan.base_common.mvp

interface IView {

    /**
     * 显示加载中
     */
    fun showLoading()

    /**
     * 隐藏加载
     */
    fun hideLoading()

    /**
     * 使用默认的样式显示信息: CustomToast
     */
    fun showDefaultMessage(message: String)

    /**
     * 显示信息
     */
    fun showMessage(message: String)

    /**
     * 显示错误信息
     */
    fun showError(errorMessage: String)
}