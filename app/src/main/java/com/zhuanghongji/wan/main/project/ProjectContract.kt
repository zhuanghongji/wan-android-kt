package com.zhuanghongji.wan.main.knowledge.tree

import com.zhuanghongji.wan.base_common.api.ApiResult
import com.zhuanghongji.wan.base_common.api.datas.Project
import com.zhuanghongji.wan.base_common.mvp.IModel
import com.zhuanghongji.wan.base_common.mvp.IPresenter
import com.zhuanghongji.wan.base_common.mvp.IView
import io.reactivex.Observable

interface ProjectContract {

    interface View: IView {

        fun scrollToTop()
        fun setProjects(projects: List<Project>)
    }

    interface Presenter: IPresenter<View> {

        fun requestProjects()
    }

    interface Model: IModel {

        fun requestProjects(): Observable<ApiResult<List<Project>>>
    }
}