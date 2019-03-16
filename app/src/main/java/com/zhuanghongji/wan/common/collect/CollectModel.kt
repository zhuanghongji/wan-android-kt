package com.zhuanghongji.wan.common.collect

import com.zhuanghongji.wan.base_common.api.ApiResult
import com.zhuanghongji.wan.base_common.api.datas.ArticleCollection
import com.zhuanghongji.wan.base_common.api.datas.Collections
import com.zhuanghongji.wan.base_common.http.HttpManager
import com.zhuanghongji.wan.base_common.mvp.BaseModel
import io.reactivex.Observable

class CollectModel : BaseModel(), CollectContract.Model {

    override fun getCollectList(page: Int): Observable<ApiResult<Collections<ArticleCollection>>> {
        return HttpManager.apiService.getCollects(page)
    }

    override fun removeCollectArticle(id: Int, originId: Int): Observable<ApiResult<Any>> {
        return HttpManager.apiService.removeCollectArticle(id, originId)
    }

}