package com.zhuanghongji.wan.main.navigation

import com.zhuanghongji.wan.base_common.api.ApiResult
import com.zhuanghongji.wan.base_common.api.datas.Navigation
import com.zhuanghongji.wan.base_common.http.HttpManager
import com.zhuanghongji.wan.base_common.mvp.BaseModel
import com.zhuanghongji.wan.main.knowledge.tree.NavigationContract
import io.reactivex.Observable

class NavigationModel : BaseModel(), NavigationContract.Model {

    override fun requestNavigations(): Observable<ApiResult<List<Navigation>>> {
        return HttpManager.apiService.getNavigations()
    }
}