package com.zhuanghongji.wan.search

import com.zhuanghongji.wan.base_common.api.ApiResult
import com.zhuanghongji.wan.base_common.api.datas.HotSearch
import com.zhuanghongji.wan.base_common.api.datas.SearchHistory
import com.zhuanghongji.wan.base_common.mvp.IModel
import com.zhuanghongji.wan.base_common.mvp.IPresenter
import com.zhuanghongji.wan.base_common.mvp.IView
import io.reactivex.Observable

interface SearchContract {

    interface View : IView {

        fun showHistoryData(historyBeans: MutableList<SearchHistory>)

        fun showHotSearchData(hotSearchDatas: MutableList<HotSearch>)

    }

    interface Presenter : IPresenter<View> {

        fun queryHistory()

        fun saveSearchKey(key: String)

        fun deleteById(id: Long)

        fun clearAllHistory()

        fun getHotSearchData()

    }

    interface Model : IModel {

        fun getHotSearchData(): Observable<ApiResult<MutableList<HotSearch>>>

    }
}