package com.zhuanghongji.wan.main

import com.zhuanghongji.wan.base_common.api.ApiResult
import com.zhuanghongji.wan.base_common.api.datas.Banner
import com.zhuanghongji.wan.base_common.http.HttpManager
import com.zhuanghongji.wan.base_common.mvp.BaseModel
import io.reactivex.Observable

class MainModel: BaseModel(), MainContract.Model {

    override fun getBanner(): Observable<ApiResult<List<Banner>>> {
        return HttpManager.apiService.getBanner()
    }
}