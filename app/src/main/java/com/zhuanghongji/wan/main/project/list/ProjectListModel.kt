package com.zhuanghongji.wan.main.project.list

import com.zhuanghongji.wan.base_common.api.ApiResult
import com.zhuanghongji.wan.base_common.api.datas.Articles
import com.zhuanghongji.wan.base_common.http.HttpManager
import com.zhuanghongji.wan.main.common.MainCommonModel
import io.reactivex.Observable

class ProjectListModel : MainCommonModel(), ProjectListContract.Model {

    override fun requestProjectList(page: Int, cid: Int): Observable<ApiResult<Articles>> {
        return HttpManager.apiService.getProjectArticles(page, cid)
    }

}