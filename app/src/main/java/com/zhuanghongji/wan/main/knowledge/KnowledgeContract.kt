package com.zhuanghongji.wan.main.knowledge

import com.zhuanghongji.wan.base_common.api.ApiResult
import com.zhuanghongji.wan.base_common.api.datas.Articles
import com.zhuanghongji.wan.main.common.MainCommonContract
import io.reactivex.Observable

interface KnowledgeContract {

    interface View : MainCommonContract.View {

        fun scrollToTop()

        fun setKnowledgeList(articles: Articles)

    }

    interface Presenter : MainCommonContract.Presenter<View> {

        fun requestKnowledgeList(page: Int, cid: Int)

    }

    interface Model : MainCommonContract.Model {

        fun requestKnowledgeList(page: Int, cid: Int): Observable<ApiResult<Articles>>

    }

}