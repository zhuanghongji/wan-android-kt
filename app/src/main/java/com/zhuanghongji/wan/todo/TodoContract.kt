package com.zhuanghongji.wan.todo

import com.zhuanghongji.wan.base_common.api.ApiResult
import com.zhuanghongji.wan.base_common.api.datas.AllTodo
import com.zhuanghongji.wan.base_common.api.datas.Todos
import com.zhuanghongji.wan.base_common.mvp.IModel
import com.zhuanghongji.wan.base_common.mvp.IPresenter
import com.zhuanghongji.wan.base_common.mvp.IView
import io.reactivex.Observable

interface TodoContract {

    interface View : IView {

        fun showNoTodoList(todoResponseBody: Todos)

        fun showDeleteSuccess(success: Boolean)

        fun showUpdateSuccess(success: Boolean)

    }

    interface Presenter : IPresenter<View> {

        fun getAllTodoList(type: Int)

        fun getNoTodoList(page: Int, type: Int)

        fun getDoneList(page: Int, type: Int)

        fun deleteTodoById(id: Int)

        fun updateTodoById(id: Int, status: Int)

    }

    interface Model : IModel {

        fun getTodoList(type: Int): Observable<ApiResult<AllTodo>>

        fun getNoTodoList(page: Int, type: Int): Observable<ApiResult<Todos>>

        fun getDoneList(page: Int, type: Int): Observable<ApiResult<Todos>>

        fun deleteTodoById(id: Int): Observable<ApiResult<Any>>

        fun updateTodoById(id: Int, status: Int): Observable<ApiResult<Any>>

    }

}