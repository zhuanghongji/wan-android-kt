package com.zhuanghongji.wan.content

import com.zhuanghongji.wan.main.common.MainCommonContract

interface ContentContract {

    interface View : MainCommonContract.View {

    }

    interface Presenter : MainCommonContract.Presenter<View> {

    }

    interface Model : MainCommonContract.Model {

    }

}