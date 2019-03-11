package com.zhuanghongji.wan.main.home

import com.zhuanghongji.wan.base_common.api.ApiResult
import com.zhuanghongji.wan.base_common.api.datas.Article
import com.zhuanghongji.wan.base_common.api.datas.Articles
import com.zhuanghongji.wan.base_common.ext.ss
import com.zhuanghongji.wan.main.common.MainCommonPresenter
import com.zhuanghongji.wan.manager.SettingManager
import io.reactivex.Observable
import io.reactivex.functions.BiFunction

class HomePresenter: MainCommonPresenter<HomeContract.Model, HomeContract.View>(), HomeContract.Presenter {

    override fun requestBanner() {
        mModel?.requestBanner()?.ss(mModel, mView, false) {
            mView?.setBanner(it.data)
        }
    }

    override fun requestHomeData() {
        requestBanner()

        val observable = if (SettingManager.getIsShowTopArticle()) {
            mModel?.requestArticles(0)
        } else {
            Observable.zip(mModel?.requestTopArticles(), mModel?.requestArticles(0),
                BiFunction<ApiResult<MutableList<Article>>, ApiResult<Articles>,
                        ApiResult<Articles>> { t1, t2 ->
                    t1.data.forEach {
                        // 置顶数据中没有标识，手动添加一个标识
                        it.top = "1"
                    }
                    t2.data.datas.addAll(0, t1.data)
                    t2
                })
        }
        observable?.ss(mModel, mView, false) {
            mView?.setArticles(it.data)
        }
    }

    override fun requestArticles(num: Int) {
        mModel?.requestArticles(num)?.ss(mModel, mView, num == 0) {
            mView?.setArticles(it.data)
        }
    }
}