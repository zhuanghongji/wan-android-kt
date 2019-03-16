package com.zhuanghongji.wan.todo

import com.zhuanghongji.wan.base_common.api.ApiResult
import com.zhuanghongji.wan.base_common.api.datas.AllTodo
import com.zhuanghongji.wan.base_common.api.datas.Todos
import com.zhuanghongji.wan.base_common.http.HttpManager
import com.zhuanghongji.wan.base_common.mvp.BaseModel
import io.reactivex.Observable

class TodoModel : BaseModel(), TodoContract.Model {

    override fun getTodoList(type: Int): Observable<ApiResult<AllTodo>> {
        return HttpManager.apiService.getTodoList(type)
    }

    override fun getNoTodoList(page: Int, type: Int): Observable<ApiResult<Todos>> {
        return HttpManager.apiService.getNoTodoList(page, type)
    }

    override fun getDoneList(page: Int, type: Int): Observable<ApiResult<Todos>> {
        return HttpManager.apiService.getDoneList(page, type)
    }

    override fun deleteTodoById(id: Int): Observable<ApiResult<Any>> {
        return HttpManager.apiService.deleteTodoById(id)
    }

    override fun updateTodoById(id: Int, status: Int): Observable<ApiResult<Any>> {
        return HttpManager.apiService.updateTodoById(id, status)
    }

}