package com.zhuanghongji.wan.login

import com.zhuanghongji.wan.base_common.ext.ss
import com.zhuanghongji.wan.base_common.mvp.BasePresenter

class LoginPresenter : BasePresenter<LoginContract.Model, LoginContract.View>(), LoginContract.Presenter {

    override fun createModel(): LoginContract.Model? = LoginModel()

    override fun loginWanAndroid(username: String, password: String) {
        mModel?.loginWanAndroid(username, password)?.ss(mModel, mView) {
            mView?.loginSuccess(it.data)
        }
    }

}