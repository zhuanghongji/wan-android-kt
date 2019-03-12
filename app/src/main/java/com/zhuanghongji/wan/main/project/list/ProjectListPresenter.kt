package com.zhuanghongji.wan.main.project.list

import com.zhuanghongji.wan.base_common.ext.ss
import com.zhuanghongji.wan.main.common.MainCommonPresenter

class ProjectListPresenter : MainCommonPresenter<ProjectListContract.Model, ProjectListContract.View>(), ProjectListContract.Presenter {

    override fun createModel(): ProjectListContract.Model? = ProjectListModel()

    override fun requestProjectList(page: Int, cid: Int) {
        mModel?.requestProjectList(page, cid)?.ss(mModel, mView, page == 1) {
            mView?.setProjectList(it.data)
        }
    }

}