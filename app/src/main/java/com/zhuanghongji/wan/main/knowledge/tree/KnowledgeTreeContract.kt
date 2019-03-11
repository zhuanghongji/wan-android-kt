package com.zhuanghongji.wan.main.knowledge.tree

import com.zhuanghongji.wan.base_common.api.ApiResult
import com.zhuanghongji.wan.base_common.api.datas.Knowledges
import com.zhuanghongji.wan.base_common.mvp.IModel
import com.zhuanghongji.wan.base_common.mvp.IPresenter
import com.zhuanghongji.wan.base_common.mvp.IView
import io.reactivex.Observable

interface KnowledgeTreeContract {

    interface View: IView {

        fun scrollToTop()
        fun setKnowledgeTree(knowledgeTree: List<Knowledges>)
    }

    interface Presenter: IPresenter<View> {

        fun requestKnowledgeTree()
    }

    interface Model: IModel {

        fun requestKnowledgeTree(): Observable<ApiResult<List<Knowledges>>>
    }
}