package com.zhuanghongji.wan.search

import com.zhuanghongji.wan.base_common.api.datas.SearchHistory
import com.zhuanghongji.wan.base_common.ext.ss
import com.zhuanghongji.wan.base_common.mvp.BasePresenter
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import org.litepal.LitePal

class SearchPresenter : BasePresenter<SearchContract.Model, SearchContract.View>(), SearchContract.Presenter {


    override fun createModel(): SearchContract.Model? = SearchModel()

    override fun deleteById(id: Long) {
        doAsync {
            LitePal.delete(SearchHistory::class.java, id)
        }

    }

    override fun clearAllHistory() {
        doAsync {
            LitePal.deleteAll(SearchHistory::class.java)
            uiThread {

            }
        }
    }

    override fun saveSearchKey(key: String) {
        doAsync {
            val historyBean = SearchHistory(key.trim())
            val beans = LitePal.where("key = '${key.trim()}'").find(SearchHistory::class.java)
            if (beans.size == 0) {
                historyBean.save()
            } else {
                deleteById(beans[0].id)
                historyBean.save()
            }
        }
    }

    override fun queryHistory() {
        doAsync {
            val historyBeans = LitePal.findAll(SearchHistory::class.java)
            historyBeans.reverse()
            uiThread {
                mView?.showHistoryData(historyBeans)
            }
        }
    }

    override fun getHotSearchData() {
        mModel?.getHotSearchData()?.ss(mModel, mView) {
            mView?.showHotSearchData(it.data)
        }
    }

}