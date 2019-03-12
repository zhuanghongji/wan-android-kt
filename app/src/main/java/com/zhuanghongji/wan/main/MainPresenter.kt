package com.zhuanghongji.wan.main

import com.zhuanghongji.wan.base_common.ext.ss
import com.zhuanghongji.wan.base_common.mvp.BasePresenter

class MainPresenter: BasePresenter<MainContract.Model, MainContract.View>(),
    MainContract.Presenter {



    override fun createModel(): MainContract.Model? {
        return MainModel()
    }

    override fun logout() {
        mModel?.logout()?.ss(mModel, mView) {
            mView?.showLogoutSuccess(true)
        }
    }
}