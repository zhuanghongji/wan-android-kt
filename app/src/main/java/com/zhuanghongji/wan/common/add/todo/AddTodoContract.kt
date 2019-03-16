package com.zhuanghongji.wan.common.add.todo

import com.zhuanghongji.wan.base_common.api.ApiResult
import com.zhuanghongji.wan.base_common.mvp.IModel
import com.zhuanghongji.wan.base_common.mvp.IPresenter
import com.zhuanghongji.wan.base_common.mvp.IView
import io.reactivex.Observable

interface AddTodoContract {

    interface View : IView {

        fun showAddTodo(success: Boolean)

        fun showUpdateTodo(success: Boolean)

        fun getType(): Int
        fun getCurrentDate(): String
        fun getTitle(): String
        fun getContent(): String
        fun getStatus(): Int
        fun getItemId(): Int
        fun getPriority(): String

    }

    interface Presenter : IPresenter<View> {

        fun addTodo()

        fun updateTodo(id: Int)

    }

    interface Model : IModel {

        fun addTodo(map: MutableMap<String, Any>): Observable<ApiResult<Any>>

        fun updateTodo(id: Int, map: MutableMap<String, Any>): Observable<ApiResult<Any>>

    }


}