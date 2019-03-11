package com.zhuanghongji.wan.main.wechat

import com.zhuanghongji.wan.base_common.ext.ss
import com.zhuanghongji.wan.base_common.mvp.BasePresenter
import com.zhuanghongji.wan.main.knowledge.tree.WeChatContract

class WeChatPresenter : BasePresenter<WeChatContract.Model, WeChatContract.View>(), WeChatContract.Presenter {

    override fun createModel(): WeChatContract.Model? = WeChatModel()

    override fun requestWxChapters() {
        mModel?.requestWxChapters()?.ss(mModel, mView) {
            mView?.setWxChapters(it.data)
        }
    }

}