package com.zhuanghongji.wan.main.project

import com.zhuanghongji.wan.base_common.api.ApiResult
import com.zhuanghongji.wan.base_common.api.datas.Project
import com.zhuanghongji.wan.base_common.http.HttpManager
import com.zhuanghongji.wan.base_common.mvp.BaseModel
import com.zhuanghongji.wan.main.knowledge.tree.ProjectContract
import io.reactivex.Observable

class ProjectModel : BaseModel(), ProjectContract.Model {

    override fun requestProjects(): Observable<ApiResult<List<Project>>> {
        return HttpManager.apiService.getProjects()
    }

}