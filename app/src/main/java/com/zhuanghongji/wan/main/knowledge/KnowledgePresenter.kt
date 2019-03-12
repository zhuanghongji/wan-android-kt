package com.zhuanghongji.wan.main.knowledge

import com.zhuanghongji.wan.base_common.ext.ss
import com.zhuanghongji.wan.main.common.MainCommonPresenter

class KnowledgePresenter : MainCommonPresenter<KnowledgeContract.Model, KnowledgeContract.View>(), KnowledgeContract.Presenter {

    override fun createModel(): KnowledgeContract.Model? = KnowledgeModel()

    override fun requestKnowledgeList(page: Int, cid: Int) {
        mModel?.requestKnowledgeList(page, cid)?.ss(mModel, mView) {
            mView?.setKnowledgeList(it.data)
        }
    }

}