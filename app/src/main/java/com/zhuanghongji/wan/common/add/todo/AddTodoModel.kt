package com.zhuanghongji.wan.common.add.todo

import com.zhuanghongji.wan.base_common.api.ApiResult
import com.zhuanghongji.wan.base_common.http.HttpManager
import com.zhuanghongji.wan.base_common.mvp.BaseModel
import io.reactivex.Observable

class AddTodoModel : BaseModel(), AddTodoContract.Model {

    override fun addTodo(map: MutableMap<String, Any>): Observable<ApiResult<Any>> {
        return HttpManager.apiService.addTodo(map)
    }

    override fun updateTodo(id: Int, map: MutableMap<String, Any>): Observable<ApiResult<Any>> {
        return HttpManager.apiService.updateTodo(id, map)
    }

}