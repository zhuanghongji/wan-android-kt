package com.zhuanghongji.wan.todo

import com.zhuanghongji.wan.base_common.ext.ss
import com.zhuanghongji.wan.base_common.mvp.BasePresenter

class TodoPresenter : BasePresenter<TodoContract.Model, TodoContract.View>(), TodoContract.Presenter {

    override fun createModel(): TodoContract.Model? = TodoModel()

    override fun getAllTodoList(type: Int) {
        mModel?.getTodoList(type)?.ss(mModel, mView) {

        }
    }

    override fun getNoTodoList(page: Int, type: Int) {
        mModel?.getNoTodoList(page, type)?.ss(mModel, mView, page == 1) {
            mView?.showNoTodoList(it.data)
        }
    }

    override fun getDoneList(page: Int, type: Int) {
        mModel?.getDoneList(page, type)?.ss(mModel, mView, page == 1) {
            mView?.showNoTodoList(it.data)
        }
    }

    override fun deleteTodoById(id: Int) {
        mModel?.deleteTodoById(id)?.ss(mModel, mView) {
            mView?.showDeleteSuccess(true)
        }
    }

    override fun updateTodoById(id: Int, status: Int) {
        mModel?.updateTodoById(id, status)?.ss(mModel, mView) {
            mView?.showUpdateSuccess(true)
        }
    }

}