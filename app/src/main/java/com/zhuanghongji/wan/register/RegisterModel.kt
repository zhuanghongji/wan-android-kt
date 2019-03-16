package com.zhuanghongji.wan.register

import com.zhuanghongji.wan.base_common.api.ApiResult
import com.zhuanghongji.wan.base_common.api.datas.LoginInfo
import com.zhuanghongji.wan.base_common.http.HttpManager
import com.zhuanghongji.wan.base_common.mvp.BaseModel
import io.reactivex.Observable

class RegisterModel : BaseModel(), RegisterContract.Model {

    override fun registerWanAndroid(username: String, password: String, repassword: String):
            Observable<ApiResult<LoginInfo>> {
        return HttpManager.apiService.register(username, password, repassword)
    }

}