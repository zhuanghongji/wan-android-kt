package com.zhuanghongji.wan.main.knowledge.tree

import com.zhuanghongji.wan.base_common.api.ApiResult
import com.zhuanghongji.wan.base_common.api.datas.Knowledges
import com.zhuanghongji.wan.base_common.http.HttpManager
import com.zhuanghongji.wan.base_common.mvp.BaseModel
import io.reactivex.Observable

class KnowledgeTreeModel : BaseModel(), KnowledgeTreeContract.Model {

    override fun requestKnowledgeTree(): Observable<ApiResult<List<Knowledges>>> {
        return HttpManager.apiService.getKnowledgeTree()
    }

}