package com.zhuanghongji.wan.login

import com.zhuanghongji.wan.base_common.api.ApiResult
import com.zhuanghongji.wan.base_common.api.datas.LoginInfo
import com.zhuanghongji.wan.base_common.http.HttpManager
import com.zhuanghongji.wan.base_common.mvp.BaseModel
import io.reactivex.Observable

class LoginModel : BaseModel(), LoginContract.Model {

    override fun loginWanAndroid(username: String, password: String): Observable<ApiResult<LoginInfo>> {

        return HttpManager.apiService.login(username, password)
    }

}