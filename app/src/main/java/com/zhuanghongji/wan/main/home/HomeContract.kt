package com.zhuanghongji.wan.main.home

import com.zhuanghongji.wan.base_common.api.ApiResult
import com.zhuanghongji.wan.base_common.api.datas.Article
import com.zhuanghongji.wan.base_common.api.datas.Articles
import com.zhuanghongji.wan.base_common.api.datas.Banner
import com.zhuanghongji.wan.main.common.MainCommonContract
import io.reactivex.Observable

interface HomeContract {

    interface View: MainCommonContract.View {

        fun scrollToTop()
        fun setBanner(banners: List<Banner>)
        fun setArticles(articles: Articles)
    }

    interface Presenter: MainCommonContract.Presenter<View> {

        fun requestBanner()
        fun requestHomeData()
        fun requestArticles(num: Int)
    }

    interface Model: MainCommonContract.Model {

        fun requestBanner(): Observable<ApiResult<List<Banner>>>
        fun requestTopArticles(): Observable<ApiResult<MutableList<Article>>>
        fun requestArticles(num: Int): Observable<ApiResult<Articles>>
    }
}