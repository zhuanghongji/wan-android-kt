package com.zhuanghongji.wan.main.common

import com.zhuanghongji.wan.base_common.api.ApiResult
import com.zhuanghongji.wan.base_common.mvp.IModel
import com.zhuanghongji.wan.base_common.mvp.IPresenter
import com.zhuanghongji.wan.base_common.mvp.IView
import io.reactivex.Observable

interface MainCommonContract {

    interface View: IView {

        fun showCollectSuccess(success: Boolean)
        fun showCancelCollectSuccess(success: Boolean)
    }

    interface Presenter<V: View>: IPresenter<V> {

        fun addCollectArticle(id: Int)
        fun cancelCollectArticle(id: Int)
    }

    interface Model: IModel {

        fun addCollectArticle(id: Int): Observable<ApiResult<Any>>
        fun cancelCollectArticle(id: Int): Observable<ApiResult<Any>>
    }
}