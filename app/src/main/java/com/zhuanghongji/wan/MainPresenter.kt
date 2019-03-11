package com.zhuanghongji.wan

import com.zhuanghongji.wan.base_common.ext.ss
import com.zhuanghongji.wan.base_common.mvp.BasePresenter

class MainPresenter: BasePresenter<MainContract.Model, MainContract.View>(), MainContract.Presenter {

    override fun createModel(): MainContract.Model? {
        return MainModel()
    }

    override fun requestBanner() {
        mModel?.getBanner()?.ss(mModel, mView) {
            mView?.setBanner("$it")
        }
    }
}