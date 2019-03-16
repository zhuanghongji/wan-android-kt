package com.zhuanghongji.wan.register

import com.zhuanghongji.wan.base_common.api.ApiResult
import com.zhuanghongji.wan.base_common.api.datas.LoginInfo
import com.zhuanghongji.wan.base_common.mvp.IModel
import com.zhuanghongji.wan.base_common.mvp.IPresenter
import com.zhuanghongji.wan.base_common.mvp.IView
import io.reactivex.Observable

interface RegisterContract {

    interface View : IView {

        fun registerSuccess(data: LoginInfo)

        fun registerFail()
    }

    interface Presenter : IPresenter<View> {

        fun registerWanAndroid(username: String, password: String, repassword: String)

    }

    interface Model : IModel {
        fun registerWanAndroid(username: String, password: String, repassword: String)
                : Observable<ApiResult<LoginInfo>>
    }

}