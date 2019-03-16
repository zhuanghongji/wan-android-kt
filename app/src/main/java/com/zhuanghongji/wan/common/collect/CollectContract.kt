package com.zhuanghongji.wan.common.collect

import com.zhuanghongji.wan.base_common.api.ApiResult
import com.zhuanghongji.wan.base_common.api.datas.ArticleCollection
import com.zhuanghongji.wan.base_common.api.datas.Collections
import com.zhuanghongji.wan.base_common.mvp.IModel
import com.zhuanghongji.wan.base_common.mvp.IPresenter
import com.zhuanghongji.wan.base_common.mvp.IView
import io.reactivex.Observable

interface CollectContract {

    interface View : IView {

        fun setCollectList(articles: Collections<ArticleCollection>)

        fun showRemoveCollectSuccess(success: Boolean)

        fun scrollToTop()

    }

    interface Presenter : IPresenter<View> {

        fun getCollectList(page: Int)

        fun removeCollectArticle(id: Int, originId: Int)

    }

    interface Model : IModel {

        fun getCollectList(page: Int): Observable<ApiResult<Collections<ArticleCollection>>>

        fun removeCollectArticle(id: Int, originId: Int): Observable<ApiResult<Any>>

    }

}