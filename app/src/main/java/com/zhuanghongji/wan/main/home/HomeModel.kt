package com.zhuanghongji.wan.main.home

import com.zhuanghongji.wan.base_common.api.ApiResult
import com.zhuanghongji.wan.base_common.api.datas.Article
import com.zhuanghongji.wan.base_common.api.datas.Articles
import com.zhuanghongji.wan.base_common.api.datas.Banner
import com.zhuanghongji.wan.base_common.http.HttpManager
import com.zhuanghongji.wan.main.common.MainCommonModel
import io.reactivex.Observable

class HomeModel : MainCommonModel(), HomeContract.Model {

    override fun requestBanner(): Observable<ApiResult<List<Banner>>> {
        return HttpManager.apiService.getBanner()
    }

    override fun requestTopArticles(): Observable<ApiResult<MutableList<Article>>> {
        return HttpManager.apiService.getTopArticles()
    }

    override fun requestArticles(num: Int): Observable<ApiResult<Articles>> {
        return HttpManager.apiService.getArticles(num)
    }

}