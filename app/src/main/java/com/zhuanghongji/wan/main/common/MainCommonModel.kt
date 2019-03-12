package com.zhuanghongji.wan.main.common

import com.zhuanghongji.wan.base_common.api.ApiResult
import com.zhuanghongji.wan.base_common.http.HttpManager
import com.zhuanghongji.wan.base_common.mvp.BaseModel
import io.reactivex.Observable

open class MainCommonModel : BaseModel(), MainCommonContract.Model {

    override fun addCollectArticle(id: Int): Observable<ApiResult<Any>> {
        return HttpManager.apiService.addCollectArticle(id)
    }

    override fun cancelCollectArticle(id: Int): Observable<ApiResult<Any>> {
        return HttpManager.apiService.cancelCollectArticle(id)
    }

}