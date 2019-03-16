package com.zhuanghongji.wan.login

import com.zhuanghongji.wan.base_common.api.ApiResult
import com.zhuanghongji.wan.base_common.api.datas.LoginInfo
import com.zhuanghongji.wan.base_common.mvp.IModel
import com.zhuanghongji.wan.base_common.mvp.IPresenter
import com.zhuanghongji.wan.base_common.mvp.IView
import io.reactivex.Observable

interface LoginContract {

    interface View : IView {

        fun loginSuccess(data: LoginInfo)

        fun loginFail()
    }

    interface Presenter : IPresenter<View> {

        fun loginWanAndroid(username: String, password: String)
    }

    interface Model : IModel {

        fun loginWanAndroid(username: String, password: String): Observable<ApiResult<LoginInfo>>
    }
}