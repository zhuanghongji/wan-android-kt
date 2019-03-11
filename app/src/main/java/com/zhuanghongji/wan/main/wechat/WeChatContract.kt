package com.zhuanghongji.wan.main.knowledge.tree

import com.zhuanghongji.wan.base_common.api.ApiResult
import com.zhuanghongji.wan.base_common.api.datas.WxChapter
import com.zhuanghongji.wan.base_common.mvp.IModel
import com.zhuanghongji.wan.base_common.mvp.IPresenter
import com.zhuanghongji.wan.base_common.mvp.IView
import io.reactivex.Observable

interface WeChatContract {

    interface View: IView {

        fun scrollToTop()
        fun setWxChapters(banners: MutableList<WxChapter>)
    }

    interface Presenter: IPresenter<View> {

        fun requestWxChapters()
    }

    interface Model: IModel {

        fun requestWxChapters(): Observable<ApiResult<MutableList<WxChapter>>>
    }
}