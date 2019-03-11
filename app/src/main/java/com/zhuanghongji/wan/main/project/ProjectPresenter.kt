package com.zhuanghongji.wan.main.project

import com.zhuanghongji.wan.base_common.ext.ss
import com.zhuanghongji.wan.base_common.mvp.BasePresenter
import com.zhuanghongji.wan.main.knowledge.tree.ProjectContract

class ProjectPresenter : BasePresenter<ProjectContract.Model, ProjectContract.View>(), ProjectContract.Presenter {

    override fun createModel(): ProjectContract.Model? = ProjectModel()

    override fun requestProjects() {
        mModel?.requestProjects()?.ss(mModel, mView) {
            mView?.setProjects(it.data)
        }
    }

}