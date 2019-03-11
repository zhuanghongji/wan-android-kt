package com.zhuanghongji.wan

import com.zhuanghongji.wan.base_common.api.ApiResult
import com.zhuanghongji.wan.base_common.api.datas.Banner
import com.zhuanghongji.wan.base_common.mvp.IModel
import com.zhuanghongji.wan.base_common.mvp.IPresenter
import com.zhuanghongji.wan.base_common.mvp.IView
import io.reactivex.Observable

interface MainContract {

    interface View: IView {
        fun setBanner(banners: String)
    }

    interface Presenter: IPresenter<View> {
        fun requestBanner()
    }

    interface Model: IModel {
        fun getBanner(): Observable<ApiResult<List<Banner>>>
    }
}