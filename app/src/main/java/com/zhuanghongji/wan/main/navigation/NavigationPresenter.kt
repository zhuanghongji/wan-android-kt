package com.zhuanghongji.wan.main.navigation

import com.zhuanghongji.wan.base_common.ext.ss
import com.zhuanghongji.wan.base_common.mvp.BasePresenter
import com.zhuanghongji.wan.main.knowledge.tree.NavigationContract

class NavigationPresenter : BasePresenter<NavigationContract.Model, NavigationContract.View>(), NavigationContract.Presenter {

    override fun requestNavigations() {
        mModel?.requestNavigations()?.ss(mModel, mView) {
            mView?.setNavigations(it.data)
        }
    }

    override fun createModel(): NavigationContract.Model? = NavigationModel()
}