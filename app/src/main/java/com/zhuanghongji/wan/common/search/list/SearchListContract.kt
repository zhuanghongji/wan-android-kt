package com.zhuanghongji.wan.common.search.list

import com.zhuanghongji.wan.base_common.api.ApiResult
import com.zhuanghongji.wan.base_common.api.datas.Articles
import com.zhuanghongji.wan.main.common.MainCommonContract
import io.reactivex.Observable

interface SearchListContract {

    interface View : MainCommonContract.View {

        fun showArticles(articles: Articles)

        fun scrollToTop()

    }

    interface Presenter : MainCommonContract.Presenter<View> {

        fun queryBySearchKey(page: Int, key: String)

    }

    interface Model : MainCommonContract.Model {

        fun queryBySearchKey(page: Int, key: String): Observable<ApiResult<Articles>>

    }

}