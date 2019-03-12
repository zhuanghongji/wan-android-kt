package com.zhuanghongji.wan.main.project.list

import com.zhuanghongji.wan.base_common.api.ApiResult
import com.zhuanghongji.wan.base_common.api.datas.Articles
import com.zhuanghongji.wan.main.common.MainCommonContract
import io.reactivex.Observable

interface ProjectListContract {

    interface View : MainCommonContract.View {

        fun scrollToTop()

        fun setProjectList(articles: Articles)

    }

    interface Presenter : MainCommonContract.Presenter<View> {

        fun requestProjectList(page: Int, cid: Int)

    }

    interface Model : MainCommonContract.Model {

        fun requestProjectList(page: Int, cid: Int): Observable<ApiResult<Articles>>

    }

}