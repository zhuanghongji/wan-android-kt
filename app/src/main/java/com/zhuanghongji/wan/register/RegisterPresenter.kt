package com.zhuanghongji.wan.register

import com.zhuanghongji.wan.base_common.ext.ss
import com.zhuanghongji.wan.base_common.mvp.BasePresenter

class RegisterPresenter : BasePresenter<RegisterContract.Model, RegisterContract.View>(),
    RegisterContract.Presenter {

    override fun createModel(): RegisterContract.Model? = RegisterModel()

    override fun registerWanAndroid(username: String, password: String, repassword: String) {
        mModel?.registerWanAndroid(username, password, repassword)?.ss(mModel, mView) {
            mView?.apply {
                if (it.errorCode != 0) {
                    registerFail()
                } else {
                    registerSuccess(it.data)
                }
            }
        }
    }


}