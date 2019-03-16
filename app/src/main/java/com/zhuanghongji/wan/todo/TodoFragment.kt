package com.zhuanghongji.wan.todo

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.zhuanghongji.wan.R
import com.zhuanghongji.wan.base_common.api.datas.Todos
import com.zhuanghongji.wan.base_common.base.BaseMvpFragment
import com.zhuanghongji.wan.base_common.constants.PreferenceConstant
import com.zhuanghongji.wan.base_common.ext.showSnackbar
import com.zhuanghongji.wan.base_common.ext.toast
import com.zhuanghongji.wan.base_common.impl.App
import com.zhuanghongji.wan.base_common.utils.NetworkUtil
import com.zhuanghongji.wan.common.CommonActivity
import com.zhuanghongji.wan.event.RefreshTodoEvent
import com.zhuanghongji.wan.event.TodoEvent
import com.zhuanghongji.wan.event.TodoTypeEvent
import com.zhuanghongji.wan.utils.DialogUtil
import com.zhuanghongji.wan.widget.SpaceItemDecoration
import com.zhuanghongji.wan.widget.SwipeItemLayout
import org.greenrobot.eventbus.ThreadMode

import kotlinx.android.synthetic.main.fragment_todo.*
import org.greenrobot.eventbus.Subscribe

class TodoFragment : BaseMvpFragment<TodoContract.View, TodoContract.Presenter>(), TodoContract.View {

    companion object {
        fun getInstance(type: Int): TodoFragment {
            val fragment = TodoFragment()
            val bundle = Bundle()
            bundle.putInt(PreferenceConstant.TODO_TYPE, type)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun createPresenter(): TodoContract.Presenter = TodoPresenter()

    /**
     * is Refresh
     */
    private var isRefresh = true

    private var mType: Int = 0

    /**
     * 是否是已完成 false->待办 true->已完成
     */
    private var bDone: Boolean = false

    private val datas = mutableListOf<TodoDataBean>()

    private val mAdapter: TodoAdapter by lazy {
        TodoAdapter(R.layout.item_todo_list, R.layout.item_sticky_header, datas)
    }

    /**
     * LinearLayoutManager
     */
    private val linearLayoutManager: LinearLayoutManager by lazy {
        LinearLayoutManager(activity)
    }

    /**
     * RecyclerView Divider
     */
    private val recyclerViewItemDecoration by lazy {
        activity?.let {
            SpaceItemDecoration(it)
        }
    }

    override fun showLoading() {
        // swipeRefreshLayout?.isRefreshing = false
    }

    override fun hideLoading() {
        swipeRefreshLayout?.isRefreshing = false
        if (isRefresh) {
            mAdapter.run {
                setEnableLoadMore(true)
            }
        }
    }

    override fun showError(errorMsg: String) {
        super.showError(errorMsg)
        mLayoutStatusView?.showError()
        mAdapter.run {
            if (isRefresh)
                setEnableLoadMore(true)
            else
                loadMoreFail()
        }
    }

    override fun getLayoutResID(): Int = R.layout.fragment_todo

    override fun initView(view: View) {
        // super.initView(view)
        mLayoutStatusView = multiple_status_view
        mType = arguments?.getInt(PreferenceConstant.TODO_TYPE) ?: 0

        swipeRefreshLayout.run {
            setOnRefreshListener(onRefreshListener)
        }
        recyclerView.run {
            layoutManager = linearLayoutManager
            adapter = mAdapter
            itemAnimator = DefaultItemAnimator()
            recyclerViewItemDecoration?.let { addItemDecoration(it) }
            addOnItemTouchListener(SwipeItemLayout.OnSwipeItemTouchListener(activity))
        }
        mAdapter.run {
            bindToRecyclerView(recyclerView)
            setOnLoadMoreListener(onRequestLoadMoreListener, recyclerView)
            onItemClickListener = this@TodoFragment.onItemClickListener
            onItemChildClickListener = this@TodoFragment.onItemChildClickListener
            // setEmptyView(R.layout.fragment_empty_layout)
        }

    }

    override fun lazyLoad() {
        mLayoutStatusView?.showLoading()
        if (bDone) {
            mPresenter?.getDoneList(1, mType)
        } else {
            mPresenter?.getNoTodoList(1, mType)
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun doTodoTypeEvent(event: TodoTypeEvent) {
        mType = event.type
        bDone = false
        lazyLoad()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun doTodoEvent(event: TodoEvent) {
        if (mType == event.curIndex) {
            when (event.type) {
                PreferenceConstant.TODO_ADD -> {
                    Intent(activity, CommonActivity::class.java).run {
                        putExtra(PreferenceConstant.TYPE_KEY, PreferenceConstant.Type.ADD_TODO_TYPE_KEY)
                        putExtra(PreferenceConstant.TODO_TYPE, mType)
                        startActivity(this)
                    }
                }
                PreferenceConstant.TODO_NO -> {
                    bDone = false
                    lazyLoad()
                }
                PreferenceConstant.TODO_DONE -> {
                    bDone = true
                    lazyLoad()
                }
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun doRefresh(event: RefreshTodoEvent) {
        if (event.isRefresh) {
            if (mType == event.type) {
                lazyLoad()
            }
        }
    }

    override fun showNoTodoList(todoResponseBody: Todos) {
        // TODO 待优化
        val list = mutableListOf<TodoDataBean>()
        var bHeader = true
        todoResponseBody.datas.forEach { todoBean ->
            bHeader = true
            for (i in list.indices) {
                if (todoBean.dateStr == list[i].header) {
                    bHeader = false
                    break
                }
            }
            if (bHeader)
                list.add(TodoDataBean(true, todoBean.dateStr))
            list.add(TodoDataBean(todoBean))
        }

        list.let {
            mAdapter.run {
                if (isRefresh) {
                    replaceData(it)
                } else {
                    addData(it)
                }
                val size = it.size
                if (size < todoResponseBody.size) {
                    loadMoreEnd(isRefresh)
                } else {
                    loadMoreComplete()
                }
            }
        }
        if (mAdapter.data.isEmpty()) {
            mLayoutStatusView?.showEmpty()
        } else {
            mLayoutStatusView?.showContent()
        }
    }

    override fun showDeleteSuccess(success: Boolean) {
        if (success) {
            toast(resources.getString(R.string.delete_success))
        }
    }

    override fun showUpdateSuccess(success: Boolean) {
        if (success) {
            toast(resources.getString(R.string.completed))
        }
    }

    /**
     * RefreshListener
     */
    private val onRefreshListener = SwipeRefreshLayout.OnRefreshListener {
        isRefresh = true
        mAdapter.setEnableLoadMore(false)
        lazyLoad()
    }
    /**
     * LoadMoreListener
     */
    private val onRequestLoadMoreListener = BaseQuickAdapter.RequestLoadMoreListener {
        isRefresh = false
        swipeRefreshLayout.isRefreshing = false
        val page = mAdapter.data.size / 20 + 1
        if (bDone) {
            mPresenter?.getDoneList(page, mType)
        } else {
            mPresenter?.getNoTodoList(page, mType)
        }
    }

    /**
     * ItemClickListener
     */
    private val onItemClickListener = BaseQuickAdapter.OnItemClickListener { _, _, position ->
        if (datas.size != 0) {
            val data = datas[position]
        }
    }

    /**
     * ItemChildClickListener
     */
    private val onItemChildClickListener =
        BaseQuickAdapter.OnItemChildClickListener { _, view, position ->
            if (datas.size != 0) {
                val data = datas[position].t
                when (view.id) {
                    R.id.btn_delete -> {
                        if (!NetworkUtil.isNetworkAvailable(App.context)) {
                            showSnackbar(resources.getString(R.string.no_network))
                            return@OnItemChildClickListener
                        }
                        activity?.let {
                            DialogUtil.getConfirmDialog(it, resources.getString(R.string.confirm_delete),
                                DialogInterface.OnClickListener { _, _ ->
                                    mPresenter?.deleteTodoById(data.id)
                                    mAdapter.remove(position)
                                }).show()
                        }
                    }
                    R.id.btn_done -> {
                        if (!NetworkUtil.isNetworkAvailable(App.context)) {
                            showSnackbar(resources.getString(R.string.no_network))
                            return@OnItemChildClickListener
                        }
                        if (bDone) {
                            mPresenter?.updateTodoById(data.id, 0)
                        } else {
                            mPresenter?.updateTodoById(data.id, 1)
                        }
                        mAdapter.remove(position)
                    }
                    R.id.item_todo_content -> {
                        if (bDone) {
                            Intent(activity, CommonActivity::class.java).run {
                                putExtra(PreferenceConstant.TYPE_KEY, PreferenceConstant.Type.SEE_TODO_TYPE_KEY)
                                putExtra(PreferenceConstant.TODO_BEAN, data)
                                putExtra(PreferenceConstant.TODO_TYPE, mType)
                                startActivity(this)
                            }
                        } else {
                            Intent(activity, CommonActivity::class.java).run {
                                putExtra(PreferenceConstant.TYPE_KEY, PreferenceConstant.Type.EDIT_TODO_TYPE_KEY)
                                putExtra(PreferenceConstant.TODO_BEAN, data)
                                putExtra(PreferenceConstant.TODO_TYPE, mType)
                                startActivity(this)
                            }
                        }
                    }
                }
            }
        }

}