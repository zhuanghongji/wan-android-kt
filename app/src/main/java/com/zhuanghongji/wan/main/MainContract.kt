package com.zhuanghongji.wan.main

import com.zhuanghongji.wan.base_common.api.ApiResult
import com.zhuanghongji.wan.base_common.mvp.IModel
import com.zhuanghongji.wan.base_common.mvp.IPresenter
import com.zhuanghongji.wan.base_common.mvp.IView
import io.reactivex.Observable

interface MainContract {

    interface View : IView {

        fun showLogoutSuccess(success: Boolean)
    }

    interface Presenter : IPresenter<View> {

        fun logout()
    }

    interface Model : IModel {

        fun logout(): Observable<ApiResult<Any>>
    }
}