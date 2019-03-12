package com.zhuanghongji.wan.main.knowledge

import com.zhuanghongji.wan.base_common.api.ApiResult
import com.zhuanghongji.wan.base_common.api.datas.Articles
import com.zhuanghongji.wan.base_common.http.HttpManager
import com.zhuanghongji.wan.main.common.MainCommonModel
import io.reactivex.Observable

class KnowledgeModel : MainCommonModel(), KnowledgeContract.Model {

    override fun requestKnowledgeList(page: Int, cid: Int): Observable<ApiResult<Articles>> {
        return HttpManager.apiService.getKnowledgeArticles(page, cid)
    }

}
