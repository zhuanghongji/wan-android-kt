package com.zhuanghongji.wan.search

import com.zhuanghongji.wan.base_common.api.ApiResult
import com.zhuanghongji.wan.base_common.api.datas.HotSearch
import com.zhuanghongji.wan.base_common.http.HttpManager
import com.zhuanghongji.wan.base_common.mvp.BaseModel
import io.reactivex.Observable

class SearchModel : BaseModel(), SearchContract.Model {

    override fun getHotSearchData(): Observable<ApiResult<MutableList<HotSearch>>> {
        return HttpManager.apiService.getHotSearchs()
    }

}