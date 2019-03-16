package com.zhuanghongji.wan.content

import com.zhuanghongji.wan.main.common.MainCommonPresenter

class ContentPresenter : MainCommonPresenter<ContentContract.Model, ContentContract.View>(), ContentContract.Presenter {

    override fun createModel(): ContentContract.Model? = ContentModel()

}