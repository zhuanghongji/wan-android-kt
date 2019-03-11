package com.zhuanghongji.wan.main.knowledge.tree

import com.zhuanghongji.wan.base_common.api.ApiResult
import com.zhuanghongji.wan.base_common.api.datas.Navigation
import com.zhuanghongji.wan.base_common.mvp.IModel
import com.zhuanghongji.wan.base_common.mvp.IPresenter
import com.zhuanghongji.wan.base_common.mvp.IView
import io.reactivex.Observable

interface NavigationContract {

    interface View: IView {

        fun scrollToTop()
        fun setNavigations(navigations: List<Navigation>)
    }

    interface Presenter: IPresenter<View> {

        fun requestNavigations()
    }

    interface Model: IModel {

        fun requestNavigations(): Observable<ApiResult<List<Navigation>>>
    }
}