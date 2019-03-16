package com.zhuanghongji.wan.common.search.list

import com.zhuanghongji.wan.base_common.api.ApiResult
import com.zhuanghongji.wan.base_common.api.datas.Articles
import com.zhuanghongji.wan.base_common.http.HttpManager
import com.zhuanghongji.wan.main.common.MainCommonModel
import io.reactivex.Observable

class SearchListModel : MainCommonModel(), SearchListContract.Model {

    override fun queryBySearchKey(page: Int, key: String): Observable<ApiResult<Articles>> {
        return HttpManager.apiService.queryBySearchKey(page, key)
    }

}