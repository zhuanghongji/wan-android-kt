package com.zhuanghongji.wan.common.search.list

import com.zhuanghongji.wan.base_common.ext.ss
import com.zhuanghongji.wan.main.common.MainCommonPresenter

class SearchListPresenter : MainCommonPresenter<SearchListContract.Model, SearchListContract.View>(), SearchListContract.Presenter {

    override fun createModel(): SearchListContract.Model? = SearchListModel()

    override fun queryBySearchKey(page: Int, key: String) {
        mModel?.queryBySearchKey(page, key)?.ss(mModel, mView, page == 0) {
            mView?.showArticles(it.data)
        }
    }

}